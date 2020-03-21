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
 * 配置信息
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCarConfig extends Model<CsCarConfig> {

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
     * 是否有ABS（1:有，0：无）
     */
    @TableField("have_abs")
    private Integer haveAbs;
    /**
     * 是否有转向动力（1:有，0：无）
     */
    @TableField("have_turn_engine")
    private Integer haveTurnEngine;
    /**
     * 气囊
     */
    @TableField("air_bag")
    private String airBag;
    /**
     * 【数据字典】车窗玻璃类型
     */
    @TableField("window_glass_code")
    private String windowGlassCode;
    /**
     * 【数据字典】天窗类型
     */
    @TableField("sky_window_code")
    private String skyWindowCode;
    /**
     * 电动1、手动0
     */
    @TableField("rearview_mirror_type")
    private Integer rearviewMirrorType;
    /**
     * 【数据字典】座椅材料
     */
    @TableField("seat_material_code")
    private String seatMaterialCode;
    /**
     * 【数据字典】座椅调节方式
     */
    @TableField("seat_adjust_type_code")
    private String seatAdjustTypeCode;
    /**
     * 【数据字典】座椅功能
     */
    @TableField("seat_function_code")
    private String seatFunctionCode;
    /**
     * 音响设备（0：cd,1:收音机，2dvd)
     */
    @TableField("music_type")
    private Integer musicType;
    /**
     * 导航（0：无，1：加装，2：原装）
     */
    @TableField("navigate")
    private Integer navigate;
    /**
     * 定速巡航（0：无，1：加装，2：原装）
     */
    @TableField("dlcc")
    private Integer dlcc;
    /**
     * 【数据字典】倒车雷达
     */
    @TableField("pdc_code")
    private String pdcCode;
    /**
     * 【数据字典】倒车影像
     */
    @TableField("rvc_code")
    private String rvcCode;
    /**
     * 【数据字典】轮毂
     */
    @TableField("hub_code")
    private String hubCode;
    /**
     * 空调 0手动 1自动
     */
    @TableField("air_condition")
    private Integer airCondition;
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
