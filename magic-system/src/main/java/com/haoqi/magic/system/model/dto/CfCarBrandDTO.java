package com.haoqi.magic.system.model.dto;

import lombok.Data;

@Data
public class CfCarBrandDTO {

    private static final long serialVersionUID = 1L;
    private String brandNameMain; // 品牌名称
    private String brandNameMainType; // 品牌简称
    private Integer brandId;
}
