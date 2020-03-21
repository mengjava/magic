package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 基本枚举前端返回值
 *
 * @Author: yanhao
 * @Date: 2019/12/12 9:51
 * @Param:
 * @Description:
 */
@Data
public class CsItemBaseDTO implements Serializable {

    private Integer key;
    private String value;
}
