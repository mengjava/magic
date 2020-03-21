package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/6
 */
@Data
public class AccountTotalAmountDTO implements Serializable {

    /**
     * 收入
     */
    private BigDecimal income;
    /**
     * 支出
     */
    private BigDecimal pay;

}
