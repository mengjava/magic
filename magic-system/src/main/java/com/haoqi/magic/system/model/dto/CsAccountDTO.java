package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/27
 */
@Data
public class CsAccountDTO implements Serializable {
    private Long id;
    private Long sysUserId;
    private String username;

    /**
     * 余额
     */
    private BigDecimal balanceMoney;
    /**
     * 冻结金额
     */
    private BigDecimal freezeMoney;
}
