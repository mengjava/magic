package com.haoqi.magic.common.enums;

import lombok.Getter;

/***
 * 排放标准的枚举
 */
@Getter
public enum EmissionStandardEnum {
    /**
     * 160000	排放标准
     * 160001	国1
     * 160002	国2
     * 160003	国3
     * 160004	国4
     * 160005	国5
     * 160006	国6
     * 160007	新能源
     * 160008	欧1
     * 160009	欧2
     * 160011	欧3
     * 160012	欧4
     * 160013	欧5
     * 160016	欧6
     */
    //EMISSION_STANDARD_160000("160000", "排放标准"),
    EMISSION_STANDARD_160001("160001", "国1"),
    EMISSION_STANDARD_160002("160002", "国2"),
    EMISSION_STANDARD_160003("160003", "国3"),
    EMISSION_STANDARD_160004("160004", "国4"),
    EMISSION_STANDARD_160005("160005", "国5"),
    EMISSION_STANDARD_160006("160006", "国6"),
    EMISSION_STANDARD_160007("160007", "新能源"),
    EMISSION_STANDARD_160008("160008", "欧1"),
    EMISSION_STANDARD_160009("160009", "欧2"),
    EMISSION_STANDARD_160011("160011", "欧3"),
    EMISSION_STANDARD_160012("160012", "欧4"),
    EMISSION_STANDARD_160013("160013", "欧5"),
    EMISSION_STANDARD_160016("160016", "欧6"),;

    EmissionStandardEnum(String classCode, String desc) {
        this.classCode = classCode;
        this.desc = desc;
    }
    private String classCode;
    private String desc;

    /***
     * 通过排放标准获取code
     * @param desc
     * @return
     */
    public static String getTypeCode(String desc) {
        EmissionStandardEnum[] typeEnums = EmissionStandardEnum.values();
        for (EmissionStandardEnum typeEnum : typeEnums) {
            if (typeEnum.getDesc().equals(desc)) {
                return typeEnum.getClassCode();
            }
        }
        return EMISSION_STANDARD_160001.getClassCode();
    }


    public static String getTypeDesc(String code) {
        EmissionStandardEnum[] typeEnums = EmissionStandardEnum.values();
        for (EmissionStandardEnum typeEnum : typeEnums) {
            if (typeEnum.getClassCode().equals(code)) {
                return typeEnum.getDesc();
            }
        }
        return EMISSION_STANDARD_160001.getDesc();
    }

    public static void main(String[] args) {
        System.out.println(getTypeCode(null));
    }
}
