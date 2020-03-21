package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 发动机
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersCarEngineResult implements Serializable {

    @JSONField(name = "燃料形式")
    private String test;
    @JSONField(name = "排量(L)")
    private String test1;
    @JSONField(name = "环保标准")
    private String test2;
    @JSONField(name = "发动机型号")
    private String test3;
    @JSONField(name = "进气形式")
    private String test4;
    @JSONField(name = "燃油标号")
    private String test5;
    @JSONField(name = "缸体材料")
    private String test6;
}
