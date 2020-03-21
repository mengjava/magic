package com.haoqi.magic.business.open.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 功能描述:
 * 操控配置
 * @auther: yanhao
 * @param:
 * @date: 2019/8/15 11:08
 * @Description: 
 */
@Data
public class ParamtersCaoResult {


    /***
     * 是否有ABS（1:有，0：无）
     */
    @JSONField(name = "ABS防抱死")
    private String test;




    @JSONField(name = "前桥限滑差速器/差速锁")
    private String test1;
    @JSONField(name = "中央差速器锁止功能")
    private String test2;
    @JSONField(name = "上坡辅助")
    private String test3;
    @JSONField(name = "陡坡缓降")
    private String test4;
    @JSONField(name = "随速助力转向调节(EPS)")
    private String test5;
    @JSONField(name = "空气悬架")
    private String test6;
    @JSONField(name = "车身稳定控制(ESC/ESP/DSC等)")
    private String test7;
    @JSONField(name = "可变转向比")
    private String test8;




}
