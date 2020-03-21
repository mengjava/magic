package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function: 车商-车源管理<br/>
 * Date:     2019/5/8 11:15 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCarSourceVO implements Serializable {

    private static final long serialVersionUID = -7545718208279292807L;

    //车辆主键ID
    @ApiModelProperty(value = "车辆id")
    private Long id;

    //车商ID
    private Long csCarDealerId;

	@ApiModelProperty(value = "下架备注")
    private String remark;


}
