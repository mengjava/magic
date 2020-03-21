package com.haoqi.magic.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/27
 */
@Data
public class CsAccountVO implements Serializable {
    /**
     * 账户id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long sysUserId;
    /**
     * 用户姓名
     */
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
