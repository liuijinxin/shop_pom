package com.qf.shop_service_provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceProviderApplicationTests {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource(){
        System.out.println(dataSource);
    }

    @Test
    public void testQueryAll() {
        List<Goods> goodsList = goodsService.queryAll();
        for (Goods goods : goodsList) {
            System.out.println(goods);
        }
    }

}
