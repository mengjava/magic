package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarProcedureDTO implements Serializable {
    private Long id;
    /**
     * 是否有登记证（1：有，0：无）
     */
    @ApiModelProperty(value = "登记证（1：有，0：无）")
    @NotNull(message = "登记证不能为空")
    private Integer haveRegisterCard;
    /**
     * 是否有行驶证（1：有，0：无）
     */
    @ApiModelProperty(value = "行驶证（1：有，0：无）")
    @NotNull(message = "行驶证不能为空")
    private Integer haveDriveCard;
    /**
     * 年检有效期
     */
    @ApiModelProperty(value = "年检有效期")
    @NotNull(message = "年检有效期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;
    /**
     * 是否有发票(1:有，0：无）
     */
    @ApiModelProperty(value = "发票(1:有，0：无）")
    @NotNull(message = "购车发票不能为空")
    private Integer buyBill;
    /**
     * 是否有过户发票（1：有，0：无）
     */
    @ApiModelProperty(value = "过户发票（1：有，0：无）")
    @NotNull(message = "过户发票不能为空")
    private Integer transferBill;
    /**
     * 交强险有效期
     */
    @ApiModelProperty(value = "交强险有效期")
    @NotNull(message = "交强险有效期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tciValidDate;
    /**
     * 交强险所在地
     */
    @ApiModelProperty(value = "交强险所在地")
    @NotBlank(message = "交强险所在地不能为空")
    private String tciPosition;
    /**
     * 是否有车船税（1：有：0：无）
     */
    @ApiModelProperty(value = "车船税（1：有：0：无）")
    @NotNull(message = "车船税不能为空")
    private Integer haveVehicleVesselTax;
    /**
     * 是否有购置税（1：有，0：无）
     */
    @ApiModelProperty(value = "购置税（1：有，0：无）")
    @NotNull(message = "购置税不能为空")
    private Integer havePurchaseTax;
    /**
     * 是否有车身铭牌(1:有，0：无）
     */
    @ApiModelProperty(value = "车身铭牌(1:有，0：无）")
    @NotNull(message = "车身铭牌不能为空")
    private Integer haveNamePlate;
    /**
     * 外观改装（1：行驶证与车辆外观相符，0：行驶证与车辆外观不符）
     */
    @ApiModelProperty(value = "外观改装（1：行驶证与车辆外观相符，0：行驶证与车辆外观不符）")
    @NotNull(message = "外观改装不能为空")
    private Integer surface;
    /**
     * 是否有违章记录（1：有，0：无）
     */
    @ApiModelProperty(value = "违章记录（1：有，0：无）")
    @NotNull(message = "违章记录不能为空")
    private Integer haveBreakRuleRecord;
    /**
     * 是否有备用钥匙（1：有，0：无）
     */
    @ApiModelProperty(value = "备用钥匙（1：有，0：无）")
    @NotNull(message = "备用钥匙不能为空")
    private Integer haveSpareKey;
    /**
     * 是否有进口关单（1:有，0：无）
     */
    @ApiModelProperty(value = "进口关单（1:有，0：无）")
    @NotNull(message = "进口关单不能为空")
    private Integer haveImportOrder;

    @ApiModelProperty(value = "车辆id")
    private Long csCarInfoId;
}
