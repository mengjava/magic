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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息推送表
 * </p>
 *
 * @author yanhao
 * @since 2019-12-10
 */
@Data
@Accessors(chain = true)
public class CsPushMessage extends Model<CsPushMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 公司id
     */
    @TableField("comp_id")
    private Long compId;
    /**
     * 公司名
     */
    @TableField("comp_name")
    private String compName;
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
     * json内容
     */
    @TableField("json_content")
    private String jsonContent;
    /**
     * 推送人
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 推送时间
     */
    @TableField("push_time")
    private Date pushTime;
    /**
     * 是否已读
     */
    @TableField("is_read")
    private Integer isRead;
    /**
     * 推送内容
     */
    @TableField("push_content")
    private String pushContent;
    /**
     * 推送给激光是成功或失败(1:成功，0：失败)
     */
    @TableField("push_result")
    private Integer pushResult;
    /**
     * 推送类型  1买入/2撤销买入通过/ 3撤销买入拒绝（买入）/4卖出/5卖出拒绝
     */
    @TableField("push_type")
    private Integer pushType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
