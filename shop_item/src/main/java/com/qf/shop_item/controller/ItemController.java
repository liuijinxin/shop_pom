package com.qf.shop_item.controller;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/22  17:03
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @Value("${image.path}")
    private String spath;

    @RequestMapping("/createHtml")
    public String createHtml(@RequestBody Goods goods, HttpServletRequest request){
        Writer out = null;
        try {
            Template template = configuration.getTemplate("goods.ftl");

            //集合存放响应到页面的数据
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("goods",goods);
            map.put("path",spath);
            map.put("context",request.getContextPath());

            //获得classpath路径
            String path = this.getClass().getResource("/static/html/").getPath();
            System.out.println(path);
            out = new FileWriter(path + goods.getId() + ".html");
            template.process(map,out);
        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
