package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.magic.system.mapper.SysUserMapper;
import com.haoqi.magic.system.model.dto.UserAreaDTO;
import com.haoqi.magic.system.model.dto.UserDTO;
import com.haoqi.magic.system.model.entity.CsAccount;
import com.haoqi.magic.system.model.entity.SysUser;
import com.haoqi.magic.system.model.entity.SysUserArea;
import com.haoqi.magic.system.model.entity.SysUserRole;
import com.haoqi.magic.system.model.enums.UserRoleEnum;
import com.haoqi.magic.system.model.vo.SysSendPasswordVO;
import com.haoqi.magic.system.service.*;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.common.util.SecureUtils;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysUserAreaService userAreaService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ISysSmsSendService sysSmsSendService;

    @Autowired
    private ICsAccountService accountService;

    private final static String PASSWORD = "111111";

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;

    @Override
    public Boolean deleteUserById(Long userId) {
        SysUser user = this.selectById(userId);
        if (Objects.isNull(user)) {
            throw new RiggerException("该用户不存在");
        }
        SysUserRole userRole = new SysUserRole();
        userRole.setSysUserId(userId);
        userRoleService.delete(new EntityWrapper<SysUserRole>(userRole));
        redisTemplate.keys("user:*");
        return this.deleteById(userId);
    }

    @Override
    public Page<UserDTO> findUserByPage(Query query) {
        List<UserDTO> userDTOS = userMapper.findUserByPage(query, query.getCondition());
        userDTOS.forEach(userDTO -> {
            setUserDTO(userDTO.getId(), userDTO);
        });
        return query.setRecords(userDTOS);
    }

    @Override
    public List<SysUserRole> findUserRoleTreeByUserId(Long userId) {
        Assert.notNull(userId, "用户id不能为空");
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserId(userId);
        return userRoleService.selectList(new EntityWrapper<>(sysUserRole));
    }

    @Override
    public Optional<UserDTO> getUserByLoginName(String loginName) {
        Assert.notBlank(loginName, "用户名不能为空！");
        SysUser user = new SysUser();
        user.setLoginName(loginName);
        SysUser u = userMapper.selectOne(user);
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(u, userDTO);
        return Optional.ofNullable(userDTO);
    }

    @Override
    public Optional<UserDTO> getUserWithRoleById(Long userId) {
        Assert.notNull(userId, "用户ID不能为空");
        return Optional.ofNullable(userMapper.getUserWithRoleById(userId));
    }

    @Override
    public Optional<UserDTO> getUserWithRoleAreaById(Long userId) {
        Assert.notNull(userId, "用户ID不能为空");
        Optional<UserDTO> optional = getUserWithRoleById(userId);
        UserDTO userDTO = setUserDTO(userId, optional.get());
        return Optional.of(userDTO);
    }


    @Override
    @Cached(name = "user:getUserWithRoleByLoginName:", key = "#loginName", expire = 86400)
    @CacheRefresh(refresh = 10, stopRefreshAfterLastAccess = 200)
    public Optional<UserDTO> getUserWithRoleByLoginName(String loginName) {
        Assert.notBlank(loginName, "用户名不能为空");
        return Optional.ofNullable(userMapper.getUserWithRoleByLoginName(loginName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertUser(UserDTO userDTO) {
        Assert.notBlank(userDTO.getLoginName(), "用户名不能为空");
        if (isExist(userDTO.getLoginName())) {
            throw new RiggerException("用户[" + userDTO.getLoginName() + "]已存在！");
        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(userDTO, sysUser);
        this.insert(sysUser);
        if (UserLevelEnum.USER_LEVEL.getLevel().equals(userDTO.getType()) ||
                UserLevelEnum.SELLER_LEVEL.getLevel().equals(userDTO.getType())) {
            CsAccount account = BeanUtils.beanCopy(sysUser, CsAccount.class, "id");
            account.setSysUserId(sysUser.getId());
            accountService.insert(account);
        }
        if (Objects.nonNull(userDTO.getRoleIds())) {
            String[] roleIds = userDTO.getRoleIds();
            insertUserRole(sysUser.getId(), roleIds);
        }
        if (Objects.isNull(userDTO.getAreaId())) {
            return sysUser.getId();
        }
        insertUserArea(userDTO.getAreaId(), sysUser.getId());
        return sysUser.getId();
    }


    @Override
    public Boolean isExist(String loginName) {
        Assert.notBlank(loginName, "用户名不能为空！");
        SysUser user = new SysUser();
        user.setLoginName(loginName);
        SysUser sysUser = userMapper.selectOne(user);
        if (Objects.isNull(sysUser)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户ID不能为空！");
        Assert.notNull(userDTO.getLoginName(), "用户名不能为空！");
        int count = this.selectCount(new EntityWrapper().eq("login_name", userDTO.getLoginName()).ne("id", userDTO.getId()));
        if (count > 0) {
            throw new RiggerException("用户[" + userDTO.getLoginName() + "]已存在！");
        }
        if (userDTO.getRoleIds().length > 0) {
            SysUserRole userRole = new SysUserRole();
            userRole.setSysUserId(userDTO.getId());
            userRoleService.delete(new EntityWrapper<SysUserRole>(userRole));
            //保存用户角色关系
            String[] roleIds = userDTO.getRoleIds();
            insertUserRole(userDTO.getId(), roleIds);
        }
        insertUserArea(userDTO.getAreaId(), userDTO.getId());
        SysUser sysUser = this.selectById(userDTO.getId());
        BeanUtil.copyProperties(userDTO, sysUser);
        this.updateById(sysUser);
        //普通用户、车商 生成账户
        if (UserLevelEnum.USER_LEVEL.getLevel().equals(userDTO.getType()) ||
                UserLevelEnum.SELLER_LEVEL.getLevel().equals(userDTO.getType())) {
            CsAccount oldAccount = accountService.selectOne(new EntityWrapper<CsAccount>()
                    .eq("sys_user_id", sysUser.getId())
                    .eq("is_deleted", CommonConstant.STATUS_NORMAL));
            if (Objects.isNull(oldAccount)) {
                CsAccount account = new CsAccount();
                account.setSysUserId(sysUser.getId());
                accountService.insert(account);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateWithPassword(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户ID不能为空");
        Assert.notNull(userDTO.getPassword(), "密码不能为空");
        return this.updateById(BeanUtils.beanCopy(userDTO, SysUser.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserRole(Long userId, String[] roleIds) {
        SysUserRole userRole = new SysUserRole();
        userRole.setSysUserId(userId);
        userRoleService.delete(new EntityWrapper<SysUserRole>(userRole));
        insertUserRole(userId, roleIds);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean resetPassword(String mobile) {
        SysUser sysUser = this.selectOne(new EntityWrapper<SysUser>().eq("tel", mobile));
        if (Objects.isNull(sysUser)) {
            return Boolean.TRUE;
        }
	    resetPassword(mobile, sysUser);
        return Boolean.TRUE;
    }

	private void resetPassword(String mobile, SysUser sysUser) {
		String randomString = RandomUtil.randomString(8);
		String password = DigestUtil.bcrypt(randomString + sysUser.getSalt());
		sysUser.setPassword(password);
		this.updateById(sysUser);

		CompletableFuture.runAsync(() -> {
		    SysSendPasswordVO smsVO = new SysSendPasswordVO();
		    smsVO.setTel(mobile);
		    smsVO.setPassword(randomString);
		    sysSmsSendService.sendCarDealerPassword(smsVO);
		});
	}

	@Override
    public Boolean updateEnabledByLoginName(String loginName) {
        Assert.notNull(loginName, "用户loginName不能为空");
        SysUser sysUser = this.selectOne(new EntityWrapper<SysUser>().eq("login_name", loginName));
        Integer isEnabled = CommonConstant.STATUS_NORMAL.equals(sysUser.getIsEnabled()) ?
                CommonConstant.STATUS_DEL : CommonConstant.STATUS_NORMAL;
        sysUser.setIsEnabled(isEnabled);
        this.updateById(sysUser);
        return Boolean.TRUE;
    }

    @Override
    public List<UserDTO> getUserListByRole(Integer role) {
        List<UserDTO> list = userMapper.getUserListByRole(UserRoleEnum.getTypeName(role));
        return list;
    }

    @Override
    public Optional<UserDTO> getUserAreaAndAccountById(Long id) {
        Assert.notNull(id, "用户ID不能为空");
        Optional<UserDTO> optional = getUserWithRoleById(id);
        UserDTO userDTO = setUserDTO(id, optional.get());
        setAccount(id, userDTO);
        return Optional.of(userDTO);
    }

    @Override
    public Boolean updateWithPayPassword(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户ID不能为空");
        Assert.notNull(userDTO.getPayPassword(), "支付密码不能为空");
        return this.updateById(BeanUtils.beanCopy(userDTO, SysUser.class));
    }

    @Override
    public Page findBuyerUserByPage(Query query) {
        List<UserDTO> userDTOS = userMapper.findBuyerUserByPage(query, query.getCondition());
        userDTOS.forEach(userDTO -> {
            setUserDTO(userDTO.getId(), userDTO);
        });
        return query.setRecords(userDTOS);
    }

    @Override
    public UserDTO getUserAreaWithVipAndDealer(Long userId) {
        UserDTO user = super.baseMapper.getUserAreaWithVipAndDealer(userId);
        if (Objects.isNull(user.getExpiredDate()) ||
                (Objects.nonNull(user.getExpiredDate()) && DateUtil.beginOfDay(DateUtil.date()).after(user.getExpiredDate()))) {
            user.setVipFlag(CommonConstant.STATUS_NORMAL);
        }
        return setUserDTO(userId, user);
    }

    @Override
    public void resetPwd(String ids) {
        String[] reIds = ids.split(",");
        for (String reId : reIds) {
            SysUser user = this.selectById(reId);
	        resetPassword(user.getTel(),user);
        }

    }

    @Override
    public void resetPayPwd(String ids) {
        String[] reIds = ids.split(",");
        for (String reId : reIds) {
            SysUser user = this.selectById(reId);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setPayPassword(DigestUtil.bcrypt(PASSWORD));
            this.updateWithPayPassword(userDTO);
        }

    }

    @Override
    public void confirmPayPwd(Long userId, String password) {
        try {
            SysUser user = this.selectById(userId);
            String context = SecureUtils.decryptRSAByPrivateKey(password, Constants.privateKey);
            if (!DigestUtil.bcryptCheck(context, user.getPayPassword())) {
                throw new RiggerException("支付密码不正确");
            }
        } catch (Exception e) {
            log.error("支付密码确认异常：", e);
            throw new RiggerException("支付密码不正确");
        }
    }

    /****************************************************************************************/

    private void setAccount(Long id, UserDTO userDTO) {
        CsAccount account = null;
        try {
            account = accountService.getByUserId(id);
        } catch (Exception e) {

        }
        if (Objects.nonNull(account)) {
            userDTO.setAccountId(account.getId());
            userDTO.setBalanceMoney(account.getBalanceMoney());
            userDTO.setFreezeMoney(account.getFreezeMoney());
        }
    }


    /**
     * 添加用户、角色关系
     *
     * @param userId
     * @param roleIds
     */
    private void insertUserRole(Long userId, String[] roleIds) {
        Arrays.stream(roleIds).forEach(o -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setSysRoleId(Long.parseLong(o));
            sysUserRole.setSysUserId(userId);
            userRoleService.insert(sysUserRole);
        });
    }

    private void insertUserArea(Long areaId, Long userId) {
        SysUserArea sysUserArea = userAreaService.selectOne(new EntityWrapper<SysUserArea>().eq("sys_user_id", userId));
        if (Objects.isNull(sysUserArea)) {
            SysUserArea userArea = new SysUserArea();
            userArea.setCreator(0L);
            userArea.setModifier(0L);
            userArea.setGmtCreate(DateUtil.date());
            userArea.setGmtModified(userArea.getGmtCreate());
            userArea.setSysUserId(userId);
            userArea.setSysAreaId(areaId);
            userAreaService.insert(userArea);
            return;
        }
        sysUserArea.setSysUserId(userId);
        sysUserArea.setSysAreaId(areaId);
        userAreaService.updateById(sysUserArea);
    }

    private UserDTO setUserDTO(Long userId, UserDTO userDTO) {
        Optional<UserAreaDTO> userAreaDTO = userAreaService.getByUserId(userId);
        userAreaDTO.ifPresent(o -> {
            userDTO.setAreaId(o.getAreaId());
            userDTO.setArea(o.getProvinceName() + StrUtil.DASHED + o.getCityName());
        });
        return userDTO;
    }
}
