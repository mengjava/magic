package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.common.enums <br/>
 * Function: 数据字典类别<br/>
 * Date:     2019/5/9 14:43 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
public enum DictClassEnum {

    TRANSMISSION_100000("100000", "变速箱"),
    CAR_TYPE_110000("110000", "车辆类型"),
    DRIVE_WAY_120000("120000", "驱动方式"),
    FUEL_TYPE_130000("130000", "燃油类型"),
    CAR_COLOR_140000("140000", "车辆颜色"),
    FILE_BASE_TYPE_150000("150000", "文件类型-基本图片"),
    EMISSION_STANDARD_160000("160000", "排放标准"),
    WORK_CODE_170000("170000", "工作情况"),
    INCOME_CODE_180000("180000", "收入情况"),
    AD_POSITION_190000("190000", "广告展示位置"),
    CAR_NO_200000("200000", "车辆号码"),//浙A 浙B
    FILE_PROCEDURE_TYPE_210000("210000", "文件类型-手续图片"),
    CAR_GLASS_220000("220000", "车窗玻璃"),
    CAR_WINDOW_230000("230000", "天窗"),
    SEAT_TYPE_240000("240000", "座椅材质"),
    SEAT_ADJUST_250000("250000", "座椅调节方式"),
    SEAT_FUNCTION_260000("260000", "座椅功能"),
    CAR_RADAR_270000("270000", "倒车雷达"),
    CAR_VIDEO_280000("280000", "倒车影像"),
    CAR_NAVE_BOSEE_290000("290000", "车毂"),
	BANK_CARD_LIST_300000("300000", "银行列表"),
    ;

    DictClassEnum(String classCode, String desc) {
        this.classCode = classCode;
        this.desc = desc;
    }

    private String classCode;
    private String desc;

}
