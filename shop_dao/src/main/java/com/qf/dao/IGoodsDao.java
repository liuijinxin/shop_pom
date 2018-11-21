package com.qf.dao;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/19  17:17
 */
public interface IGoodsDao {

    List<Goods> queryAll();

    int addGoods(Goods goods);

    List<Goods> queryNewGoods();
}
