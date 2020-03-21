package com.haoqi.magic.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 热发布DTO
 * @author huming
 * @date 2019/4/25 14:52
 */
@Data
public class SysHotDeployDTO implements Serializable {

    //主键ID
    private Long id;
    /**
     * 备注
     */
    @ApiModelProperty(value = "热更新备注")
    private String remark;
    /**
     * APP名称
     */
    @ApiModelProperty(value = "app名称")
    private String name;
    /**
     * APP类型
     */
    @ApiModelProperty(value = "APP类型（1:IOS 2:安卓3:pad）")
    private String type;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件分组
     */
    private String fileGroup;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    /**
     * 文件md5
     */
    @ApiModelProperty(value = "文件md5")
    private String md5;
    /**
     * 请求文件地址
     */
    @ApiModelProperty(value = "请求文件地址")
    private String url;
    /**
     * 项目标识（来自数据字典）
     */
    @ApiModelProperty(value = "项目标识")
    private String projectCode;

    @ApiModelProperty(value = "app类型唯一标识")
    private String appKey;

}
