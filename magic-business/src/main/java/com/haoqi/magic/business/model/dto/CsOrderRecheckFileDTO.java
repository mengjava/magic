package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 复检附件DTO (关联检测项)
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class CsOrderRecheckFileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "复检项id,添加时不传,更新时必传")
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private Long csCarOrderId;


    /**
     * 检测项文本拼接内容
     */
    @ApiModelProperty(value = "检测项文本拼接内容")
    private String checkItemText;
    /**
     * 关联检测项最后一级id,如果有多个逗号分隔
     */
    @ApiModelProperty(value = "关联检测项最后一级id,如果有多个逗号分隔")
    private String csCarCheckLastItemId;
    /**
     * 关联检测项父id
     */
    @ApiModelProperty(value = "关联检测项父id")
    private Long csCarCheckItemId;


    /**
     * 检查项名称
     */
    @ApiModelProperty(value = "检查项名称")
    private String checkItemName;

    @ApiModelProperty(value = "检查类型: 1事故检测，2外观检测，3检测信息")
    private Integer type;

    private String webUrl;
    /**
     * 检查项部位项集
     */
    @ApiModelProperty(value = "检查项部位项集")
    private List<CsCarCheckItemDTO> checkItems = new ArrayList<>();
}
