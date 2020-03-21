package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 基本参数
 * @auther: yanhao
 * @param:
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersBaseResult implements Serializable {

    @JSONField(name = "市郊工况油耗")
    private String test;
    @JSONField(name = "整车质保")
    private String test1;
    @JSONField(name = "工信部综合油耗(L/100km)")
    private String test2;
    @JSONField(name = "车身结构")
    private String test3;
    @JSONField(name = "发动机")
    private String test4;
    @JSONField(name = "市区工况油耗")
    private String test5;
    @JSONField(name = "实测油耗(L/100km)")
    private String test6;
    @JSONField(name = "官方0-100km/h加速(s)")
    private String test7;
    @JSONField(name = "长*宽*高(mm)")
    private String test8;
    @JSONField(name = "级别")
    private String test9;
    @JSONField(name = "变速箱")
    private String test10;
    @JSONField(name = "厂商")
    private String test11;

}
