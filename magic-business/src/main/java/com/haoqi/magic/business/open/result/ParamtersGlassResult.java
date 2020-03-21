package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 玻璃/后视镜
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersGlassResult implements Serializable {

    /**
     * 【数据字典】车窗玻璃类型 两门电动 手动 四门 加装
     220001两门电动
     220002手动
     220003四门电动
     220004加装
     */

    // 1 有 0 无
    @JSONField(name = "前电动车窗")
    private String test;
    @JSONField(name = "后电动车窗")
    private String test2;


    /**
     * 车外后视镜 电动1、手动0
     */
    @JSONField(name = "后视镜电动调节")
    private String test3;
    @JSONField(name = "后视镜电动折叠")
    private String test1;
}
