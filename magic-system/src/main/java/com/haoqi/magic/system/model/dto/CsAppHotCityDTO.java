package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yanhao on 2019/11/28.15:49
 */
@Data
public class CsAppHotCityDTO implements Serializable {

    /**
     * 城市名
     */
    private String cityName;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 城市code
     */
    private String cityCode;
    private String value;
    /**
     * 城市热度(备用)
     */
    private Long hit;

    /**
     * 首字母
     */
    private String cityInitial;
}
