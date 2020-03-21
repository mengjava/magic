package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarConfigDTO implements Serializable {
    private Long id;
    /**
     * 是否有ABS（1:有，0：无）
     */
    @ApiModelProperty(value = "ABS（1:有，0：无）")
    @NotNull(message = "ABS不能为空")
    private Integer haveAbs;
    /**
     * 是否有转向动力（1:有，0：无）
     */
    @ApiModelProperty(value = "转向动力（1:有，0：无）")
    @NotNull(message = "转向动力不能为空")
    private Integer haveTurnEngine;
    /**
     * 气囊
     */
    @ApiModelProperty(value = "气囊")
    @NotBlank(message = "气囊不能为空")
    private String airBag;
    /**
     * 【数据字典】车窗玻璃类型
     */
    @ApiModelProperty(value = "【数据字典】车窗玻璃类型")
    @NotBlank(message = "车窗玻璃不能为空")
    private String windowGlassCode;

    @ApiModelProperty(value = "车窗玻璃类型名")
    private String windowGlassCodeName;
    /**
     * 【数据字典】天窗类型
     */
    @ApiModelProperty(value = "【数据字典】天窗类型")
    @NotBlank(message = "天窗不能为空")
    private String skyWindowCode;

    @ApiModelProperty(value = "天窗类型名")
    private String skyWindowCodeName;
    /**
     * 电动1、手动0
     */
    @ApiModelProperty(value = "车外后视镜（电动1、手动0）")
    @NotNull(message = "车外后视镜不能为空")
    private Integer rearviewMirrorType;
    /**
     * 【数据字典】座椅材料
     */
    @ApiModelProperty(value = "【数据字典】座椅材料")
    @NotBlank(message = "座椅材质不能为空")
    private String seatMaterialCode;

    @ApiModelProperty(value = "座椅材料名")
    private String seatMaterialCodeName;
    /**
     * 【数据字典】座椅调节方式
     */
    @ApiModelProperty(value = "【数据字典】座椅调节方式")
    @NotBlank(message = "座椅调节方式不能为空")
    private String seatAdjustTypeCode;

    @ApiModelProperty(value = "座椅调节方式名")
    private String seatAdjustTypeCodeName;
    /**
     * 【数据字典】座椅功能
     */
    @ApiModelProperty(value = "【数据字典】座椅功能")
    @NotBlank(message = "座椅功能不能为空")
    private String seatFunctionCode;

    @ApiModelProperty(value = "座椅功能名")
    private String seatFunctionCodeName;
    /**
     * 音响设备（0：cd,1:收音机，2dvd)
     */
    @ApiModelProperty(value = "影音设备（0：cd,1:收音机，2dvd)")
    @NotNull(message = "影音设备不能为空")
    private Integer musicType;
    /**
     * 导航（0：无，1：加装，2：原装）
     */
    @ApiModelProperty(value = "导航（0：无，1：加装，2：原装）")
    @NotNull(message = "导航不能为空")
    private Integer navigate;
    /**
     * 定速巡航（0：无，1：加装，2：原装）
     */
    @ApiModelProperty(value = "定速巡航（0：无，1：加装，2：原装）")
    @NotNull(message = "定速巡航不能为空")
    private Integer dlcc;
    /**
     * 【数据字典】倒车雷达
     */
    @ApiModelProperty(value = "【数据字典】倒车雷达")
    @NotBlank(message = "倒车雷达不能为空")
    private String pdcCode;

    @ApiModelProperty(value = "倒车雷达名")
    private String pdcCodeName;
    /**
     * 【数据字典】倒车影像
     */
    @ApiModelProperty(value = "【数据字典】倒车影像")
    @NotBlank(message = "倒车影像不能为空")
    private String rvcCode;

    @ApiModelProperty(value = "倒车影像名")
    private String rvcCodeName;
    /**
     * 【数据字典】轮毂
     */
    @ApiModelProperty(value = " 【数据字典】轮毂")
    @NotBlank(message = "轮毂不能为空")
    private String hubCode;

    @ApiModelProperty(value = "轮毂名")
    private String hubCodeName;
    /**
     * 空调 0手动 1自动
     */
    @ApiModelProperty(value = "空调 0手动 1自动")
    @NotNull(message = "空调不能为空")
    private Integer airCondition;

    private Long csCarInfoId;
}
