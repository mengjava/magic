package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by twg on 2018/6/3.
 */
@Data
public class MenuDTO implements Serializable {

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 请求链接
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * VUE页面
     */
    private String component;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private Integer type;

    /**
     * 是否显示(0显示 1不显示)
     */
    private Integer isShow;
}
