package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 空调/冰箱
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersAirResult implements Serializable {

    /**
     * 空调 0手动 1自动,3无
     */
    //"空调控制方式": "自动",
    @JSONField(name = "空调控制方式")
    private String test1;


    @JSONField(name = "前排空调")
    private String test2;

    @JSONField(name = "车载冰箱")
    private String test3;

    @JSONField(name = "第三排独立空调")
    private String test;
}
