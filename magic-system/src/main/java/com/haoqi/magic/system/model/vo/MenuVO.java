package com.haoqi.magic.system.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.LongJsonDeserializer;
import com.haoqi.rigger.core.serializer.LongJsonSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by twg on 2018/6/4.
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = -1028167562989421978L;
    /**
     * 菜单ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 前端VUE路径
     */
    private String menuPath;
    /**
     * 菜单权限请求链接
     */
    private String menuUrl;
    /**
     * 菜单权限请求方法:GET,POST,PUT,DELETE
     */
    private String menuMethod;
    /**
     * 父菜单ID
     */
    @NotNull(message = "父菜单id不能为空")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long parentId;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单排序值
     */
    private Integer menuSort;
    /**
     * 菜单类型:0菜单1权限
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;
    /**
     * 是否显示 0显示1不显示
     */
    private Integer isShow;
}
