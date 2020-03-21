package com.haoqi.magic.business.model.vo;


import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function: 分期数据<br/>
 * Date:     2019/5/5 9:53 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsLoanCreditVO extends Page {

    private static final long serialVersionUID = 4916330277738500973L;

    //主键
    private Long id;

    //姓名
    @ApiModelProperty(value = "姓名",required = true)
    @NotBlank(message = "姓名不能为空")
    private String customerName;

    //客户 手机号
    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String customerTel;

    //身份证
    @ApiModelProperty(value = "身份证",required = true)
    @NotBlank(message = "身份证不能为空")
    private String cardNo;

    //上牌地
    @ApiModelProperty(value = "上牌地",required = true)
    @NotNull(message = "上牌地不能为空")
    private Long sysAreaId;

    //上牌地名称
    @ApiModelProperty(value = "上牌地名称",required = true)
    @NotNull(message = "上牌地名称不能为空")
    private String sysAreaName;

    //【来源于数据字典】工作情况
    @ApiModelProperty(value = "工作情况")
    private String workCode;


    //【来源于数据字典】收入情况
    @ApiModelProperty(value = "收入情况")
    private String incomeCode;

    //经销商id
    private Long csCarDealerId;


    // 状态（1：已提交/2:已处理）
    private Integer status;


    //搜索关键字
    @ApiModelProperty(value = "搜索关键字")
    private String keyWord;

    //申请分期时间--开始
    @ApiModelProperty(value = "申请分期时间--开始")
    private String applyTimeStart;

    //申请分期时间--结束
    @ApiModelProperty(value = "申请分期时间--结束")
    private String applyTimeEnd;

    @ApiModelProperty(value = "经销商名称")
    private String dealerName;
}
