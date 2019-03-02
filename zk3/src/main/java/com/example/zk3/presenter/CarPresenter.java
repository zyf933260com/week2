package com.example.zk3.presenter;

import com.example.zk3.bean.CarBean;
import com.example.zk3.bean.XQBean;
import com.example.zk3.contract.CarContract;
import com.example.zk3.model.CarModel;
import com.example.zk3.net.ResponseCallBack;
import com.google.gson.Gson;

import java.util.HashMap;

public class CarPresenter extends CarContract.IPresenter {

    private CarModel model;
    private CarContract.IView view;

    public CarPresenter(CarContract.IView view) {
        this.view = view;
        this.model=new CarModel();
    }

    @Override
    public void car(String url, HashMap<String, String> map) {

        model.car(url, map, new ResponseCallBack() {
            @Override
            public void success(String reslut) {
                if (view!=null){
                    CarBean carBean = new Gson().fromJson(reslut, CarBean.class);
                    view.carsuccess(carBean);
                }
            }

            @Override
            public void failure(String error) {
                if (view!=null){
                    view.defeated(error);
                }
            }
        });
    }

    @Override
    public void xq(String url, HashMap<String, String> map) {
        model.xq(url, map, new ResponseCallBack() {
            @Override
            public void success(String reslut) {
                if (view!=null){
                    XQBean xqBean = new Gson().fromJson(reslut, XQBean.class);
                    view.xqsuccess(xqBean);
                }
            }

            @Override
            public void failure(String error) {
                if (view!=null){
                    view.defeated(error);
                }
            }
        });
    }
}
