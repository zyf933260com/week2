package com.example.zk3.model;

import com.example.zk3.contract.CarContract;
import com.example.zk3.net.ResponseCallBack;
import com.example.zk3.util.RetfroUtils;
import com.example.zk3.util.RetroCallBack;

import java.util.HashMap;

public class CarModel implements CarContract.IModel {
    @Override
    public void car(String url, HashMap<String, String> map, final ResponseCallBack callBack) {
        RetfroUtils.getInstence().doGet(url, map, new RetroCallBack() {
            @Override
            public void success(String reslut) {
                if (callBack!=null){
                    callBack.success(reslut);
                }
            }

            @Override
            public void failure(String error) {
                if (callBack!=null){
                    callBack.failure(error);
                }
            }
        });
    }

    @Override
    public void xq(String url, HashMap<String, String> map, final ResponseCallBack callBack) {
        RetfroUtils.getInstence().doGet(url, map, new RetroCallBack() {
            @Override
            public void success(String reslut) {
                if (callBack!=null){
                    callBack.success(reslut);
                }
            }

            @Override
            public void failure(String error) {
                if (callBack!=null){
                    callBack.failure(error);
                }
            }
        });
    }
}
