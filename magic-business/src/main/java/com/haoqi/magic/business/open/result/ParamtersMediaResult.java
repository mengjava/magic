package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 多媒体配置
 *
 * @auther: yanhao
 * @param:engine
 * @date: 2019/8/14 21:56
 * @Description:
 */
@Data
public class ParamtersMediaResult implements Serializable {

    /**
     * （0：无，1：加装，2：原装）
     */
    @JSONField(name = "GPS导航系统")
    private String test;

    /**
     * 音响设备 （0：cd,1:收音机，2dvd, 3:无)
     */
    @JSONField(name = "CD/DVD")
    private String test1;


    @JSONField(name = "多媒体系统")
    private String test2;
    @JSONField(name = "内置硬盘")
    private String test3;
    @JSONField(name = "车载电视")
    private String test4;
    @JSONField(name = "中控台彩色大屏")
    private String test5;
    @JSONField(name = "后排音响遥控器")
    private String test6;
}
