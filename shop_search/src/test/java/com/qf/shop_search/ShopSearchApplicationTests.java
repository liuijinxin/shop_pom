package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void testAdd() throws IOException, SolrServerException {
        /*SolrInputDocument document=new SolrInputDocument();
        document.addField("id",1);
        document.addField("title","美的冰箱");
        document.addField("ginfo","实用的电器产品");
        document.addField("gcount",100);
        document.addField("gprice",200.5);
        document.addField("gimage","www.baidu.com");
        solrClient.add(document);
        solrClient.commit();*/
        for (int i = 1; i < 20; i++) {
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",i);
            document.addField("gtitle","美的冰箱"+i);
            document.addField("ginfo","实用的电器产品"+i);
            document.addField("gcount",100+i);
            document.addField("gprice",200.5+i);
            document.addField("gimage","www.baidu.com"+i);
            solrClient.add(document);
        }
        solrClient.commit();
    }

    @Test
    public void testUpdate() throws IOException, SolrServerException {
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id",20);
        document.addField("gtitle","华为手机");
        document.addField("ginfo","好手机，选华为");
        document.addField("gcount",2562);
        document.addField("gprice",2100.0);
        document.addField("gimage","group1/M00/00/00/wKjmgFv1SPqACP53AACNukNaZ2o980.jpg");
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void testDelete() throws IOException, SolrServerException {
        //根据id删除索引
        /*solrClient.deleteById("0");
        solrClient.commit();*/
        //根据语句删除索引
        /*solrClient.deleteByQuery("gtitle:美的冰箱2");
        solrClient.commit();*/
        //删除所有
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    @Test
    public void testQuery() throws IOException, SolrServerException {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("gtitle:格力空调 || ginfo:格力");

        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            Integer id = Integer.parseInt(result.get("id")+"");
            String title = (String) result.get("gtitle");
            String ginfo = (String) result.get("ginfo");
            Integer gcount = (Integer) result.get("gcount");
            float price = (float) result.get("gprice");
            System.out.println(id + "   " + title + "   " + ginfo + "   " + gcount + "   " + price);
        }

    }

}
