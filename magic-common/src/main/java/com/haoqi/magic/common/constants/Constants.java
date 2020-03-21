package com.haoqi.magic.common.constants;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
public interface Constants {


    /**
     * 短信发送成功状态码
     */
    String SMS_SUCCESS_CODE = "0";

    /**
     * 短信验证码模板唯一标识
     */
    String TEMPLATE_CODE_SMS = "SMS_VALIDATE_CODE";


    /**
     * 重置密码-模板唯一标识
     */
    String SMS_RESET_PASSWORD = "SMS_RESET_PASSWORD";

    /**
     * 审核拒绝短信-模板唯一标识
     */
    String SMS_AUDIT_REJECTION = "SMS_AUDIT_REJECTION";


    /**
     * 验证码有效时间
     */
    int NUMBER60 = 60;

    /**
     * 超出短信条数限制时间
     */
    int HOURS24 = 24 * 60 * 60;


    /**
     * 默认24小时短信条数
     */
    int DEFAULT_SMS_NUM = 10;

    /**
     * 是
     */
    Integer YES = 1;
    /**
     * 否
     */
    Integer NO = 0;
    /**
     * 2
     */
    Integer TWO = 2;
    /**
     * 私密
     */
    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ7CZ2W4POpr29D2IzHJJ0K+VYLqURL25njezE5BpBToHokmvPMXE2DZSrZEVefsn9/SsVIn1S6EZRH4FEsL+9oJu6JIy8zSOwXHJi5FlvGu1pUzWBldUq/qUDZdlr2ddzhE7yDX+1O5QyfWlb2IUF5uEu4AZQ+tOWv7dn5QlxDrAgMBAAECgYBTnOd/9yx9+hhnUXrvuZVn3X6D2IyihE6V0iGgBYo5SJbsfO5+yOR32XkzzYHBCCqbYwfo0PnB6hChChsF+2XEd4erkFm/QrvcvlnMlSdg38fzP8HLu2ft0sWtyV4Em2rKtsjY1wwGcVM2KzoDIwYXoAOXhauz0Tp0fO419UpFgQJBAMsfsV2jwFQPUnu+dld5gHgDh7MccGSdJehebibNMUyz61En64rl7fHkj4i6Px/7pYATbtzsH20xsApjJ3nrwbECQQDIFjyhL5ZS86mbR9V8lAH/T4PB4RbZxL/VegpR7zYIM210vt/cGox0fwZw7ESblkwfqsUdVkaier3vGPRgS2dbAkBBrpXj+bePdwTtDsGlt5xbMokG2gNeBZLkeOSVl3SBoQxOyeHYoFE5Dvd69v7CkNULfT00IwZmgNK0CSwSuLGBAkEAkakOjlE0YEMeb/rFgiHFqnXad2RD3FkNRn3H0YoRjbrSrjDeiz+QjKaEq+R0tceet1b8lLTgSxyL9On3zbpXDQJBAKdM88mj1OfpQzdxuZiGtZa3jds2pmoqMf6+RkLQY/zVyhj1ybXZQgN9BZTsIhNnnhqfrVpEX7aU9/iKCrVp0rA=";

    /**
     * 0
     */
    Long ZEOR = 0L;


    /**
     * 登录code
     */
}
