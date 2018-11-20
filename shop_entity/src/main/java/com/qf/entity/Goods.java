package com.qf.entity;

import lombok.*;

import java.io.Serializable;
/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/19  12:20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods implements Serializable {

    private Integer id;

    private String title;

    private String ginfo;

    private Integer gcount;

    private Integer tid=1;

    private Double allprice;

    private Double price;

    private  String gimage;

}
