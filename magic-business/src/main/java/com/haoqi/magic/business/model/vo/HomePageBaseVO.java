package com.haoqi.magic.business.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述:
 * 首页搜索查询参数
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/7 10:04
 * @Description:
 */
@Data
public class HomePageBaseVO extends Page {
    @ApiModelProperty(value = "车辆所在地 code")
    private String locate;
    @ApiModelProperty(value = "标签id")
    private Long tagId;
    //品质联盟
    @JsonIgnore
    private Integer creditUnion;

    @ApiModelProperty(value = "车辆所在地 locateStr")
    private String locateStr;

}
