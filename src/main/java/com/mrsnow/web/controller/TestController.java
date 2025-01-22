package com.mrsnow.web.controller;

import com.mrsnow.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dongzhen
 * @CreateTime: 2025-01-21  09:14
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    
    @PostMapping("/hello")
    public R hello(){
        return R.success("hello world！");
    }

    @GetMapping("/hello2")
    public R hello2(){
        return R.success("hello world2！");
    }
}
