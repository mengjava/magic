package com.haoqi.magic.business.model.dto;


import com.haoqi.magic.business.model.entity.CsParam;
import lombok.Data;

/**
 * ClassName:com.haoqi.magic.system.model.dto <br/>
 * Function: <br/>
 * Date:     2019/4/26 14:24 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsParamDTO extends CsParam {
    private static final long serialVersionUID = 890371954611841618L;

    //【数据字典】车辆类型对应的中文描述
    private String carTypeCodeName;
}
