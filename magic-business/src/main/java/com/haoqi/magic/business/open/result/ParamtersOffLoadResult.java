package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 越野性能
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersOffLoadResult implements Serializable {

    @JSONField(name = "最大涉水深度(mm)")
    private String test;
    @JSONField(name = "最小离地间隙(mm)")
    private String test1;
    @JSONField(name = "离去角(°)")
    private String test2;
    @JSONField(name = "纵向通过角(°)")
    private String test3;
}
