package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付配置
 * </p>
 *
 * @author mengyao
 * @since 2019-12-12
 */
@Data
@Accessors(chain = true)
public class CsPayConfig extends Model<CsPayConfig> {

    private static final long serialVersionUID = 1L;

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
     * 产品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 产品code
     */
    @TableField("product_code")
    private String productCode;
    /**
     * 支付类别（0：代付，1：协议支付，默认为0）
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 客户展示名称
     */
    @TableField("show_name")
    private String showName;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 分组名
     */
    @TableField("file_group")
    private String fileGroup;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 是否推荐（0：不推荐，1：推荐，默认为0）
     */
    @TableField("recommend")
    private Integer recommend;
    /**
     *支付描述语
     */
    @TableField("pay_desc")
    private String payDesc;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	public CsPayConfig(String productName, String productCode, String filePath) {
		this.productName = productName;
		this.productCode = productCode;
		this.filePath = filePath;
	}

	public CsPayConfig() {
	}
}
