package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author yanhao
 * @since 2019-05-14
 */
@Data
@Accessors(chain = true)
public class CsCarInfo extends Model<CsCarInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建人
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;
    /**
     * 修改人
     */
    @TableField(value = "modifier", fill = FieldFill.INSERT_UPDATE)
    private Long modifier;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
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
     * 车商id
     */
    @TableField("cs_car_dealer_id")
    private Long csCarDealerId;
    /**
     * 车型id
     */
    @TableField("sys_car_model_id")
    private Long sysCarModelId;
    /**
     * 车辆编号(自动生成，唯一标识）
     */
    @TableField("car_no")
    private String carNo;
    /**
     * 【冗余字段】车型名称
     */
    @TableField("sys_car_model_name")
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    @TableField("sys_car_brand_name")
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    @TableField("sys_car_series_name")
    private String sysCarSeriesName;
    /**
     * vin码
     */
    @TableField("vin")
    private String vin;
    /**
     * 初始登记日期
     */
    @TableField("init_date")
    private Date initDate;
    /**
     * 使用性质（1非运营 、2运营 、3营转非 、4租赁 、5教练车 、6家用车 、7出租车 、8公务车、9其他 ）
     */
    @TableField("use_type")
    private Integer useType;
    /**
     * 行驶里程（万公里）
     */
    @TableField("travel_distance")
    private BigDecimal travelDistance;
    /**
     * 表显里程（万公里）
     */
    @TableField("instrument_show_distance")
    private BigDecimal instrumentShowDistance;
    /**
     * 出厂日期
     */
    @TableField("product_date")
    private Date productDate;
    /**
     * 车辆所在地
     */
    @TableField("locate")
    private String locate;
    /**
     * 车辆注册地
     */
    @TableField("register_locate")
    private String registerLocate;
    /**
     * 车牌号
     */
    @TableField("plate_no")
    private String plateNo;
    /**
     * 【来自数据字典】排放标准
     */
    @TableField("emission_standard_code")
    private String emissionStandardCode;
    /**
     * 汽车厂商
     */
    @TableField("car_factory")
    private String carFactory;
    /**
     * 排量
     */
    @TableField("displacement")
    private Double displacement;
    /**
     * 【数据字典】变速箱
     */
    @TableField("gear_box_code")
    private String gearBoxCode;
    /**
     * 【数据字典】车辆类型
     */
    @TableField("car_type_code")
    private String carTypeCode;
    /**
     * 【数据字典】驱动方式
     */
    @TableField("drive_method_code")
    private String driveMethodCode;
    /**
     * 【数据字典】燃油类型
     */
    @TableField("fuel_type_code")
    private String fuelTypeCode;
    /**
     * 【来自数据字典】颜色
     */
    @TableField("color_code")
    private String colorCode;
    /**
     * 座位数
     */
    @TableField("seat_num")
    private Integer seatNum;
    /**
     * 轮胎规格
     */
    @TableField("tyre_type")
    private String tyreType;
    /**
     * 整车型号
     */
    @TableField("car_version")
    private String carVersion;
    /**
     * 发动机号
     */
    @TableField("engine_no")
    private String engineNo;
    /**
     * 过户次数
     */
    @TableField("transfer_num")
    private Integer transferNum;
    /**
     * 过户类型
     */
    @TableField("transfer_type")
    private String transferType;
    /**
     * 现使用方（归属1个人，0单位）
     */
    @TableField("belong_to")
    private Integer belongTo;
    /**
     * 是否进口（1：是，0：否）
     */
    @TableField("import_type")
    private Integer importType;
    /**
     * 是否有备胎（1:有，0：无）
     */
    @TableField("spare_wheel")
    private Integer spareWheel;
    /**
     * 零售价格（元）
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 新车指导价格（元）
     */
    @TableField("suggest_price")
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    @TableField("wholesale_price")
    private BigDecimal wholesalePrice;
    /**
     * 是否有一口价（1：有，0：无）
     */
    @TableField("have_fixed_price")
    private Integer haveFixedPrice;
    /**
     * 是否有促销价格（1：有，0：无）
     */
    @TableField("have_promote_price")
    private Integer havePromotePrice;
    /**
     * 买方信息
     */
    @TableField("buyer_info")
    private String buyerInfo;
    /**
     * 卖方信息
     */
    @TableField("seller_info")
    private String sellerInfo;
    /**
     * 其他信息
     */
    @TableField("other_info")
    private String otherInfo;
    /**
     * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
     */
    @TableField("publish_status")
    private Integer publishStatus;
    /**
     * 【冗余字段】调拨状态（1：已申请，0：未申请，2：申请通过，-1：申请拒绝，-2取消）
     */
    @TableField("transfer_status")
    private Integer transferStatus;
    /**
     * 浏览次数
     */
    @TableField("scan_num")
    private Integer scanNum;
    /**
     * 审核时间/上架时间
     */
    @TableField("audit_time")
    private Date auditTime;
    /**
     * 提交时间/发布时间
     */
    @TableField("publish_time")
    private Date publishTime;
    /**
     * 下架时间
     */
    @TableField("pull_off_time")
    private Date pullOffTime;
    /**
     * 浏览次数基数，随机生成
     */
    @TableField("scan_base_num")
    private Integer scanBaseNum;
    /**
     * 左前45度缩略文件名
     */
    @TableField("icon_file_name")
    private String iconFileName;
    /**
     * 左前45度缩略分组名
     */
    @TableField("icon_file_group")
    private String iconFileGroup;
    /**
     * 左前45度缩略文件路径
     */
    @TableField("icon_file_path")
    private String iconFilePath;

    /**
     * 检测时间
     */
    @TableField("check_time")
    private Date checkTime;

    /**
     * 检测员id
     */
    @TableField("check_user_id")
    private Long checkUserId;

    /**
     * 检测员用户名称
     */
    @TableField("check_login_name")
    private String checkLoginName;

    /***
     * 排量类型 : 0L 1T
     */
    @TableField("displacement_type")
    private String displacementType;

    /**
     * 管理员调拨处理时间
     */
    @TableField("transfer_handle_time")
    private Date transferHandleTime;

    /**
     * 车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）
     */
    @TableField("trade_flag")
    private Integer tradeFlag;
    /**
     * 1------两厢车 ， 2------3厢车
     */
    @TableField("car_trunk_type")
    private Integer carTrunkType;

    /**
     * 车模型图片名称
     */
    @TableField("car_model_file_name")
    private String carModelFileName;
    /**
     * 车模型图片分组名
     */
    @TableField("car_model_file_group")
    private String carModelFileGroup;
    /**
     * 车模型图片文件路径
     */
    @TableField("car_model_file_path")
    private String carModelFilePath;

	/**
	 * 维保URL
	 */
	@TableField("maintenance_url")
	private String maintenanceUrl;


	/**
	 * 出险URL
	 */
	@TableField("insurance_url")
	private String insuranceUrl;

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

}
