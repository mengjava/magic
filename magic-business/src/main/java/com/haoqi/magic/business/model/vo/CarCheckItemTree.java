package com.haoqi.magic.business.model.vo;

import lombok.Data;

/**
 * Created by yanhao on 2018/7/2 12:36.
 */
@Data
public class CarCheckItemTree extends TreeNode {
    /**
     * 检测项名称
     */
    private String name;
    /**
     * 检测项code(type_pinyin)
     */
    private String code;
    /**
     * 排序
     */
    private Integer orderNo;
    /**
     * 1事故检测，2外观检测，3检测信息
     */
    private Integer type;

}
