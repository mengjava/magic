package com.haoqi.magic.system.model.vo;


import com.haoqi.magic.system.model.dto.MenuDTO;
import lombok.Data;

/**
 * @author lengleng
 * @date 2017年11月9日23:33:27
 */
@Data
public class MenuTree extends TreeNode {
    private String icon;
    private String name;
    private String url;
    //权限码
    private String code;
    private Integer type;
    private String label;

    public MenuTree() {
    }

    public MenuTree(Long id, String name, Long parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.label = name;
    }

    public MenuTree(Long id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.label = name;
    }

    public MenuTree(MenuDTO menuVo) {
        this.id = menuVo.getMenuId();
        this.parentId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.url = menuVo.getUrl();
        this.type = menuVo.getType();
        this.label = menuVo.getName();
        this.isShow = menuVo.getIsShow();
    }
}
