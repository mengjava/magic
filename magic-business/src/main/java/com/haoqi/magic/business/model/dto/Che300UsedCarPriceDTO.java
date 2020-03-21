package com.haoqi.magic.business.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yanhao on 2018/11/8.
 */
@Data
public class Che300UsedCarPriceDTO implements Serializable {

    /**
     * token
     * modelId  车型ID(string )
     * zone  城市标识(string )
     * regDate   车辆上牌日期，如2012-01(date)
     * mile  车辆行驶里程，单位是万公里(double )
     */
    @NotNull(message = "车型ID不能为空")
    private Long modelId;
    @NotNull(message = "vin不能为空")
    private String vin;
    @NotBlank(message = "车辆上牌日期不能为空")
    private String regDate;
    @NotNull(message = "车辆行驶里程不能为空")
    private Long mile;
    @NotNull(message = "城市标识不能为空")
    private Long zone;
    /**
     * 数据来源
     */
    private String dataFrom;
}
