package com.qf.service;

import com.qf.entity.Goods;
import com.qf.entity.PageSolr;

import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/21  16:00
 */
public interface ISearchService {

    int addIndex(Goods goods);

    List<Goods> queryIndex(String keyword);

    PageSolr<Goods> queryIndexPage(String keyword, PageSolr<Goods> pageSolr);

}
