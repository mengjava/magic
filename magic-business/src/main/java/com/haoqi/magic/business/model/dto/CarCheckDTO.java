package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author twg
 * @since 2019/5/7
 */
@Data
public class CarCheckDTO implements Serializable {
    private Long id;

    @NotBlank(message = "异常图片名不能为空")
    @ApiModelProperty(value = "异常图片名")
    private String fileName;

    @NotBlank(message = "异常图片路径不能为空")
    @ApiModelProperty(value = "异常图片路径")
    private String filePath;

    @NotBlank(message = "异常部位名不能为空")
    @ApiModelProperty(value = "异常部位名")
    private String checkItemText;

    @ApiModelProperty(value = "异常项id")
    private String csCarCheckLastItemId;

    @NotNull(message = "检查项id不能为空")
    @ApiModelProperty(value = "检查项id")
    private Long csCarCheckItemId;

    @NotNull(message = "车辆id不能为空")
    @ApiModelProperty(value = "车辆id")
    private Long csCarInfoId;


    /**
     * 检查项名称
     */
    @ApiModelProperty(value = "检查项名称")
    private String checkItemName;

    @ApiModelProperty(value = "检查类型")
    private Integer type;

    private String webUrl;
    /**
     * 检查项部位项集
     */
    @ApiModelProperty(value = "检查项部位项集")
    private List<CsCarCheckItemDTO> checkItems = new ArrayList<>();
}
