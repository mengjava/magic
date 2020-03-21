package com.haoqi.magic.system.model.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/11/29 0029 15:27
 * @Description:
 */
@Data
public class SysConfigVO  implements Serializable {
	private static final long serialVersionUID = 946516489152159293L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 创建人
	 */
	private Long creator;
	/**
	 * 修改人
	 */
	private Long modifier;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	/**
	 * 注释
	 */
	private String remark;
	/**
	 * 是否删除 0否1是
	 */
	private Integer isDeleted;
	/**
	 * 值
	 */
	private String globalValue;
	/**
	 * 类别(1:保证金金额，2提现，3违约超时,4争议，5推送）
	 */
	private Integer type;
	/**
	 * 名称（10：跨城拨打电话，11跨城买入，12跨城卖出，13跨城拨打电话未交易退回周期,20间隔提现周期，30买家付款违约周期，31过户超时周期，40初审赔付金额范围，41复检价格，50二次提醒付款周期，60维保成本价，61排放成本价，62出险成本价，63车型识别成本价，64快速估值成本价）
	 */
	private Integer name;

	private String typeStr;

	private String nameStr;

}
