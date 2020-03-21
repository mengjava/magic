package com.haoqi.magic.system.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yanhao on 2019/4/26.
 */
@Getter
@Setter
public class CarSeriesPageQueryVO extends Page {
    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 车系名称
     */
    private String seriesName;
}
