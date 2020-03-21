package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 安全装备
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description: 
 */
@Data
public class ParamtersSafeResult implements Serializable {


    /**
     * 气囊个数
     */
    @JSONField(name = "第三排头部气囊(气帘)")
    private String test;
    @JSONField(name = "副驾驶座安全气囊")
    private String test1;
    @JSONField(name = "膝部气囊")
    private String test2;
    @JSONField(name = "前排侧气囊")
    private String test3;
    @JSONField(name = "后排头部气囊(气帘)")
    private String test4;
    @JSONField(name = "主驾驶座安全气囊")
    private String test5;
    @JSONField(name = "后排侧气囊")
    private String test6;

}
