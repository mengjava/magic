package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class PayWaysListDTO implements Serializable {

	private static final long serialVersionUID = -5143934180981300842L;
    private List<PayWaysDTO> list;


}
