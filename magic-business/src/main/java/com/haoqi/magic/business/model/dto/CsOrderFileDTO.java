package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
@Data
public class CsOrderFileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long csCarOrderId;
    /**
     * 附件类型（1：过户，2：买家打款，3，卖家打款）
     */
    private Integer type;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 分组名
     */
    private String fileGroup;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     *
     */
    private String pictureURL;
}
