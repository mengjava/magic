package com.haoqi.magic.system.model.vo;

import lombok.Data;

/**
 * Created by yanhao on 2018/7/2 12:36.
 */
@Data
public class DictTree extends TreeNode {

    private String valueDesc;

    private String keyworld;

    private Integer classLevel;

    private Integer classOrder;

    private String classType;
}
