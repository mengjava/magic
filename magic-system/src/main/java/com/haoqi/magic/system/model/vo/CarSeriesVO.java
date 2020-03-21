package com.haoqi.magic.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yanhao on 2019/4/28.
 */
@Getter
@Setter
public class CarSeriesVO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 车系Id
     */
    @NotNull(message = "品牌Id")
    private Integer brandId;
    /**
     * 车系名称
     */
    @ApiModelProperty(value = "车系名称")
    @NotEmpty(message = "车系名称不能为空")
    private String seriesName;
    /**
     * 车系分组名
     */
    /*@ApiModelProperty(value = "车系分组名")
    private String seriesGroupName;*/
    /**
     * 车类型（中型，紧凑等等）
     */
    /*@ApiModelProperty(value = "车类型（中型，紧凑等等）")
    private String levelName;*/
    /**
     * 合资/进口/国产
     */
    /*@ApiModelProperty(value = "合资/进口/国产")
    private String makerType;*/


}
