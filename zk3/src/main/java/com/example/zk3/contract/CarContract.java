package com.example.zk3.contract;

import com.example.zk3.net.ResponseCallBack;

import java.util.HashMap;

public interface CarContract {

    //抽象类
    public abstract class IPresenter{
        public abstract void car(String url, HashMap<String,String> map);
        public abstract void xq(String url, HashMap<String,String> map);

    }
    //Model层
    interface IModel{
        void car(String url, HashMap<String,String> map, ResponseCallBack callBack);
        void xq(String url, HashMap<String,String> map, ResponseCallBack callBack);
    }
    //View 层
    interface IView{
        void carsuccess(Object obj);
        void xqsuccess(Object obj);
        void defeated(String error);
    }
}
