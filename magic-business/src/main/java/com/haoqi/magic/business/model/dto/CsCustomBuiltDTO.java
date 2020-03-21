package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/5 11:35 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCustomBuiltDTO {
    private static final long serialVersionUID = 6724565429232035661L;

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    private Long sysCarBrandId;

    private String sysCarBrandName;

    private Integer age;

    private String colorCode;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String emissionStandardCode;

    private String travelDistance;

    private Long csCarDealerId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date customBuiltTime;

    private String dealerName;

    private String contactName;

    private String tel;

    private String colorCodeName;

    private String emissionStandardCodeName;

    //车辆价格(万元)中文描述（主要是为了车辆定制中的[车辆价格(万元)]）
    private String priceName;
}
