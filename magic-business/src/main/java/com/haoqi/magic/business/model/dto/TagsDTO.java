package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class TagsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "标签名称")
    private String tagName;
    /**
     * 文件路径（详情专用）
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;

}
