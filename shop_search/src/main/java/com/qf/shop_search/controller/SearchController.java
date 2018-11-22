package com.qf.shop_search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.PageSolr;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        int i = searchService.addIndex(goods);
        if ( i==1 ){
            return "succ";
        }
        return "error";
    }

    @RequestMapping("/list")
    public String queryIndex(String keyword, Model model) {
        List<Goods> goodsList = searchService.queryIndex(keyword);
        model.addAttribute("goodsList",goodsList);
        model.addAttribute("path",spath);
        return "searchlist";
    }

    @RequestMapping("/listPage")
    public String queryIndexPage(String keyword, Model model, PageSolr<Goods> pageSolr) {
        pageSolr = searchService.queryIndexPage(keyword, pageSolr);
        model.addAttribute("pageSolr",pageSolr);
        model.addAttribute("keyword",keyword);
        model.addAttribute("path",spath);
        return "searchlist";
    }

}
