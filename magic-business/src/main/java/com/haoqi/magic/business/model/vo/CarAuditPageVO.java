package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 功能描述:
 * 前端查询车辆照片的vo
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/15 10:36
 * @Description:
 */
@Data
public class CarAuditPageVO extends Page {
    @NotNull(message = "车辆id不能为空")
    private Long carId;
}
