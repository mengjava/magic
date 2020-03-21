package com.haoqi.magic.business.model.dto;

import com.haoqi.magic.business.model.vo.DisputeItemVO;
import com.haoqi.magic.business.model.vo.RecheckFileVO;
import com.haoqi.magic.business.model.vo.TransferFileVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author twg
 * @since 2019/12/4
 */
@Data
public class OrderCheckAndDisputeDTO implements Serializable {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 复检结果
     */
    private Integer recheckResult;

    /**
     * 争议处理方式
     */
    private Integer disputeProcessType;


    /**
     * 订单争议项
     */
    private List<DisputeItemVO> disputeItems;

    /**
     * 复检信息
     */
    private List<RecheckFileVO> recheckFiles;

    /**
     * 复检结果（正常，异常,1:正常，2：异常，默认0）
     */
    private Integer recheckStatus;

}
