package com.haoqi.magic.system.common.sms;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ClassName:com.haoyun.common.util.sms <br/>
 * Function: 调用步云短信发送接口<br/>
 * Date:     2018/5/17 15:59 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Data
public class BuYunSmsUtil {

    private String appId;

    /**
     * 用户名
     */
    private String appkey;

    /**
     * 秘钥（此处指密码）
     */
    private String appsecret;

    /**
     * 短信发送请求地址
     */
    private String url;


    private static final Pattern TELPHONEREG =
            Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|16[0-9]|17[0-9]|19[0-9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
    private static RequestConfig defaultRequestConfig =
            RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
    private static CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();




    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param content 短信类容
     * @param extno
     * @param product
     * @return
     * @author huming
     * @date 2018/5/17 16:20
     */
    public BuYunSmsResult buYunSendSms(String mobile, String content, String extno, String product) {
        CloseableHttpResponse httpResponse = null;
        try {
            if (!TELPHONEREG.matcher(mobile).matches()){
                return new BuYunSmsResult(com.haoqi.magic.system.common.sms.BuYunSmsResult.codeEnum.CODE_107.getCode(),BuYunSmsResult.codeEnum.CODE_107.getMsg());
            }
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));
            httpPost.setHeader("Accept", "application/text");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action","send"));
            params.add(new BasicNameValuePair("userid",appId));
            params.add(new BasicNameValuePair("account", appkey));
            params.add(new BasicNameValuePair("pswd", appsecret));
            params.add(new BasicNameValuePair("password",appsecret));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("msg", URLEncoder.encode(content, "UTF-8")));
            params.add(new BasicNameValuePair("content",content));
            params.add(new BasicNameValuePair("needstatus", "true"));
            params.add(new BasicNameValuePair("extno", extno));
            params.add(new BasicNameValuePair("product", product));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpResponse = httpclient.execute(httpPost);
            HttpEntity ent = httpResponse.getEntity();
            int headCode = httpResponse.getStatusLine().getStatusCode();
            log.info("[BuYunSmsUtil buYunSendSms return code] mobile : headCode : {}", mobile + ":" + headCode);
            if (headCode == 200) {
                String body1 = EntityUtils.toString(ent, Charset.forName("utf-8"));
                return (new BuYunSmsResult()).parseFromHTTPResponse(body1,false);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                    httpResponse = null;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
