package com.haoqi.magic.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mengyao
 * @since 2019-04-25
 */
@Data
public class SysAdvertDTO implements Serializable {

    private Long id;
    private String title;
    private String pictureURL;
    private String picturePath;
    private String positionCode;
    private String pictureName;
    private String linkUrl;
    private Integer sort;
    private Integer jumpType;
    private Integer status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtModified;
}
