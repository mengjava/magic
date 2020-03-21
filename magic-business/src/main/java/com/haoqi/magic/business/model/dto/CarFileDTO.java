package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarFileDTO implements Serializable {
    private Long id;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    @NotBlank(message = "文件名不能为空")
    private String fileName;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String filePath;
    /**
     * 【来自数据字典】文件类型code（N_,Y_,N和Y开头的控制是否必填）
     */
    @ApiModelProperty(value = "【来自数据字典】文件类型code（N_,Y_,N和Y开头的控制是否必填）")
    @NotBlank(message = "文件类型不能为空")
    private String fileChildTypeCode;

    /**
     * 文件类型code名
     */
    @ApiModelProperty(value = "文件类型code名")
    private String fileChildTypeName;


    /**
     * 1:基本图片，2手续图片
     */
    @ApiModelProperty(value = "图片类型（1:基本图片，2手续图片）")
    @NotNull(message = "图片类型不能为空")
    private Integer fileType;

    private Long csCarInfoId;
    //fdfd静态地址
    private String webUrl;
}
