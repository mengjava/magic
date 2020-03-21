package com.haoqi.magic.system.model.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 广告配置表
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
@Data
public class SysAdvertConfigVO  extends  Page{




	/**
	 * 广告id
	 */
	@ApiModelProperty(value = "广告id",required = true)
	private Long id;

    /**
     * 标题
     */
    @Length(min = 1, max = 100, message = "广告标题长度必须在1-100之间")
    @ApiModelProperty(value = "标题",required = true)
    private String title;


	/**
	 * 【来自数据字典】投放位置
	 */
	@ApiModelProperty(value = "来自数据字典】投放位置",required = true)
	private String positionCode;


	/**
	 * 图片文件路径
	 */
	@ApiModelProperty(value = "图片文件路径",required = true)
	@NotBlank(message = "广告图片路径不能为空")
	private String picturePath;

	/**
	 * 图片文件名称
	 */
	@ApiModelProperty(value = "图片文件名称",required = true)
	private String pictureName;


	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序",required = true)
	private Integer sort;


	/**
	 * 跳转类型（1：网页，2：本地app页面,0不跳转,网页内编辑）
	 */
	@ApiModelProperty(value = "跳转类型（1：网页，2：本地app页面,0不跳转,网页内编辑）",required = true)
	private Integer jumpType;


	/**
	 * 广告超级链接
	 */
	@ApiModelProperty(value = "广告超级链接",required = true)
	private String linkUrl;


    /**
     * 状态（1：上架，2：下架）
     */
    @ApiModelProperty(value = "状态（1：上架，2：下架）用来进行搜索")
    private Integer status;

	/**
	 * 文本编辑内容
	 */
	@ApiModelProperty(value = "文本编辑内容",required = true)
	private String content;

}
