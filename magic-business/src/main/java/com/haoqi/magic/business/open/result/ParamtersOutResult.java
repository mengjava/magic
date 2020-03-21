package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 外部配置
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersOutResult implements Serializable {


    /**
     * 天窗
     */
    @JSONField(name = "电动天窗")
    private String test;
    @JSONField(name = "全景天窗")
    private String test1;

    /***
     *  轮毂
     */
    //铝合金轮圈 1 是铝合金 0 铁
    @JSONField(name = "铝合金轮圈")
    private String test2;
}
