package com.haoqi.magic.system.model.dto;

import java.util.List;

public class CarBrand {
    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	private String letter;
    private List<CfCarBrandDTO> list;
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public List<CfCarBrandDTO> getList() {
		return list;
	}
	public void setList(List<CfCarBrandDTO> list) {
		this.list = list;
	}






}
