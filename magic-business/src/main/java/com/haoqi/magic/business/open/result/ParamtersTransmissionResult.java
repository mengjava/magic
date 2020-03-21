package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 变速箱
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersTransmissionResult implements Serializable {

    @JSONField(name = "简称")
    private String test;
    @JSONField(name = "换档拨片")
    private String test1;
    @JSONField(name = "挡位个数")
    private String test2;
    @JSONField(name = "变速箱类型")
    private String test3;
}
