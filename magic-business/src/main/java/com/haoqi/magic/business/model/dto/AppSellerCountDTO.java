package com.haoqi.magic.business.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yanhao on 2019/12/3.16:11
 */
@Getter
@Setter
public class AppSellerCountDTO implements Serializable {

    /**
     * 待卖出数量
     */
    private int waitSellCount;
    /**
     * 待过户数量
     */
    private int waitTransferCount;
    /**
     * 待收款数量
     */
    private int waitReceiveCount;
    /**
     * 完成数量
     */
    private int completeCount;
    /**
     * 争议数量
     */
    private int disputeCount;
    /**
     * 取消数量
     */
    private int cancelCount;
}
