package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 手续信息
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCarProcedure extends Model<CsCarProcedure> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建人
     */
    @TableField(value = "creator",fill = FieldFill.INSERT)
    private Long creator;
    /**
     * 修改人
     */
    @TableField(value = "modifier",fill = FieldFill.INSERT_UPDATE)
    private Long modifier;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    /**
     * 注释
     */
    @TableField("remark")
    private String remark;
    /**
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 是否有登记证（1：有，0：无）
     */
    @TableField("have_register_card")
    private Integer haveRegisterCard;
    /**
     * 是否有行驶证（1：有，0：无）
     */
    @TableField("have_drive_card")
    private Integer haveDriveCard;
    /**
     * 年检有效期
     */
    @TableField("valid_date")
    private Date validDate;
    /**
     * 是否有发票(1:有，0：无）
     */
    @TableField("buy_bill")
    private Integer buyBill;
    /**
     * 是否有过户发票（1：有，0：无）
     */
    @TableField("transfer_bill")
    private Integer transferBill;
    /**
     * 交强险有效期
     */
    @TableField("tci_valid_date")
    private Date tciValidDate;
    /**
     * 交强险所在地
     */
    @TableField("tci_position")
    private String tciPosition;
    /**
     * 是否有车船税（1：有：0：无）
     */
    @TableField("have_vehicle_vessel_tax")
    private Integer haveVehicleVesselTax;
    /**
     * 是否有购置税（1：有，0：无）
     */
    @TableField("have_purchase_tax")
    private Integer havePurchaseTax;
    /**
     * 是否有车身铭牌(1:有，0：无）
     */
    @TableField("have_name_plate")
    private Integer haveNamePlate;
    /**
     * 外观改装（1：行驶证与车辆外观相符，0：行驶证与车辆外观不符）
     */
    @TableField("surface")
    private Integer surface;
    /**
     * 是否有违章记录（1：有，0：无）
     */
    @TableField("have_break_rule_record")
    private Integer haveBreakRuleRecord;
    /**
     * 是否有备用钥匙（1：有，0：无）
     */
    @TableField("have_spare_key")
    private Integer haveSpareKey;
    /**
     * 是否有进口关单（1:有，0：无）
     */
    @TableField("have_import_order")
    private Integer haveImportOrder;
    /**
     * 管理车辆基础信息表id
     */
    @TableField("cs_car_info_id")
    private Long csCarInfoId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
