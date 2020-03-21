package com.haoqi.magic.business;


import com.haoqi.rigger.mybatis.util.CodeAutoGeneratorUtil;

/**
 * @author twg
 * @since 2019/1/8
 */
public class CodeAutoGenerator {
    public static void main(String[] args) {
     /*   CodeAutoGeneratorUtil.generateByTables("mengyao","D:/work/magic/magic-business/src/main/java","com.haoqi.magic.business",
                "cs_cash");*/
        CodeAutoGeneratorUtil.build().generateByTables("yanhao", "E://temp", "com.haoqi.magic.business",
                "", "cs_finance_pay_action");

    }
}