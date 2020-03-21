package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName:com.haoqi.magic.system.model.vo <br/>
 * Function: 标签展示管理 <br/>
 * Date:     2019/4/30 9:23 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsTagVO extends Page {
    private static final long serialVersionUID = 2846163271892415488L;

    //主键
    private Long id;

    /**
     * 1筛选里的标签/2详情/3今日推荐
     */
    @ApiModelProperty(value = "类型，1筛选里的标签/2详情/3今日推荐",required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;

    //名称
    @ApiModelProperty(value = "名称",required = true)
    @NotBlank(message = "名称不能为空")
    private String tagName;

    //关联标签
    @ApiModelProperty(value = "关联标签id")
    @NotNull(message = "关联标签id不能为空")
    private Long csParamId;

    //排序
    @ApiModelProperty(value = "排序")
    @Min(value = 1, message = "排序号限制在[1-255]之间")
    @Max(value = 255, message = "排序号限制在[1-255]之间")
    private Integer orderNo;

    //文件名（详情专用）
    @ApiModelProperty(value = "文件名（详情专用）")
    private String fileName;

    //分组名（详情专用）
    @ApiModelProperty(value = "分组名（详情专用）")
    private String fileGroup;

    //文件路径（详情专用）
    @ApiModelProperty(value = "文件路径（详情专用）")
    private String filePath;
}
