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
public class TransferFileVO implements Serializable {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;

    private String webUrl;
}
