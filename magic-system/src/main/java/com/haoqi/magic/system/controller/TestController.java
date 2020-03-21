package com.haoqi.magic.system.controller;

import com.haoqi.rigger.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twg
 * @since 2019/4/21
 */
@RequestMapping("test")
@RestController
@RefreshScope
public class TestController extends BaseController {

    @Value("${title:}")
    private String title;

    @GetMapping("index")
    public String index(){
        return title;
    }
}
