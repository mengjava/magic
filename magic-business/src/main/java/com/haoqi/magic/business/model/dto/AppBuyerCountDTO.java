package com.haoqi.magic.business.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yanhao on 2019/12/3.16:11
 */
@Getter
@Setter
public class AppBuyerCountDTO implements Serializable {

    /**
     * 买入
     */
    private int buyCount;
    /**
     * 代付款
     */
    private int waitPayCount;
    /**
     * 过户
     */
    private int transferCount;
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
