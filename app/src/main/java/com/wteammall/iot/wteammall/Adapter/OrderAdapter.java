package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wteammall.iot.wteammall.Bean.MyOrderBean;
import com.wteammall.iot.wteammall.R;

import java.util.List;

/**
 * Created by I0T on 2016/11/23.
 */
public class OrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyOrderBean> myOrderBeanList;

    public OrderAdapter(Context context , List<MyOrderBean>list){
        this.mInflater = LayoutInflater.from(context);
        this.myOrderBeanList = list;
    }

    @Override
    public int getCount() {
        return myOrderBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return myOrderBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = mInflater.inflate(R.layout.listview_item_per_order,null);

        return view;
    }
}
