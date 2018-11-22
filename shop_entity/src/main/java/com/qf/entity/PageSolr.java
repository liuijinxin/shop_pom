package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/21  20:16
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageSolr<T> implements Serializable {

    private Integer page = 1;
    private Integer pageSize = 8;
    private Integer pageCount;
    private Integer pageSum;
    private List<T> datas;
    private List<Integer> indexs;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
        setIndexs();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs() {
        if(getPageSum() == 0){
            return;
        }
        indexs=new ArrayList<Integer>();
        indexs = new ArrayList<>();
        int begin = Math.max(1, getPage() - 2);
        int end = Math.min(getPageSum(), getPage() + 2);
        for (int i = begin; i <= end; i++){
            indexs.add(i);
        }
    }
}
