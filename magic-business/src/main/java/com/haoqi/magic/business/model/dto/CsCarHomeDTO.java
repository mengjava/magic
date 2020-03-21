package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/29 10:28 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCarHomeDTO implements Serializable {
    private static final long serialVersionUID = 1653231875573766292L;

    //车辆ID
    private Long id;

    //操作人ID（检测员ID）
    private Long checkId;

    //操作人名称（检测员名）
    private String checkLoginName;
    //检测员真实姓名
    private String checkUserName;

    //操作人类型
    private Integer userType;

    //下架类型(1:pc 2:检测员 3:用户自己)
    private Integer pullOffType;
    private String pullOffLoginName;

    private String remark;
}
