package com.haoqi.magic.business.job;

import com.haoqi.magic.business.service.ICsCarOrderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: mengyao
 * @Date: 2019/12/20 0020 11:05
 * @Description:
 */
@Component
@JobHandler(value="overTimeJobHandler")
public class OverTimeJobHandler extends IJobHandler {

	@Autowired
	private ICsCarOrderService  csCarOrderService;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		XxlJobLogger.log("dispute overTime task ...{}", s);
		csCarOrderService.overTimeHandle();
		return SUCCESS;
	}
}
