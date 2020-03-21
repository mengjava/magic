package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:com.haoqi.magic.system.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/8 11:24 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsFilterDTO implements Serializable {
    private static final long serialVersionUID = -2062943633387993739L;
    /**
     * 主键
     */
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    /**
     * 筛选名称
     */
    private String filterName;
    /**
     * 1车系，2品牌，3价格
     */
    private Integer filterType;
    /**
     * 排序号
     */
    private Integer orderNo;
    /**
     * 品牌id
     */
    private Long sysCarBrandId;
    /**
     * 【冗余】品牌名称
     */
    private String sysCarBrandName;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 分组名
     */
    private String fileGroup;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 车系id
     */
    private Long sysCarSeries;
    /**
     * 【冗余】车系名称
     */
    private String sysCarSeriesName;
    /**
     * 最小价格（单位：万元）
     */
    private BigDecimal minPrice;
    /**
     * 最大价格（单位：万元）
     */
    private BigDecimal maxPrice;

    private String pictureURL;
}
