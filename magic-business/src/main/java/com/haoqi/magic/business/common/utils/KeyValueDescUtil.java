package com.haoqi.magic.business.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.business.model.dto.SysDictDTO;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.business.common.utils <br/>
 * Function: 在字典list中查找对应的desc<br/>
 * Date:     2019/5/7 14:19 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public class KeyValueDescUtil {

    /**
     * 查找key对应的描述
     *
     * @param key
     * @param list
     * @return
     * @author huming
     * @date 2019/5/7 14:12
     */
    public static String handleValueDesc(String key, List<SysDictDTO> list) {
        String value = "";
        if (CollectionUtil.isEmpty(list) || StrUtil.isBlank(key)) {
            return value;
        }
        for (SysDictDTO sysDictDTO : list) {
            if (sysDictDTO.getKeyworld().equals(key)) {
                value = sysDictDTO.getValueDesc();
                break;
            }
        }
        return value;
    }
}
