package com.haoqi.magic.system.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author twg
 * @since 2018/8/10
 */
@Data
public class FileVersionVO implements Serializable {


    private static final long serialVersionUID = 6441057720144022991L;
    /**
     * 资源文件唯一标识
     */
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
    private String fileMd5;

}
