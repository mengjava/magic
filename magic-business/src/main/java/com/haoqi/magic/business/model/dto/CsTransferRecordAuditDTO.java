package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/7 14:38 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsTransferRecordAuditDTO implements Serializable {
    private static final long serialVersionUID = 7077415754065349724L;

    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transferAuditTime;

    //审核备注
    private String remark;
}
