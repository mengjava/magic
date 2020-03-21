package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 座椅材质
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersSeatResult implements Serializable {


    /**
     * 座椅材质
     */
    @JSONField(name = "座椅材质")
    private String test;


    /**
     * 座椅调节方式 电动 手动 记忆
     */
    @JSONField(name = "主驾驶座电动调节")
    private String test1;
    @JSONField(name = "电动座椅记忆")
    private String test2;




    /***
     * 座椅功能 通风 加热 按摩
     */

    @JSONField(name = "前排座椅按摩")
    private String test3;
    @JSONField(name = "后排座椅按摩")
    private String test4;

    @JSONField(name = "后排座椅加热")
    private String test5;
    @JSONField(name = "前排座椅加热")
    private String test6;

    @JSONField(name = "前排座椅通风")
    private String test7;
    @JSONField(name = "后排座椅通风")
    private String test8;
}
