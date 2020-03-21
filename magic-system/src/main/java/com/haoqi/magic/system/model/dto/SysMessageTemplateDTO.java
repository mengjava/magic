package com.haoqi.magic.system.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: <br/>
 * @author huming
 * @date 2019/4/25 10:16
 * @see
 * @since JDK 1.8
 */
@Data
public class SysMessageTemplateDTO implements Serializable {

    private static final long serialVersionUID = -700015985095781347L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 消息唯一标识（供调用时使用）
     */
    private String templateCode;

    /**
     * 消息内容
     */
    private String templateContent;

    /**
     * 模板变量提示内容
     */
    private String templateVariables;
}
