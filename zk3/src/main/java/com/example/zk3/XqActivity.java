package com.example.zk3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zk3.api.Api;
import com.example.zk3.bean.XQBean;
import com.example.zk3.contract.CarContract;
import com.example.zk3.presenter.CarPresenter;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XqActivity extends AppCompatActivity implements CarContract.IView {

    @BindView(R.id.name1)
    TextView name1;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.xbanner)
    XBanner xbanner;
    private CarPresenter carPresenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {


    }

    private void initData() {
        carPresenter = new CarPresenter(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("commodityId", id);
        carPresenter.xq(Api.XQ, map);
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true)
    public void getId(String commodityId) {
        id = commodityId;
    }

    @Override
    public void carsuccess(Object obj) {

    }

    @Override
    public void xqsuccess(Object obj) {
        if (obj != null) {
            XQBean xqBean = (XQBean) obj;
            name1.setText(xqBean.getResult().getCategoryName());
            price.setText(xqBean.getResult().getPrice());
            final List<String> list = new ArrayList<>();
            webview.setWebChromeClient(new WebChromeClient());
            webview.loadDataWithBaseURL(null, xqBean.getResult().getDetails(), "text/html", "utf-8", null);
            String[] split = xqBean.getResult().getPicture().split(",");
            for (String s : split) {
                    list.add(s);
            }
            xbanner.setData(list,null);
            xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, View view, int position) {
                    Glide.with(XqActivity.this).load(list.get(position)).into((ImageView) view);
                }
            });

        }
    }

    @Override
    public void defeated(String error) {

    }

}
