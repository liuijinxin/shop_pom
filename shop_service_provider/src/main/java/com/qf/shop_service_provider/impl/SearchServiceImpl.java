package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.PageSolr;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String queryStr = null;
        //判断传过来的关键字是否为空，为空则搜索全部
        if (keyword == null || keyword.trim().equals("")){
            queryStr = "*:*";
        } else {
            queryStr = String.format("gtitle:%s || ginfo:%s",keyword,keyword);
        }
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryStr);
        //设置关键字的高亮
        solrQuery.setHighlight(true);
        //设置高亮的前后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //设置需要高亮的字段
        solrQuery.addHighlightField("gtitle");
        //设置折叠的次数及前后内容的大小
        solrQuery.setHighlightSnippets(2);
        solrQuery.setHighlightFragsize(5);
        //创建一个集合存放查到的数据
        List<Goods> goodsList=new ArrayList<Goods>();
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
            SolrDocumentList documentList = response.getResults();
            //额外获得高亮结果
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //遍历查到的结果，存到集合中
            for (SolrDocument document : documentList) {
                Integer id = Integer.parseInt(document.get("id")+"");
                String title = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                Integer gcount = (Integer) document.get("gcount");
                String gimage = (String) document.get("gimage");
                float price = (float) document.get("gprice");

                if (highlighting.containsKey(id+"")){
                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    List<String> gtitle = stringListMap.get("gtitle");
                    if(gtitle != null){
                        title = "";
                        for (String str : gtitle)
                            title += str + "...";
                    }
                }
                Goods goods = new Goods(id,title,ginfo,gcount,null,null,(double)price,gimage);
                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public PageSolr<Goods> queryIndexPage(String keyword, PageSolr<Goods> pageSolr) {
        String queryStr = null;
        //判断传过来的关键字是否为空，为空则搜索全部
        if (keyword == null || keyword.trim().equals("")){
            queryStr = "*:*";
        } else {
            queryStr = String.format("gtitle:%s || ginfo:%s",keyword,keyword);
        }
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryStr);
        //设置关键字的高亮
        solrQuery.setHighlight(true);
        //设置高亮的前后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //设置需要高亮的字段
        solrQuery.addHighlightField("gtitle");

        solrQuery.setStart((pageSolr.getPage()-1)*pageSolr.getPageSize());
        solrQuery.setRows(pageSolr.getPageSize());

        List<Goods> goodsList=new ArrayList<Goods>();
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
            SolrDocumentList documentList = response.getResults();

            //获得搜索的总条数
            pageSolr.setPageCount((int) documentList.getNumFound());
            pageSolr.setPageSum(pageSolr.getPageCount() % pageSolr.getPageSize() == 0 ?
                    pageSolr.getPageCount() / pageSolr.getPageSize() :
                    pageSolr.getPageCount() / pageSolr.getPageSize() + 1);

            //额外获得高亮结果
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //遍历查到的结果，存到集合中
            for (SolrDocument document : documentList) {
                Integer id = Integer.parseInt(document.get("id")+"");
                String title = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                Integer gcount = (Integer) document.get("gcount");
                String gimage = (String) document.get("gimage");
                float price = (float) document.get("gprice");

                if (highlighting.containsKey(id+"")){
                    Map<String, List<String>> stringListMap = highlighting.get(id + "");
                    List<String> gtitle = stringListMap.get("gtitle");
                    if(gtitle != null){
                        title = gtitle.get(0);
                    }
                }

                Goods goods = new Goods(id,title,ginfo,gcount,null,null,(double)price,gimage);
                goodsList.add(goods);
            }
            pageSolr.setDatas(goodsList);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageSolr;
    }
}
