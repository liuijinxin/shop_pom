package com.qf.shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/19  16:49
 */
@Controller
public class PageController {

    @RequestMapping("/toPage/{param}")
    public String toPage(@PathVariable String param){
        return param;
    }

}
