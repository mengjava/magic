package com.haoqi.magic.system.model.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 热发布管理VO
 * @author huming
 * @date 2019/4/25 14:16
 */
@Data
public class SysHotDeployVO extends Page {
    //数据主键ID
    private Long id;

    //热更新备注
    @ApiModelProperty(value = "热更新备注")
    @NotBlank(message = "热更新备注不能为空")
    private String remark;

    //APP名称
    @ApiModelProperty(value = "app名称")
    @NotBlank(message = "app名称不能为空")
    private String name;

    //APP类型
    @ApiModelProperty(value = "APP类型（1:IOS 2:安卓3:IOS-pad 4:安卓-pad）")
    @NotBlank(message = "APP类型不能为空")
    private String type;

    //版本号
    @ApiModelProperty(value = "版本号")
    @NotBlank(message = "版本号不能为空")
    private String version;

    //文件名
    @TableField("file_name")
    private String fileName;

    //文件分组
    @TableField("file_group")
    private String fileGroup;

    //文件路径
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    //文件md5
    @ApiModelProperty(value = "文件md5")
    private String md5;

    //请求文件地址
    @ApiModelProperty(value = "请求文件地址")
    private String url;

    //项目标识（来自数据字典）
    @ApiModelProperty(value = "项目标识")
    private String projectCode;

    //是否删除 0否1是
    @ApiModelProperty(value = "是否有效(0启用,1停用)")
    private Integer isDeleted;

    @ApiModelProperty(value = "上传时间开始")
    private String startDate;

    @ApiModelProperty(value = "上传时间结束")
    private String endDate;

    @ApiModelProperty(value = "app类型唯一标识")
    private String appKey;
}
