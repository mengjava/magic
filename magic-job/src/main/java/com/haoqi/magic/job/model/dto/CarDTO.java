package com.haoqi.magic.job.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ClassName:com.haoqi.magic.job.model.dto <br/>
 * Function: 车辆<br/>
 * Date:     2019/5/13 11:44 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
@Setter
public class CarDTO implements Serializable {
    private static final long serialVersionUID = 6570798525649216473L;

    //车辆ID
    private Long carId;

}
