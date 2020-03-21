package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 车体
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersBodyResult implements Serializable {

    @JSONField(name = "车顶型式")
    private String test;
    @JSONField(name = "内饰颜色")
    private String test1;
    @JSONField(name = "车顶行李箱架")
    private String test2;
    @JSONField(name = "车篷型式")
    private String test3;
    @JSONField(name = "车身颜色")
    private String test4;
}
