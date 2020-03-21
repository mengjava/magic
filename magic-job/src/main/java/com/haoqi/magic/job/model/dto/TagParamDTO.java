package com.haoqi.magic.job.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ClassName:com.haoqi.magic.job.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/13 11:17 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
@Setter
public class TagParamDTO implements Serializable {

    private static final long serialVersionUID = -164307875546860767L;
    //主键
    private Long tagId;

    //名称
    private String tagName;

    //关联的自定义参数
    private Long paramId;

    //标签对应的自定义参数Sql
    private String sqlStr;



}
