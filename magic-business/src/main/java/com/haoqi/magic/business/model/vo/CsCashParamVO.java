package com.haoqi.magic.business.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/27
 */
@Data
public class CsCashParamVO implements Serializable {
    /**
     * 银行卡id
     */
    @NotNull(message = "银行卡不能为空")
    private Long bankId;
    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal money;

    /**
     * 设备信息
     */
    private String device;

    /**
     * 来源
     */
    private Integer source;

}
