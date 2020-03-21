package com.haoqi.magic.auth.controller;

import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.common.util.CaptchaUtils;
import com.haoqi.rigger.common.util.captcha.LineCaptcha;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * @author twg
 * @since 2019/4/15
 */
@Controller
@RefreshScope
public class HomeController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.prefix:}")
    private String prefix;

    @RequestMapping({"/", "/index", "/home"})
    public String root() {
        return "index";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Value("${application.title:}")
    private String title;

    @RequestMapping("auth-test")
    @ResponseBody
    public String test() {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        return title;
    }

    /**
     * 创建验证码
     *
     * @throws Exception
     */
    @GetMapping("code/{randomStr}")
    public void createCode(@PathVariable String randomStr, HttpServletResponse response)
            throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        LineCaptcha captcha = CaptchaUtils.createCaptcha(100, 38);
        BufferedImage image = captcha.getImage();
        saveCodeImage(randomStr, captcha.getCode());
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        IOUtils.closeQuietly(out);
    }

    private void saveCodeImage(String random, String code) {
        redisTemplate.opsForValue().set(String.format("%s:%s:%s", prefix, SecurityConstants.DEFAULT_CODE_KEY, random), code, CommonConstant.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }
}
