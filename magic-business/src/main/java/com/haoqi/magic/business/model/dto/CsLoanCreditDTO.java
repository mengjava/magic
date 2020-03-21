package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.DateJsonDeserializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import lombok.Data;

import java.util.Date;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: 分期数据<br/>
 * Date:     2019/5/5 9:58 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsLoanCreditDTO {
    private static final long serialVersionUID = 935055466572348842L;

    private Long id;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date gmtCreate;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date gmtModified;

    private String customerName;

    private String customerTel;

    private String cardNo;

    private Long sysAreaId;

    private String workCode;

    private String incomeCode;

    private Long csCarDealerId;

    private Integer status;

    private String sysAreaName;

    private String workCodeName;

    private String incomeCodeName;

    private String dealerName;

    private String contactName;

    private String tel;

}
