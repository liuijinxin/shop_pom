package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/19  16:52
 */
public interface IGoodsService {

    List<Goods> queryAll();

    Goods addGoods(Goods goods);

    List<Goods> queryNewGoods();
}
