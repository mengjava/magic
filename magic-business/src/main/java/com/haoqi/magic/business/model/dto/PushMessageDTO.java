package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2018/9/1 0001 10:32
 * @Description:
 */
@Data
public class PushMessageDTO implements Serializable {

    private Long id;
    /**
     * json内容
     */
    @ApiModelProperty(value = "json内容")
    private String jsonContent;

    /**
     * 推送时间
     */
    @ApiModelProperty(value = "推送时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date pushTime;
    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Integer isRead;
    /**
     * 推送内容
     */
    @ApiModelProperty(value = "推送内容")
    private String pushContent;
    /**
     * 推送类型（1：已接单，2：已到店/接单中，3：已完成，4：二手车评估，20其他)
     */
    @ApiModelProperty(value = "推送类型（1：已接单，2：已到店/接单中，3：已完成，4：二手车评估，20其他)")
    private Integer pushType;
    /**
     * 推送类型（1：已接单，2：已到店/接单中，3：已完成，4：二手车评估，20其他)
     */
    @ApiModelProperty(value = "推送类型（1：已接单，2：已到店/接单中，3：已完成，4：二手车评估，20其他")
    private String pushTypeStr;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 公司id
	 */
	@ApiModelProperty(value = "公司id")
	private Long compId;

}
