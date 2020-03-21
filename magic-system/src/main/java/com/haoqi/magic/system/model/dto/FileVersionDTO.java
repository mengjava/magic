package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author twg
 * @since 2018/8/10
 */
@Data
public class FileVersionDTO implements Serializable {

    private String file;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 请求文件地址
     */
    private String url;

    /**
     * 文件内容MD5字符串
     */
    private String fileMD5;

    private String sourceName;

}
