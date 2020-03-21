package com.haoqi.magic.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.magic.system.model.dto.UserDTO;
import com.haoqi.magic.system.model.entity.SysUser;
import com.haoqi.magic.system.model.entity.SysUserRole;
import com.haoqi.magic.system.model.vo.*;
import com.haoqi.magic.system.service.ISysPermissionService;
import com.haoqi.magic.system.service.ISysUserService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "系统用户管理")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private BeanValidatorHandler validatorHandler;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    @Value("${security.validate.code:false}")
    private boolean isValidate;

    /**
     * 分页获取用户信息
     *
     * @param userVO
     * @return
     * @author huming
     * @date 2019/1/14 10:07
     */
    @PostMapping("/userPage")
    @ApiOperation(value = "分页查询用户")
    public Result<Page> userPage(@RequestBody UserPageVO userVO) {
        currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("isDeleted", CommonConstant.STATUS_NORMAL);
        params.put("page", userVO.getPage());
        params.put("limit", userVO.getLimit());
        params.put("userName", StrUtil.emptyToNull(userVO.getUserName()));
        params.put("loginName", StrUtil.emptyToNull(userVO.getLoginName()));
        return Result.buildSuccessResult(userService.findUserByPage(new Query(params)));
    }

    /**
     * 添加用户信息
     *
     * @param user 用户对象
     * @return
     * @author huming
     * @date 2019/1/14 10:31
     */
    @PostMapping("/add")
    @ApiOperation("用户添加")
    public Result<Long> addUser(@RequestBody UserVO user) {
        currentUser();
        validatorHandler.validator(user);
        String salt = RandomUtil.randomString(32);
        String pass = DigestUtil.bcrypt(user.getPassword() + salt);
        UserDTO userDTO = BeanUtils.beanCopy(user, UserDTO.class);
        userDTO.setSalt(salt);
        userDTO.setPassword(pass);
        return Result.buildSuccessResult(userService.insertUser(userDTO));
    }


    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    public Result<Long> userRegister(@RequestBody UserVO user) {
        validatorHandler.validator(user);
        String salt = RandomUtil.randomString(32);
        String pass = DigestUtil.bcrypt(user.getPassword() + salt);
        UserDTO userDTO = BeanUtils.beanCopy(user, UserDTO.class);
        userDTO.setCreator(0L);
        userDTO.setModifier(0L);
        userDTO.setSalt(salt);
        userDTO.setPassword(pass);
        userDTO.setGmtCreate(DateUtil.date());
        userDTO.setGmtModified(DateUtil.date());
        Long userId = userService.insertUser(userDTO);
        return Result.buildSuccessResult(userId, "注册成功");
    }


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     * @author huming
     * @date 2019/1/14 11:18
     */
    @PutMapping("/update")
    @ApiOperation("更新用户信息")
    public Result<String> userUpdate(@RequestBody UserVO user) {
        currentUser();
        if (StrUtil.isNotBlank(user.getPassword())) {
            String salt = RandomUtil.randomString(32);
            String pass = DigestUtil.bcrypt(user.getPassword() + salt);
            user.setSalt(salt);
            user.setPassword(pass);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);
        userService.updateUser(userDTO);
        return Result.buildSuccessResult("更新成功");
    }


    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return
     * @author huming
     * @date 2019/1/15 12:00
     */
    @DeleteMapping("/{id}")
    @ApiOperation("通过ID删除用户信息")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        UserInfo userInfo = currentUser();
        if (id.equals(userInfo.getId())) {
            return Result.buildErrorResult("该用户所属本人，不能删除");
        }
        userService.deleteUserById(id);
        return Result.buildSuccessResult("删除成功");
    }

    /**
     * 通过用户名获取用户信息
     *
     * @param loginName 用户名称
     * @return
     * @author huming
     * @date 2019/1/15 12:00
     */
    @GetMapping("/getUserByName/{loginName}")
    @ApiOperation("通过用户名获取用户信息")
    public Result<UserDTO> getUserByName(@PathVariable("loginName") String loginName) {
        Optional<UserDTO> user = userService.getUserByLoginName(loginName);
        user.ifPresent(userDTO -> {
            if (StrUtil.isNotEmpty(userDTO.getHeadImageUrl())) {
                userDTO.setHeadImageUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), userDTO.getHeadImageUrl()));
            }
        });
        return Result.buildSuccessResult(user.get());
    }


    /**
     * 修改用户密码
     *
     * @return
     * @author huming
     * @date 2019/1/14 14:29
     */
    @PostMapping("/changePwd")
    @ApiOperation("修改用户密码")
    public Result<String> changePwd(@RequestBody UserPassVO userPassVO) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(userPassVO);
        SysUser user = userService.selectById(userInfo.getId());
        //根据添加用户时密码的生成规则，加密前端传递过来的密码
        if (!DigestUtil.bcryptCheck(userPassVO.getOldPass() + user.getSalt(), user.getPassword())) {
            return Result.buildErrorResult("原密码错误，无法修改");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(DigestUtil.bcrypt(userPassVO.getPass() + user.getSalt()));
        userService.updateWithPassword(userDTO);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     * @author huming
     * @date 2019/1/14 14:27
     */
    @GetMapping(value = "/userTree")
    @ApiOperation(value = "返回当前用户的树形菜单集合")
    public Result<List<MenuTree>> userTree() {
        UserInfo userInfo = currentUser();
        List<MenuTree> menuTrees = permissionService.findMenuTreeByRoleCodes(userInfo.getId(), findRoles().toArray(new String[]{}));
        return Result.buildSuccessResult(menuTrees);
    }

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return
     * @author huming
     * @date 2019/1/15 11:59
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过用户ID获取用户信息")
    public Result<UserDTO> user(@PathVariable Long id) {
        currentUser();
        return Result.buildSuccessResult(userService.getUserWithRoleAreaById(id).get());
    }

    /**
     * 功能描述:
     * 获取当前用户信息
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/1/17 10:17
     * @Description:
     */
    @GetMapping("/userInfo")
    public Result<UserDTO> userInfo() {
        UserInfo userInfo = currentUser();
        Result<UserDTO> result = getUserRolesByName(userInfo.getUserName());
        if (result.isSuccess()) {
            UserDTO dto = result.getData();
            //用户密码进行保护
            dto.setPassword("");
            dto.setSalt("");
            if (StrUtil.isNotEmpty(dto.getHeadImageUrl())) {
                dto.setHeadImageUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), dto.getHeadImageUrl()));
            }
            if (CollectionUtil.isEmpty(dto.getRoles())) {
                return Result.buildSuccessResult(dto);
            }
            dto.setPermissions(permissionService.findPermission(dto.getId(), dto.getRoleCodes()));
            return Result.buildSuccessResult(dto);
        }
        return Result.buildErrorResult("用户不存在");
    }

    /**
     * 通过用户名获取用户、角色信息
     *
     * @param username
     * @return
     */
    @GetMapping("/getUserRolesByName/{username}")
    public Result<UserDTO> getUserRolesByName(@PathVariable("username") String username) {
        Optional<UserDTO> optional = userService.getUserWithRoleByLoginName(username);
        if (!optional.isPresent()) {
            return Result.buildErrorResult("用户不存在");
        }
        return Result.buildSuccessResult(optional.get());
    }

    /**
     * 功能描述:
     * 用户分配角色
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/1/22 13:53
     * @Description:
     */
    @PutMapping("/updateUserRole")
    @ApiOperation("根据id更新用户角色信息")
    public Result<String> updateUserRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        String[] splitRoleIds = roleIds.split(",");
        userService.updateUserRole(userId, splitRoleIds);
        return Result.buildSuccessResult("更新成功");
    }

    /**
     * 功能描述:
     * 获取当前用户角色ids
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/1/22 14:09
     * @Description:
     */
    @GetMapping("/roleTree/{userId}")
    @ApiOperation(value = "通过用户id获取角色集合")
    public List<Long> userRoleTree(@PathVariable("userId") Long userId) {
        List<SysUserRole> sysUserRoles = userService.findUserRoleTreeByUserId(userId);
        return sysUserRoles.stream().filter(sysUserRole -> Objects.nonNull(sysUserRole.getSysRoleId()))
                .map(SysUserRole::getSysRoleId).collect(Collectors.toList());
    }


    /***
     * 功能描述:通过角色获取用户集合
     * @param role
     * @return com.haoqi.rigger.core.Result<java.util.List<com.haoqi.magic.system.model.dto.UserDTO>>
     * @auther mengyao
     * @date 2019/8/16 0016 下午 2:48
     */
    @GetMapping("/getUserListByRole/{role}")
    @ApiOperation(value = "通过角色获取用户集合 1:检测员 2:审核员")
    public Result<List<UserDTO>> getUserListByRole(@PathVariable("role") Integer role) {
        List<UserDTO> sysUserRoles = userService.getUserListByRole(role);
        return Result.buildSuccessResult(sysUserRoles);
    }

    /**
     * 功能描述: 商家重置密码
     *
     * @param mobile
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/8 0008 上午 10:58
     */
    @PutMapping("/resetPassword/{mobile}")
    @ApiOperation(value = "商家重置密码")
    public Result<String> resetPassword(@PathVariable String mobile) {
        currentUser();
        userService.resetPassword(mobile);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 功能描述: 商家启用禁用时同步更新用户的启用禁用
     *
     * @param loginName
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/22 0022 下午 4:31
     */
    @PutMapping("/updateEnabledByLoginName/{loginName}")
    @ApiOperation(value = "商家启用禁用时同步更新用户的启用禁用")
    public Result<String> updateEnabledByLoginName(@PathVariable String loginName) {
        currentUser();
        userService.updateEnabledByLoginName(loginName);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 更新用户类型
     *
     * @param id
     * @return
     */
    @PutMapping("updateUserType/{id}")
    public Result<String> updateUserType(@PathVariable("id") Long id) {
        currentUser();
        SysUser user = new SysUser();
        user.setType(UserLevelEnum.SELLER_LEVEL.getLevel());
        boolean result = userService.update(user, new EntityWrapper<SysUser>()
                .eq("id", id)
                .eq("type", UserLevelEnum.USER_LEVEL.getLevel())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return result ? Result.buildSuccessResult("操作成功") : Result.buildErrorResult("操作失败");

    }


    @GetMapping("/getUserAreaAndAccount/{id}")
    @ApiOperation(value = "通过用户ID获取用户信息和地区和账户信息")
    public Result<UserDTO> getUserAreaAndAccount(@PathVariable("id") Long id) {
        //currentUser();
        return Result.buildSuccessResult(userService.getUserAreaAndAccountById(id).get());
    }


    /**
     * 修改用户支付密码
     *
     * @param userPassVO
     * @return
     */
    @PostMapping("/changePayPwd")
    @ApiOperation("修改用户支付密码")
    public Result<String> changePayPwd(@RequestBody UserPassVO userPassVO) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(userPassVO);
        SysUser user = userService.selectById(userInfo.getId());
        //支付原密码
        if (!DigestUtil.bcryptCheck(userPassVO.getOldPass(), user.getPayPassword())) {
            return Result.buildErrorResult("支付原密码错误，无法修改");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPayPassword(DigestUtil.bcrypt(userPassVO.getPass()));
        userService.updateWithPayPassword(userDTO);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 设置用户支付密码
     *
     * @param userPassVO
     * @return
     */
    @PostMapping("/setPayPwd")
    @ApiOperation("设置用户支付密码")
    public Result<String> setPayPwd(@RequestBody UserPassVO userPassVO) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(userPassVO);
        SysUser user = userService.selectById(userInfo.getId());
        //根据添加用户时密码的生成规则，加密前端传递过来的密码
        if (!DigestUtil.bcryptCheck(userPassVO.getOldPass() + user.getSalt(), user.getPassword())) {
            return Result.buildErrorResult("原密码错误，无法修改");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPayPassword(DigestUtil.bcrypt(userPassVO.getPass()));
        userService.updateWithPayPassword(userDTO);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 确认支付密码
     *
     * @return
     */
    @PostMapping("confirmPayPwd")
    @ApiOperation("确认支付密码")
    public Result<String> confirmPayPwd(@RequestParam("password") String password) {
        UserInfo userInfo = currentUser();
        userService.confirmPayPwd(userInfo.getId(), password);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过用户id，获取用户、是否会员、商家信息
     *
     * @return
     */
    @GetMapping("getUserWithVipAndDealer")
    @ApiOperation("获取用户、是否会员、商家信息")
    public Result<UserAreaVipDealerVO> getUserWithVipAndDealer() {
        UserInfo userInfo = currentUser();
        UserDTO userDTO = userService.getUserAreaWithVipAndDealer(userInfo.getId());
        UserAreaVipDealerVO userAreaVipDealerVO = BeanUtils.beanCopy(userDTO, UserAreaVipDealerVO.class);
        userAreaVipDealerVO.setHasPayPassword(StrUtil.isBlank(userDTO.getPayPassword()) ? Boolean.FALSE : Boolean.TRUE);
        return Result.buildSuccessResult(userAreaVipDealerVO);
    }


    /**
     * 更新用户vip标示
     *
     * @param id
     * @return
     */
    @PutMapping("updateUserVipFlag/{id}/{vipFlag}")
    public Result<String> updateUserVipFlag(@PathVariable("id") Long id, @PathVariable("vipFlag") Integer vipFlag) {
        currentUser();
        SysUser user = new SysUser();
        user.setVipFlag(vipFlag);
        boolean result = userService.update(user, new EntityWrapper<SysUser>()
                .eq("id", id)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return result ? Result.buildSuccessResult("操作成功") : Result.buildErrorResult("操作失败");
    }

    @PutMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam("image") String image) {
        UserInfo userInfo = currentUser();
        SysUser user = new SysUser();
        user.setId(userInfo.getId());
        user.setHeadImageUrl(image);
        userService.updateById(user);
        return Result.buildSuccessResult("操作成功");
    }
}