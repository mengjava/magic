package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Map;

/**
 * @Author: mengyao
 * @Date: 2019/11/25 0025 11:25
 * @Description:
 */
@Data
public class CarReportResult {

	private Integer code;
	private String msg;
	@JSONField(name = "data")
	private Map<String, String> data;

}
