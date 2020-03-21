package com.haoqi.magic.system.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/4/28 0028 16:57
 * @Description:
 */
@Data
public class SysAreaDTO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 省份code
	 */
	private String provinceCode;

}
