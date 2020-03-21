package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册时成为商家
 */
@Data
public class CarDealerRegisterVO implements Serializable {

    /**
     * 经销商简称
     */
    @ApiModelProperty(value = "公司简称", required = true)
    @NotBlank(message = "公司简称不能为空")
    private String shortName;

    /**
     * 经销商名称/营业执照名称(唯一）
     */
    @ApiModelProperty(value = "营业执照名称", required = true)
    private String dealerName;

    /**
     * 地址
     */
    @ApiModelProperty(value = "公司地址", required = true)
    private String address;

    /**
     * 成立日期
     */
    @ApiModelProperty(value = "公司成立日期", required = true)
    private Date establishTime;


    /**
     * 营业执照编码（唯一）
     */
    @ApiModelProperty(value = "营业执照号码", required = true)
    private String licenceNo;

    /**
     * 营业执照文件名
     */
    @ApiModelProperty(value = "营业执照文件名", required = true)
    private String fileName;

    /**
     * 营业执照分组名
     */
    private String fileGroup;

    /**
     * 营业执照文件路径
     */
    @ApiModelProperty(value = "营业执照文件路径", required = true)
    private String filePath;

    /**
     * 法人身份证正面照文件名
     */
    @ApiModelProperty(value = "法人身份证正面照文件名")
    private String cardFrontFileName;

    /**
     * 法人身份证正面照分组名
     */
    @ApiModelProperty(value = "法人身份证正面照分组名")
    private String cardFrontFileGroup;

    /**
     * 法人身份证正面照文件路径
     */
    @ApiModelProperty(value = "法人身份证正面照文件路径")
    private String cardFrontFilePath;

    /**
     * 电话
     */
    @ApiModelProperty(value = "联系人手机", required = true)
    private String tel;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value = "联系人姓名", required = true)
    private String contactName;


}
