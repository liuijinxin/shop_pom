package com.qf.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/19  11:41
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${image.path}")
    private String spath;

    @RequestMapping("/list")
    public String querylAll(Model model){
        System.out.println("GoodsController.querylAll");
        List<Goods> goodsList = goodsService.queryAll();
        model.addAttribute("path",spath);
        model.addAttribute("goodsList",goodsList);
        return "goodslist";
    }

    @RequestMapping("/addGoods")
    public String addGoods(Goods goods, MultipartFile gfile) throws IOException {
        StorePath path = fastFileStorageClient.uploadImageAndCrtThumbImage(
                gfile.getInputStream(),
                gfile.getSize(),
                "jpg", null);
        String fullPath = path.getFullPath();
        goods.setGimage(fullPath);
        goodsService.addGoods(goods);
        return "redirect:/goods/list";
    }

}