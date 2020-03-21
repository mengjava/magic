package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 车身
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersCarBodyResult implements Serializable {

    @JSONField(name = "长度(mm)")
    private String test;
    @JSONField(name = "行李箱电动吸合门")
    private String test1;
    @JSONField(name = "货箱尺寸(mm)")
    private String test2;
    @JSONField(name = "后备厢最大容积(L)")
    private String test3;
    @JSONField(name = "座位数(个)")
    private String test4;
    @JSONField(name = "车身材质")
    private String test5;
    @JSONField(name = "车门数(个)")
    private String test6;
}
