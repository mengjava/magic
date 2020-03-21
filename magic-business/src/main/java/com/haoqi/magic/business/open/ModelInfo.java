package com.haoqi.magic.business.open;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by yanhao on 2019/8/9.
 */
@Data
public class ModelInfo implements Serializable{

    /**
     * 排放标准
     */
    @JSONField(name = "model_emission_standard")
    private String modelEmissionStandard;
    @JSONField(name = "model_id")
    private Long modelId;
    @JSONField(name = "series_group_name")
    private String seriesGroupName;
    @JSONField(name = "series_name")
    private String seriesName;
    @JSONField(name = "model_year")
    private int modelYear;
    @JSONField(name = "brand_id")
    private Long brandId;
    /**
     * 变速箱类型
     */
    @JSONField(name = "model_gear")
    private String modelGear;
    /**
     * 排量
     */
    @JSONField(name = "model_liter")
    private String modelLiter;
    @JSONField(name = "model_name")
    private String modelName;
    @JSONField(name = "brand_name")
    private String brandName;
    @JSONField(name = "series_id")
    private Long seriesId;

    private double model_price;

    private int min_reg_year;

    private int max_reg_year;
    @JSONField(name = "ext_model_id")
    private Long extModelId;
    /***
     * 区别配置
     */
    private DiffParams diff_params;
}
