package com.haoqi.magic.business.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签封装展示管理
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
@Data
@Accessors(chain = true)
public class CsTagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    private Date gmtModified;

    /**
     * 1筛选里的标签/2详情/3今日推荐
     */
    private Integer type;
    /**
     * 名称
     */
    private String tagName;
    /**
     * 关联标签
     */
    private Long csParamId;
    /**
     * 排序
     */
    private Integer orderNo;
    /**
     * 文件名（详情专用）
     */
    private String fileName;
    /**
     * 分组名（详情专用）
     */
    private String fileGroup;
    /**
     * 文件路径（详情专用）
     */
    private String filePath;

    private String pictureURL;

}
