package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/7 14:02 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class SysDictDTO implements Serializable {

    private static final long serialVersionUID = -4544610890368892553L;
    //key
    private String keyworld;

    //value
    private String valueDesc;

    //备注 储存sql
    private String remark;
}
