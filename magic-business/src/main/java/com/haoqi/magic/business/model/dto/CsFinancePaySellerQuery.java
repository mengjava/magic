package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 财务打款表
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Data
public class CsFinancePaySellerQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车订单id
     */
    @ApiModelProperty(value = "车订单id")
    @NotNull(message = "车订单id不能为空")
    private Long id;
    /**
     * 订单ID
     */
    @ApiModelProperty(value = "车商用户id")
    @NotNull(message = "车商用户id不能为空")
    private Long carDealerUserId;




}
