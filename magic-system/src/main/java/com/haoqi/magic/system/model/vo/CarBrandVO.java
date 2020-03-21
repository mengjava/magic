package com.haoqi.magic.system.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by yanhao on 2019/4/28.
 */
@Getter
@Setter
public class CarBrandVO implements Serializable{

    private Long id;
    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 品牌名称
     */
    @NotEmpty(message = "品牌名称不能为空")
    private String brandName;
    /**
     * 品牌首字母
     */
    private String brandInitial;

}
