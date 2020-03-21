package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author twg
 * @since 2019/5/14
 */
@Data
public class CarCheckItemDTO implements Serializable {
    /**
     * 检查部位项名称
     */
    private String itemName;

    /**
     * 检查部位项受损程度名称
     */
    private String itemLevel;
}
