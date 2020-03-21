package com.haoqi.magic.business.model.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haoqi.rigger.core.page.Page;
import lombok.Data;
/**
 * @Author: mengyao
 * @Date: 2019/12/4 0004 14:45
 * @Description:
 */
@Data
public class CsVipRecordDetailDTO extends Page implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 创建人
	 */
	private Long creator;
	/**
	 * 修改人
	 */
	private Long modifier;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtModified;
	/**
	 * 注释
	 */
	private String remark;
	/**
	 * 是否删除 0否1是
	 */
	private Integer isDeleted;
	/**
	 * "查询服务项名称（1维保，2排放，3出险，4车型识别，5快速评估）
	 */
	private String serviceItemName;
	/**
	 * 金额
	 */
	private BigDecimal money;
	/**
	 * 【冗余】用户名
	 */
	private String username;
	/**
	 * 状态（1：成功，默认，0：失败）
	 */
	private Integer status;
	/**
	 * 第三方返回结果（维保URL，出险URL，排放，快速评估价格，车型识别结果）
	 */
	private String result;
	/**
	 * vin
	 */
	private String vin;
	/**
	 * 【冗余】用户Id
	 */
	private Long sysUserId;
	/**
	 * 成本价
	 */
	private BigDecimal costPrice;
}
