package com.haoqi.magic.business.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/12/18
 */
@Data
public class OrderAuditVO implements Serializable {
    private Long id;

    private String disputeFirstAuditUser;
    /**
     * 争议初审时间
     */
    private Date disputeFirstAuditTime;
    /**
     * 争议初审备注
     */
    private String disputeFirstAuditRemark;
    /**
     * 争议初审状态(1:通过，2拒绝，3 待审，0默认）
     */
    private Integer disputeFirstAuditStatus;
    /**
     * 争议终审人
     */
    private String disputeFinishAuditUser;
    /**
     * 争议终审时间
     */
    private Date disputeFinishAuditTime;
    /**
     * 争议终审备注
     */
    private String disputeFinishAuditRemark;
    /**
     * 争议终审状态(1:通过，2拒绝，3待审，0：默认）
     */
    private Integer disputeFinishAuditStatus;
}
