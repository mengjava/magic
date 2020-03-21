package com.haoqi.magic.common.utils;

import java.util.regex.Pattern;

/**
 * 功能描述:
 *
 * @Author: yanhao 
 * @Date: 2019/12/24 9:20
 * @Param:  
 * @Description:
 */
public class UrlUtils {

    public static Boolean checkUrlLegal(String url) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=#]*)?";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(url).matches()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
