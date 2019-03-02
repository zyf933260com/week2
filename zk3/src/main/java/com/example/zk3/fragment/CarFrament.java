package com.example.zk3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zk3.R;
import com.example.zk3.XqActivity;
import com.example.zk3.adapter.CarAdapter;
import com.example.zk3.api.Api;
import com.example.zk3.bean.CarBean;
import com.example.zk3.contract.CarContract;
import com.example.zk3.presenter.CarPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarFrament extends Fragment implements CarContract.IView,CarAdapter.ItemOnClik{
    @BindView(R.id.car_rv)
    XRecyclerView carRv;  //列表
    @BindView(R.id.checkboxall)
    CheckBox checkboxall; //全选
    @BindView(R.id.hj)
    TextView hj; //合计
    @BindView(R.id.qjs)
    Button qjs;  //结算
    Unbinder unbinder;
    private CarAdapter carAdapter;
    private CarPresenter carPresenter;
    private List<CarBean.ResultBean> result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }


    /**
     * 视图
     *
     * @param view
     */
    private void initView(View view) {
        //布局管理器
        carRv.setLayoutManager(new LinearLayoutManager(getContext()));
        //适配器
        carAdapter = new CarAdapter(getContext());
        carAdapter.setItemOnClik(this);
        //添加适配器
        carRv.setAdapter(carAdapter);
        checkboxall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (CarBean.ResultBean resultBean : result) {
                    resultBean.isCheck=isChecked;
                }

                carAdapter.notifyDataSetChanged();
                zprice();
            }
        });
    }

    private void zprice(){
        double zj=0;
        for (CarBean.ResultBean resultBean : result) {
            if (resultBean.isCheck){
                zj+=resultBean.price;

            }
        }
        hj.setText("￥:"+zj);
    }

    /**
     * 数据
     */
    private void initData() {
        //new 一个P层的实例
        carPresenter = new CarPresenter(this);
        carPresenter.car(Api.CAR,new HashMap<String, String>());
    }

    @Override
    public void carsuccess(Object obj) {
        if (obj!=null){
            CarBean carBean= (CarBean) obj;
            result = carBean.result;
            carAdapter.setList(carBean.result);

        }
    }

    @Override
    public void xqsuccess(Object obj) {

    }

    @Override
    public void defeated(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onclik(int commodityId) {
        EventBus.getDefault().postSticky(commodityId+"");
        startActivity(new Intent(getActivity(),XqActivity.class));
        Toast.makeText(getContext(),"22",Toast.LENGTH_SHORT).show();
    }
}
