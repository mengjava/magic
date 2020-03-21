package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanhao on 2019/11/28.15:49
 */
@Data
public class CsHotCityDTO implements Serializable {

    private String letter;
    private List<CsAppHotCityDTO> list;
}
