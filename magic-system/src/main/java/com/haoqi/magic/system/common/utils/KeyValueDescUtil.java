package com.haoqi.magic.system.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.system.model.entity.SysDict;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.business.common.utils <br/>
 * Function: <br/>
 * Date:     2019/5/7 14:19 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public class KeyValueDescUtil {

    /**
     * 查找key对应的描述
     * @param key
     * @param list
     * @return
     * @author huming
     * @date 2019/5/7 14:12
     */
    public static String handleValueDesc(String key, List<SysDict> list){

        if (StrUtil.isNotBlank(key) && CollectionUtil.isNotEmpty(list)){
            for (int i = 0,j = list.size(); i < j; i++){
                if (list.get(i).getKeyworld().equals(key)){
                    return list.get(i).getValueDesc();
                }
            }
        }
        return "";
    }
}
