package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class OrderPageVO extends Page {
    /**
     * 买方
     */
    private Long buyerUserId;

    /**
     * 车商联系人
     */
    private Long carDealerUserId;

    @ApiModelProperty(value = "功能类别（1：买入(待卖出)，2：待付款，3：未过户(待过户)，4：已过户，5：待收款，6：完成，7：争议，8：已取消）")
    @NotNull(message = "功能类别不能为空")
    private Integer tradeType;


    /**
     * 车型
     */
    private String keyWord;
}
