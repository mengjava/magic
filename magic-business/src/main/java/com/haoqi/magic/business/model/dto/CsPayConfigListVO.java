package com.haoqi.magic.business.model.dto;

import com.haoqi.magic.business.model.vo.CsPayConfigVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: mengyao
 * @Date: 2019/12/12 0012 14:06
 * @Description:
 */
@Data
public class CsPayConfigListVO implements Serializable {

	private static final long serialVersionUID = 1455173104015068277L;
	/**
	 * 支付类别（0：代付，1：协议支付，默认为0）
	 */
	@ApiModelProperty(value = "支付类别（0：代付，1：协议支付，默认为0）")
	private Integer payType;

	@ApiModelProperty(value = "列表数据")
	private List<CsPayConfigVO> csPayConfigList;

	public CsPayConfigListVO(Integer yes, List<CsPayConfigVO> csPayConfigVOs) {
		this.payType=yes;
		this.csPayConfigList=csPayConfigVOs;
	}

}
