package com.haoqi.magic.system.model.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
@Data
@Accessors(chain = true)
public class SysConfig extends Model<SysConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建人
     */
    @TableField("creator")
    private Long creator;
    /**
     * 修改人
     */
    @TableField("modifier")
    private Long modifier;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modified")
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
     * 值
     */
    @TableField("global_value")
    private String globalValue;
    /**
     * 类别(1:保证金金额，2提现，3违约超时,4争议，5推送 6 车况查询，7电话）
     */
    @TableField("type")
    private Integer type;
    /**
     * 名称（10：买入，12卖出，20最低提现金额，21 提现模式 30买家付款违约周期，31过户超时周期，40终审赔偿金额，50二次提醒付款周期，60维保成本价，61排放成本价，62出险成本价，63车型识别成本价，64快速估值成本价，70客服电话）
     */
    @TableField("name")
    private Integer name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
