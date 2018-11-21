package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/21  16:04
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public int addIndex(Goods goods){
        System.out.println(goods);
        try {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id" ,goods.getId());
            document.addField("gtitle",goods.getTitle());
            document.addField("ginfo",goods.getGinfo());
            document.addField("gcount",goods.getGcount());
            document.addField("gprice",goods.getPrice());
            document.addField("gimage",goods.getGimage());
            solrClient.add(document);
            solrClient.commit();

            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Goods> queryIndex(String keyword) {
        return null;
    }
}
