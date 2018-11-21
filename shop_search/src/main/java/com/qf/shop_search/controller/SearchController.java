package com.qf.shop_search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/21  12:01
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @Value("${image.path}")
    private String spath;

    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestBody Goods goods){
        System.out.println(goods);
        int i = searchService.addIndex(goods);
        System.out.println(i);
        if ( i==1 ){
            return "succ";
        }
        return "error";
    }

}
