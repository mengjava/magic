package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 灯光配置
 *
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description:
 */
@Data
public class ParamtersLightingResult implements Serializable {

    @JSONField(name = "远光灯")
    private String test;
    @JSONField(name = "侧转向灯")
    private String test1;
    @JSONField(name = "车厢前阅读灯")
    private String test2;
    @JSONField(name = "日间行车灯")
    private String test3;
    @JSONField(name = "前雾灯")
    private String test4;
    @JSONField(name = "后雾灯")
    private String test5;
    @JSONField(name = "转向辅助灯")
    private String test6;
    @JSONField(name = "LED尾灯")
    private String test7;
}
