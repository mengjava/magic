package com.haoqi.magic.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yanhao on 2019/8/12.
 */
public class ValidUtils {

    /**
     * 车辆VIN
     */
    private static final String isCarVin = "^[1234567890WERTYUPASDFGHJKLZXCVBNM]{13}[0-9]{4}$";

    public static boolean isCarVin(String carVin) {

        return matches(isCarVin, carVin);

    }
    public static boolean matches(String regex, String input) {
        if (StringUtils.isBlank(input)) return false;
        if (input.matches(regex)) return true;
        return false;
    }

}
