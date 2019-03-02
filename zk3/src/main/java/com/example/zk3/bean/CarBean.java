package com.example.zk3.bean;

import java.util.List;

public class CarBean {

    public String message;
    public String status;
    public List<ResultBean> result;

    public static class ResultBean {
        public int commodityId;
        public String commodityName;
        public int count;
        public String pic;
        public int price;
        public boolean isCheck;
        public int sum;
    }
}
