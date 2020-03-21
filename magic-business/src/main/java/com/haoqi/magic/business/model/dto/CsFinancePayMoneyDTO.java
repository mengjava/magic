package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haoqi.magic.business.model.vo.BaseFileVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 财务打款表
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Data
public class CsFinancePayMoneyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "打款id")
    private Long id;
    /**
     * 创建时间
     */
    //@JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 注释
     */
    private String remark;
    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "车订单id")
    private Long csCarOrderId;
    /**
     * 打款/收款 日期
     */
    @ApiModelProperty(value = "打款/收款 日期")
    @NotNull(message = "打款/收款不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDate;
    /**
     * 打款金额
     */
    @ApiModelProperty(value = "打款金额")
    @NotNull(message = "打款金额不能为空")
    private BigDecimal payMoney;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    @NotEmpty(message = "银行名称不能为空")
    private String bankName;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    @NotEmpty(message = "银行卡号不能为空")
    private String bankCardNo;
    /**
     * 户名
     */
    @ApiModelProperty(value = "户名")
    @NotEmpty(message = "户名不能为空")
    private String bankUserName;
    /**
     * 类型（1：买家车款，2卖家车款，3收款)
     */
    @ApiModelProperty(value = "类型（1：买家车款，2卖家车款，3收款)")
    @NotNull(message = "收款类型不能为空")
    private Integer payMoneyType;
    /**
     * 【收款专用】收款项类型（1车款+服务费，0卖家退车款）
     */
    @ApiModelProperty(value = "【收款专用】收款项类型（1车款+服务费，0卖家退车款）")
    private Integer receiveItemType;


    @ApiModelProperty(value = "打款附件")
    List<BaseFileVO> files;

    /**
     * 文件类型
     */
    private Integer type;
    /**
     * 1：已打款，0：未打款，默认为0
     */
    @ApiModelProperty(value = "1：已打款，0：未打款")
    private Integer paymentStatus;


    private String operatorUser;

}
