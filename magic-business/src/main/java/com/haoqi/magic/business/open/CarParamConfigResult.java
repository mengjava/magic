package com.haoqi.magic.business.open;

import lombok.Data;

/**
 * Created by yanhao on 2019/8/15.
 */
@Data
public class CarParamConfigResult {

    /**
     * 是否有ABS（1:有，0：无）
     */
    private Integer haveAbs;
    /**
     * 是否有转向动力（1:有，0：无）
     */
    private Integer haveTurnEngine;
    /**
     * 气囊
     */
    private String airBag;
    /**
     * 【数据字典】车窗玻璃类型
     */
    private String windowGlassCode;
    /**
     * 【数据字典】天窗类型
     */
    private String skyWindowCode;
    /**
     * 电动1、手动0
     */
    private Integer rearviewMirrorType;
    /**
     * 【数据字典】座椅材料
     */
    private String seatMaterialCode;
    /**
     * 【数据字典】座椅调节方式
     */
    private String seatAdjustTypeCode;
    /**
     * 【数据字典】座椅功能
     */
    private String seatFunctionCode;
    /**
     * 音响设备（0：cd,1:收音机，2dvd)
     */
    private Integer musicType;
    /**
     * 导航（0：无，1：加装，2：原装）
     */
    private Integer navigate;
    /**
     * 定速巡航（0：无，1：加装，2：原装）
     */
    private Integer dlcc;
    /**
     * 【数据字典】倒车雷达
     */
    private String pdcCode;
    /**
     * 【数据字典】倒车影像
     */
    private String rvcCode;
    /**
     * 【数据字典】轮毂
     */
    private String hubCode;
    /**
     * 空调 0手动 1自动
     */
    private Integer airCondition;
    /**
     * 管理车辆基础信息表id
     */
    private Long csCarInfoId;

}
