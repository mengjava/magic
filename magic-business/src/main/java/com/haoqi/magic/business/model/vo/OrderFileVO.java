package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yanhao on 2019/12/11.15:14
 */
@Getter
@Setter
public class OrderFileVO extends Page {

    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单id不能为空")
    private Long csCarOrderId;

    @ApiModelProperty(value = "附件类型")
    @NotNull(message = "附件类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "打款附件 1：过户，2：买家打款，3，卖家打款")
    List<BaseFileVO> files;

}
