package com.haoqi.magic.business.model.dto;

import com.haoqi.magic.business.model.dto.CsServiceFeeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: mengyao
 * @Date: 2019/12/18 0018 11:55
 * @Description:
 */
@Data
public class CsServiceFeeListDTO  implements Serializable {

	private static final long serialVersionUID = -315907313391351087L;
	@ApiModelProperty(value = "区域Id")
	@NotNull
	private Long sysAreaId;

	@ApiModelProperty(value = "信息列表")
	private List<CsServiceFeeDTO> list;
}
