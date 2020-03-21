package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mengyao
 * @since 2019-04-25
 */
@Data
public class SysAdvertDTO implements Serializable {

    private Long id;
    private String title;
    @ApiModelProperty(value = "图链接")
    private String pictureURL;
    private String picturePath;
    private String positionCode;
    private String pictureName;
    @ApiModelProperty(value = "跳转链接")
    private String linkUrl;
    private Integer sort;
    @ApiModelProperty(value = "跳转类型（1：网页，2：本地app页面,0不跳转,网页内编辑）")
    private Integer jumpType;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
