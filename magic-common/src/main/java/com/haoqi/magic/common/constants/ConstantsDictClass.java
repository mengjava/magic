package com.haoqi.magic.common.constants;

import lombok.Getter;

/**
 * 功能描述:
 * 数据字典枚举集合
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/10 16:28
 * @Description:
 */
public class ConstantsDictClass {


    /***
     * 变速箱 100000
     */
    @Getter
    public enum GearBoxTypeEnum {
        MANUAL_TYPE("100001", "手动"),
        AUTO_TYPE("100002", "自动"),
        ALL_TYPE("100003", "手自一体");

        GearBoxTypeEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private String key;
        private String name;

        public static String getKeyByName(String name) {
            GearBoxTypeEnum[] typeEnums = GearBoxTypeEnum.values();
            for (GearBoxTypeEnum typeEnum : typeEnums) {
                if (typeEnum.getName().equals(name)) {
                    return typeEnum.getKey();
                }
            }
            return GearBoxTypeEnum.MANUAL_TYPE.getKey();
        }
    }


    /**
     * 车辆类型 110000
     */
    @Getter
    public enum CarTypeEnum {
        MIN_CAR_TYPE("110001", "小型车"),
        TINY_CAR_TYPE("110002", "微型车"),
        MID_CAR_TYPE("110003", "中型车"),
        BIG_CAR_TYPE("110004", "大型车");

        CarTypeEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private String key;
        private String name;
    }

    /***
     * 驱动方式 120000
     */
    @Getter
    public enum DriveTypeEnum {
        FRONT_TYPE("120001", "前驱"),
        BACK_TYPE("120002", "后驱"),
        ALL_TYPE("120003", "四驱");

        DriveTypeEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private String key;
        private String name;
    }

    /**
     * 燃油类型 130000
     */
    @Getter
    public enum FuelTypeEnum {
        GASOLINE_TYPE("130001", "汽油"),
        DIESEL_OIL_TYPE("130002", "柴油"),
        OIL_ELE_TYPE("130003", "油电"),
        OIL_GAS_TYPE("130004", "油气"),
        ALL_ELE_TYPE("130005", "纯电");

        FuelTypeEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private String key;
        private String name;
    }

    /***
     * 广告类型 190000
     */
    @Getter
    public enum AdvertTypeEnum {

        INDEX_PAGE_TYPE("190001", "首页"),
        INSIDE_PAGE_TYPE("190002", "内页"),
        START_UP_TYPE("190002", "启动页");

        AdvertTypeEnum(String key, String name) {
            this.key = key;
            this.name = name;
        }

        private String key;
        private String name;
    }

    /**
     * 生产方式 (进口 国产)
     */
    @Getter
    public enum ImportTypeEnum {
        //进口
        IMPORT_TYPE("030001", 1),
        //国产
        CHINA_TYPE("030002", 0);

        ImportTypeEnum(String key, Integer name) {
            this.key = key;
            this.name = name;
        }

        public static Integer getValue(String key) {
            ImportTypeEnum[] typeEnums = ImportTypeEnum.values();
            for (ImportTypeEnum typeEnum : typeEnums) {
                if (typeEnum.getKey().equals(key)) {
                    return typeEnum.getName();
                }
            }
            return 0;
        }

        private String key;
        private Integer name;
    }
}
