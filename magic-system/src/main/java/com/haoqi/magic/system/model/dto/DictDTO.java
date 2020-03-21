package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 首页数据字典筛选
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Data
public class DictDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    //private Long id;
    /**
     * 备注
     */
    //private String remark;
    /**
     * key
     */
    private String keyworld;
    /**
     * value
     */
    private String valueDesc;
    /**
     * 数据字典类级别
     */
    //private Integer classLevel;
    /**
     * 排序
     */
    //private Integer classOrder;
    /**
     * 模型名称
     */
    private String model;

    private List<DictDTO> children;
}
