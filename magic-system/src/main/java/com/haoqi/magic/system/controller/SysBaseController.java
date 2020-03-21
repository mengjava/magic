package com.haoqi.magic.system.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.system.model.dto.SysHotDeployDTO;
import com.haoqi.magic.system.model.dto.SysProvinceListDTO;
import com.haoqi.magic.system.model.entity.SysFileVersionDetail;
import com.haoqi.magic.system.model.entity.SysUser;
import com.haoqi.magic.system.model.vo.FileVersionVO;
import com.haoqi.magic.system.model.vo.SysSendPasswordVO;
import com.haoqi.magic.system.service.*;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.exception.ValidateCodeException;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Function: 不需要鉴权的controller<br/>
 * Date:     2019/4/31 15:34 <br/>
 *
 * @author mengyao
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RefreshScope
@RequestMapping("/base")
public class SysBaseController extends BaseController {

    @Autowired
    private ISysSmsSendService sysSmsSendService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ISysAreaService sysAreaService;

    @Autowired
    private ISysFileVersionDetailService fileVersionDetailService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    /**
     * 热发布服务
     */
    @Autowired
    private ISysHotDeployService sysHotDeployService;

    @Autowired
    private ISysUserService userService;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    /**
     * 功能描述:发送短信验证码
     *
     * @param mobile
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/9 0009 上午 10:42
     */
    @PostMapping("/sendSmsValidateCode")
    @ApiOperation(value = "发送短信验证码")
    public Result<String> sendSmsValidateCode(@RequestParam("mobile") String mobile) {
        String key = String.format("%s:sendCode:%s", prefix, mobile);
        if (redisTemplate.hasKey(key)) {
            return Result.buildSuccessResult("发送成功");
        }
        SysSendPasswordVO smsVO = new SysSendPasswordVO();
        smsVO.setTel(mobile);
        Result<String> result = sysSmsSendService.sendSmsValidateCode(smsVO);
        if (!result.isSuccess()) {
            return Result.buildErrorResult("发送失败");
        }
        return Result.buildSuccessResult("发送成功");
    }

    /**
     * 功能描述:审核拒绝后发送信息
     *
     * @param mobile
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/13 0013 下午 2:54
     */
    @PostMapping("/sendRefuseMessage")
    @ApiOperation(value = "审核拒绝后发送信息")
    public Result<String> sendRefuseMessage(@RequestParam("mobile") String mobile, @RequestParam("auditDetail") String auditDetail) {
        Result<String> result = sysSmsSendService.sendRefuseMessage(mobile,auditDetail);
        if (!result.isSuccess()) {
            return Result.buildErrorResult("发送失败");
        }
        return Result.buildSuccessResult("发送成功");
    }



    /**
     * 功能描述:发送商家账号密码
     *
     * @param tel
     * @param password
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/9 0009 上午 11:12
     */
    @PostMapping("/sendCarDealerPassword")
    @ApiOperation(value = "发送商家账号密码")
    public Result<String> sendCarDealerPassword(@RequestParam("tel") String tel, @RequestParam("password") String password) {
        SysSendPasswordVO smsVO = new SysSendPasswordVO();
        smsVO.setTel(tel);
        smsVO.setPassword(password);
        Result<String> result = sysSmsSendService.sendCarDealerPassword(smsVO);
        if (!result.isSuccess()) {
            return Result.buildErrorResult("发送失败");
        }
        return Result.buildSuccessResult("发送成功");
    }

    /**
     * 功能描述: 省份城市二级列表
     *
     * @auther mengyao
     * @date 2019/4/28 0028 上午 9:34
     */
    @PostMapping("getProvinceAndCityList")
    @ApiOperation(value = "获取省份城市二级列表")
    public Result<List<SysProvinceListDTO>> provinceList() {
        return Result.buildSuccessResult(sysAreaService.findProvinceAndCityList(), "查询成功");
    }


    /**
     * 获取系统文件版本
     *
     * @return
     */
    @GetMapping("/fileVersion/all")
    public Result<List<FileVersionVO>> allFile() {
        List<SysFileVersionDetail> fileVersionDetails = fileVersionDetailService.selectList(new EntityWrapper<>());
        List<FileVersionVO> fileVersions = BeanUtils.beansToList(fileVersionDetails, FileVersionVO.class);
        return Result.buildSuccessResult(fileVersions);
    }

    /**
     * 通过type 获取最新的版本信息 1:IOS 2:安卓
     *
     * @param type
     * @return
     * @author huming
     * @date 2019/4/25 15:59
     */
    @GetMapping("/checkAppVersion/{type}")
    @ApiOperation(value = "通type 获取最新的版本信息")
    public Result<SysHotDeployDTO> checkAppVersion(@PathVariable("type") String type) {
        return Result.buildSuccessResult(sysHotDeployService.checkAppVersion(type));
    }

    /**
     * 商家通过手机号，忘记密码
     *
     * @param tel
     * @param password
     * @param messageCode
     * @return
     */
    @PutMapping("/user/setPassword")
    @ApiOperation(value = "商家通过手机号，设置密码")
    public Result<String> setPassword(@RequestParam("tel") String tel, @RequestParam("password") String password,  @RequestParam("messageCode") String messageCode) {
        String telCode = (String) redisTemplate.opsForValue().get(String.format("%s:sendCode:%s", prefix, tel));
        if (!messageCode.equals(telCode)) {
            return Result.buildErrorResult("短信验证码错误");
        }
        SysUser user = userService.selectOne(new EntityWrapper<SysUser>().eq("tel", tel));
        if (Objects.isNull(user)) {
            return Result.buildSuccessResult("密码设置成功");
        }
        user.setPassword(DigestUtil.bcrypt(password + user.getSalt()));
        userService.updateById(user);
        return Result.buildSuccessResult("密码设置成功");
    }



    private void isValidateCode(String code, String randomStr) {
        if (code.isEmpty() || randomStr.isEmpty()) {
            throw new ValidateCodeException("请输入验证码");
        }
        String key = String.format("%s:%s:%s", prefix, SecurityConstants.DEFAULT_CODE_KEY, randomStr);
        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException("验证码已过期，请重新获取");
        }
        String codeObj = (String) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        if (StrUtil.isBlank(codeObj)) {
            throw new ValidateCodeException("验证码已过期，请重新获取");
        }
        if (!StrUtil.equals(codeObj, code)) {
            throw new ValidateCodeException("验证码错误，请重新输入");
        }
    }


}
