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
import com.haoqi.magic.system.model.vo.MenuTree;
import com.haoqi.magic.system.model.vo.UserPageVO;
import com.haoqi.magic.system.model.vo.UserPassVO;
import com.haoqi.magic.system.model.vo.UserVO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/buyerUser")
@Api(tags = "买家用户管理")
public class SysBuyerUserController extends BaseController {

	@Autowired
	private ISysUserService userService;


	/**
	 * 功能描述: 分页查询买家
	 *
	 * @param userVO
	 * @return com.haoqi.rigger.core.Result<com.baomidou.mybatisplus.plugins.Page>
	 * @auther mengyao
	 * @date 2019/12/25 0025 下午 3:28
	 */
	@PostMapping("/buyerUserPage")
	@ApiOperation(value = "分页查询买家")
	public Result<Page> buyerUserPage(@RequestBody UserPageVO userVO) {
		currentUser();
		Map<String, Object> params = Maps.newHashMap();
		BeanUtil.beanToMap(userVO, params, false, true);
		return Result.buildSuccessResult(userService.findBuyerUserByPage(new Query(params)));
	}


	/**
	 * 功能描述: 重置用户密码
	 *
	 * @param ids
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/25 0025 下午 3:27
	 */
	@PutMapping("/resetPwd/{ids}")
	@ApiOperation("重置用户密码 传userId")
	public Result<String> resetPwd(@PathVariable("ids") String ids) {
		userService.resetPwd(ids);
		return Result.buildSuccessResult("操作成功");
	}


	/**
	 * 功能描述: 重置用户支付密码
	 *
	 * @param
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/25 0025 下午 3:28
	 */
	@PostMapping("/resetPayPwd/{ids}")
	@ApiOperation("重置用户支付密码 传userId")
	public Result<String> resetPayPwd(@PathVariable("ids") String ids) {
		userService.resetPayPwd(ids);
		return Result.buildSuccessResult("操作成功");
	}


	/**
	 * 功能描述: 买家启用禁用
	 * @param id
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/25 0025 下午 3:29
	 */
	@PutMapping("/edit/{id}/{isEnabled}")
	@ApiOperation(value = "买家启用禁用 isEnabled:0-正常，1-失效")
	public Result<String> updateStatus(@PathVariable Long id,@PathVariable Integer isEnabled) {
		SysUser user = new SysUser();
		user.setId(id);
		user.setIsEnabled(isEnabled);
		userService.updateById(user);
		return Result.buildSuccessResult("操作成功");
	}
}

