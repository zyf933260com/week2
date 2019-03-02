package com.example.zk3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zk3.R;
import com.example.zk3.bean.CarBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CarAdapter extends XRecyclerView.Adapter<CarAdapter.CarVH> {


    private Context context;
    private List<CarBean.ResultBean> list;

    public CarAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    //给集合赋值
    public void setList(List<CarBean.ResultBean> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, viewGroup, false);
        CarVH carVH = new CarVH(view);

        return carVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CarVH carVH, final int i) {

        carVH.name.setText(list.get(i).commodityName);
        carVH.price.setText("￥:"+list.get(i).price);
        carVH.tu.setImageURI(list.get(i).pic);
        carVH.checkBox.setChecked(list.get(i).isCheck);
        carVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClik!=null){
                    itemOnClik.onclik(list.get(i).commodityId);
                }
            }
        });
        carVH.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CarVH extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView name,price;
        private SimpleDraweeView tu;

        public CarVH(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.checkbox);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            tu=itemView.findViewById(R.id.tu);

        }
    }

    public ItemOnClik itemOnClik;

    public void setItemOnClik(ItemOnClik itemOnClik) {
        this.itemOnClik = itemOnClik;
    }
    public interface ItemOnClik{
        void onclik(int commodityId);
    }
}
