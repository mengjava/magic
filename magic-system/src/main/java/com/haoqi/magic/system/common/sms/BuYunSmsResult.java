package com.haoqi.magic.system.common.sms;

import lombok.Data;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * ClassName:com.haoyun.common.utils <br/>
 * Function: <br/>
 * Date:     2018/5/17 14:32 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class BuYunSmsResult {


    public enum codeEnum {
        CODE_0("0", "发送成功"),
        CODE_101("101", "无此用户"),
        CODE_102("102", "密码错"),
        CODE_103("103", "提交过快（提交速度超过流速限制）"),
        CODE_104("104", "系统忙（因平台侧原因，暂时无法处理提交的短信）"),
        CODE_105("105", "敏感短信（短信内容包含敏感词）"),
        CODE_106("106", "消息长度错（>536或<=0）"),
        CODE_107("107", "包含错误的手机号码"),
        CODE_108("108", "手机号码个数错（群发>50000或<=0;单发>200或<=0）"),
        CODE_109("109", "无发送额度（该用户可用短信数已使用完）"),
        CODE_110("110", "不在发送时间内"),
        CODE_111("111", "超出该账户当月发送额度限制"),
        CODE_112("112", "无此产品，用户没有订购该产品"),
        CODE_113("113", "extno格式错（非数字或者长度不对）"),
        CODE_115("115", "自动审核驳回"),
        CODE_116("116", "签名不合法，未带签名（用户必须带签名的前提下）"),
        CODE_117("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址"),
        CODE_118("118", "用户没有相应的发送权限"),
        CODE_119("119", "用户已过期"),
        CODE_120("120", "内容不在白名单模板中"),
        CODE_121("121", "相同内容短信超限"),
        CODE_201("201", "公共服务中未设置验证码短信模板"),
        CODE_301("301", "公共服务中未设置派单短信模板"),
        CODE_999("999", "具体错误见errMsg"),
        ;

        private String code;
        private String msg;

        codeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static String getMsg(String key) {
            codeEnum[] codeenum = codeEnum.values();
            for (codeEnum e : codeenum) {
                if (key.equals(e.getCode())) {
                    return e.getMsg();
                }
            }
            return "";
        }
    }

    //状态码
    protected String code;

    //状态信息
    protected String errMsg;

    //信息ID
    private String msgId;

    //响应时间
    private String responseTime;

    //短信内容
    private String content;

    BuYunSmsResult() {
        this.code = "";
        this.errMsg = "";
        this.msgId = "";
        this.responseTime = "";
    }

    public BuYunSmsResult(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public BuYunSmsResult parseFromHTTPResponse(String res, boolean flag) throws DocumentException {

        if (!flag) {
            if (res.indexOf("\n") >= 2) {
                String[] re = res.split("\n");
                if (null != re && re.length == 2) {
                    String[] ree = re[0].split(",");
                    if (null != ree && ree.length == 2 && ree[1].equals(codeEnum.CODE_0.getCode())) {
                        code = codeEnum.CODE_0.getCode();
                        errMsg = codeEnum.CODE_0.getMsg();
                        responseTime = ree[0];
                        msgId = re[1];
                    }
                }
            } else {
                String[] re = res.split(",");
                if (null != re && re.length == 2) {
                    responseTime = re[0];
                    code = re[1];
                    errMsg = codeEnum.getMsg(re[1]);
                }
            }
        } else {
            Document doc = DocumentHelper.parseText(res);
            Element root = doc.getRootElement();
            String status = root.elementTextTrim("returnstatus");
            if( status.equals("Success") ) {
                code = "0";
                errMsg = root.elementTextTrim("message");
                msgId = root.elementTextTrim("taskID");
            }else {
                code = codeEnum.CODE_999.getMsg();
                errMsg = root.elementTextTrim("message");
                msgId = root.elementTextTrim("taskID");
            }
        }
        return this;
    }
}
