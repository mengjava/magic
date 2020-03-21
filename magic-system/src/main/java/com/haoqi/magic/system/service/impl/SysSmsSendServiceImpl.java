package com.haoqi.magic.system.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.system.common.sms.BuYunSmsResult;
import com.haoqi.magic.system.common.sms.BuYunSmsUtil;
import com.haoqi.magic.system.model.entity.SysMessageTemplate;
import com.haoqi.magic.system.model.vo.SysSendPasswordVO;
import com.haoqi.magic.system.service.ISysMessageTemplateService;
import com.haoqi.magic.system.service.ISysSmsSendService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

/**
 * Date:     2019/4/17 10:18 <br/>
 *
 * @author mengyao
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class SysSmsSendServiceImpl implements ISysSmsSendService {
	@Autowired
	private BuYunSmsUtil buYunSmsUtil;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ISysMessageTemplateService sysMessageTemplateService;

	/**
	 * redis 前缀
	 */
	@Value("${spring.redis.prefix}")
	private String prefix;


	private BuYunSmsResult buYunSendSmsValidateCode(String mobile, String prefix, String validateCode) {
		SysMessageTemplate commonMessageTemplate = new SysMessageTemplate();
		commonMessageTemplate.setTemplateCode(Constants.TEMPLATE_CODE_SMS);

		commonMessageTemplate = sysMessageTemplateService.getOne(commonMessageTemplate);
		if (null == commonMessageTemplate) {
			return new BuYunSmsResult(BuYunSmsResult.codeEnum.CODE_201.getCode(), BuYunSmsResult.codeEnum.CODE_201.getMsg());
		}

		String content = commonMessageTemplate.getTemplateContent();
		content = content.replace("#{code}", validateCode);
		return buYunSmsUtil.buYunSendSms(mobile, content, null, null);
	}


	private BuYunSmsResult sendPassword(SysSendPasswordVO sysSendPasswordVO) {
		SysMessageTemplate sysMessageTemplate = new SysMessageTemplate();
		sysMessageTemplate.setTemplateCode(Constants.SMS_RESET_PASSWORD);
		sysMessageTemplate = sysMessageTemplateService.getOne(sysMessageTemplate);
		if (null == sysMessageTemplate) {
			return new BuYunSmsResult(BuYunSmsResult.codeEnum.CODE_301.getCode(), BuYunSmsResult.codeEnum.CODE_301.getMsg());
		}
		String content = sysMessageTemplate.getTemplateContent();
		content = content.replace("#{tel}", sysSendPasswordVO.getTel());
		content = content.replace("#{password}", sysSendPasswordVO.getPassword());
		BuYunSmsResult result = buYunSmsUtil.buYunSendSms(sysSendPasswordVO.getTel(), content, null, null);
		result.setContent(content);
		return result;
	}


	private BuYunSmsResult setMessage(String mobile, String auditDetail) {
		SysMessageTemplate sysMessageTemplate = new SysMessageTemplate();
		sysMessageTemplate.setTemplateCode(Constants.SMS_AUDIT_REJECTION);
		sysMessageTemplate = sysMessageTemplateService.getOne(sysMessageTemplate);
		if (null == sysMessageTemplate) {
			return new BuYunSmsResult(BuYunSmsResult.codeEnum.CODE_301.getCode(), BuYunSmsResult.codeEnum.CODE_301.getMsg());
		}
		String content = sysMessageTemplate.getTemplateContent();
		content = content.replace("#{auditDetail}", auditDetail);
		BuYunSmsResult result = buYunSmsUtil.buYunSendSms(mobile, content, null, null);
		result.setContent(content);
		return result;
	}

	@Override
	public Result<String> sendSmsValidateCode(SysSendPasswordVO sysSendPasswordVO) {
		SmsHandler smsHandler = new SmsHandler() {
			@Override
			protected String handler() {
				//5、随机生成6未验证码
				String validateCode = RandomUtil.randomNumbers(6);
				//5、1 发送验证码
				BuYunSmsResult result = buYunSendSmsValidateCode(sysSendPasswordVO.getTel(), sysSendPasswordVO.getPrefix(), validateCode);
				//5、2 校验是否发送成功
				if (!Constants.SMS_SUCCESS_CODE.equals(result.getCode())) {
					throw new RiggerException("发送失败：" + result.getErrMsg());
				}
				return validateCode;
			}
		};
		return Result.buildSuccessResult(smsHandler.sendSms(sysSendPasswordVO.getTel()));
	}


	@Override
	public Result<String> sendCarDealerPassword(@RequestBody SysSendPasswordVO sysSendPasswordVO) {
		//发送账号密码
		BuYunSmsResult result = sendPassword(sysSendPasswordVO);
		// 校验是否发送成功
		if (!Constants.SMS_SUCCESS_CODE.equals(result.getCode())) {
			throw new RiggerException("发送失败：" + result.getErrMsg());
		}
		return Result.buildSuccessResult("发送成功");
	}

	@Override
	@Transactional
	public Result<String> sendRefuseMessage(String mobile, String auditDetail) {
		// 发送拒绝信息
		BuYunSmsResult result = setMessage(mobile, auditDetail);
		// 校验是否发送成功
		if (!Constants.SMS_SUCCESS_CODE.equals(result.getCode())) {
			throw new RiggerException("发送失败：" + result.getErrMsg());
		}
		return Result.buildSuccessResult("发送成功");
	}


	abstract class SmsHandler {
		private String sendSms(String tel) throws RiggerException {
			//3、获取缓存中短信条数
			String sendCodeNumKey = String.format("%s:sendCodeNum:%s", prefix, tel);
			Long count = redisTemplate.opsForValue().increment(sendCodeNumKey, 1);
			redisTemplate.expire(sendCodeNumKey, Constants.HOURS24, TimeUnit.SECONDS);
			//3、1 短信条数超出24小时最大限制
			if (count > Constants.DEFAULT_SMS_NUM) {
				throw new RiggerException("发送失败：短信发送次数请求超限,请24小时后重试");
			}
			//4、首先查看缓存中是否存在验证码，存在直接从缓存取
			String sendCodeKey = String.format("%s:sendCode:%s", prefix, tel);
			if (redisTemplate.hasKey(sendCodeKey)) {
				//4、1 缓存中存在，此次发生不占次数
				redisTemplate.opsForValue().decrement(sendCodeKey, 1);
				//4、2 取出缓存中的验证码，返回给调用者
				return (String) redisTemplate.opsForValue().get(sendCodeKey);
			}
			String result = handler();
			//5、3 短信发送成功，缓存中设置短信验证码,有效时间为5分钟
			redisTemplate.opsForValue().set(sendCodeKey, result, CommonConstant.DEFAULT_IMAGE_EXPIRE * 5, TimeUnit.SECONDS);
			return result;
		}

		/**
		 * 自定义业务处理
		 *
		 * @return
		 */
		protected abstract String handler();

	}

}
