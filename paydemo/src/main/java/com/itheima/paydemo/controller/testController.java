package com.itheima.paydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 思觉失调
 * @version 1.0
 * @date 2024-01-29 16:15
 */
@RestController
public class testController {

    @GetMapping("test")
    public String test(){
        return "hello";
    }
}
