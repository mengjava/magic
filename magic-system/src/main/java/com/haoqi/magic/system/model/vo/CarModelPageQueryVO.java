package com.haoqi.magic.system.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yanhao on 2019/4/26.
 */
@Getter
@Setter
public class CarModelPageQueryVO extends Page {

    /**
     * 车系id
     */
    private Integer seriesId;
    /**
     * 车型名称
     */
    private String modelName;



    private String keyword;
}
