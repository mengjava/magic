package com.haoqi.magic.system.service;

import com.haoqi.magic.system.model.vo.SysSendPasswordVO;
import com.haoqi.rigger.core.Result;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Date:     2019/4/17 10:18 <br/>
 * @author mengyao
 * @see
 * @since JDK 1.8
 */
public interface ISysSmsSendService {





    /**
     * 功能描述: 发送短信验证码
     * @param sysSendPasswordVO
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/9 0009 上午 9:57
     */
	 Result<String> sendSmsValidateCode(@RequestBody SysSendPasswordVO sysSendPasswordVO);

	 /**
	  * 功能描述: 发送商家账号密码
	  * @param sysSendPasswordVO
	  * @return com.haoqi.rigger.core.Result<java.lang.String>
	  * @auther mengyao
	  * @date 2019/5/9 0009 上午 10:02
	  */
	 Result<String> sendCarDealerPassword(@RequestBody SysSendPasswordVO sysSendPasswordVO);


	/**
	 * 功能描述: 发送审核拒绝短信
	 * @param mobile
	 * @param auditDetail
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/5/13 0013 下午 4:24
	 */
	Result<String> sendRefuseMessage(String mobile, String auditDetail);

	}
