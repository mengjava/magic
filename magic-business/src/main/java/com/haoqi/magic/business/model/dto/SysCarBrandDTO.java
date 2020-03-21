package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Getter
@Setter
public class SysCarBrandDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private Integer brandId;
    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    /**
     * 品牌首字母
     */
    @ApiModelProperty(value = "品牌首字母")
    private String brandInitial;

}
