package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class PayWaysDTO implements Serializable {
    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "logo路径")
    private String logoPath;

    @ApiModelProperty(value = "产品描述")
    private String desc;


}
