package com.haoqi.magic.business.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 过户附件
 *
 * @author twg
 * @since 2019/12/10
 */
@Data
public class RecheckFileVO implements Serializable {

    private Long id;

    /**
     * 检测项文本拼接内容
     */
    private String checkItemText;

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 关联检测项最后一级id,如果有多个逗号分隔
     */
    private String csCarCheckLastItemId;
    /**
     * 关联检测项父id
     */
    private Long csCarCheckItemId;

    private String webUrl;
}
