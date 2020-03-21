package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by yanhao on 2019/8/14.
 */
@Data
public class CarConfigResult {

    private String status;

    private String update_time;

    private String error_msg;

    @JSONField(name = "paramters")
    private Map<String, Object> paramters;
    @JSONField(name = "model")
    private Map<String, Object> model;
    private List city_list;
}
