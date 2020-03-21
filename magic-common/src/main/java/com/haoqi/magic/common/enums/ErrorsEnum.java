package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 错误状态枚举
 *
 * @Author: yanhao
 * @Date: 2020-02-21  15:17
 * @Param:
 * @Description:
 */
@Getter
public enum ErrorsEnum {
    ERRORS_300001(300001, "您账户余额不足{money}元，请先充值！"),
    ERRORS_300002(300002, "您未实名绑定银行卡，请先实名绑卡"),
    ERRORS_300003(300003, "您未设置支付密码，请先支付密码!"),
    ERRORS_300004(300004, "您非会员，无法操作，请成为会员"),;

    ErrorsEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
