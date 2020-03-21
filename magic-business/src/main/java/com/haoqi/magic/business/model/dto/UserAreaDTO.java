package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author twg
 * @since 2019/5/6
 */
@Data
public class UserAreaDTO implements Serializable {
    private Long areaId;
    /**
     * 省份
     */
    private String provinceName;

    /**
     * 城市
     */
    private String cityName;
}
