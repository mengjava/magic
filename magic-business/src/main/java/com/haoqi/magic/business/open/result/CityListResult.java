package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by yanhao on 2019/8/14.
 */
@Data
public class CityListResult {

    private String status;
    private String prov_id;
    private String city_id;
    private List<CityMapResult> city_list;
}
