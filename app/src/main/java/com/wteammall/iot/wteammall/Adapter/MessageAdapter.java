package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Bean.MyMessageBean;
import com.wteammall.iot.wteammall.R;

import java.util.List;

/**
 * Created by I0T on 2016/11/23.
 */
public class MessageAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<MyMessageBean> mMessageList;
    ViewHolder mViewHolder;

    public MessageAdapter(Context context,List<MyMessageBean>messagelist) {
        this.mContext = context;
        this.mMessageList = messagelist;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int i) {
        return mMessageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        mViewHolder = new ViewHolder();

        view  = mLayoutInflater.inflate(R.layout.listview_item_per_msg,null);

        mViewHolder.Sender = (TextView) view.findViewById(R.id.tv_per_msg_sender);
        mViewHolder.Content = (TextView) view.findViewById(R.id.tv_per_msg_content);
        mViewHolder.Time = (TextView) view.findViewById(R.id.tv_per_msg_time);

        mViewHolder.Sender.setText(mMessageList.get(i).getSender());
        mViewHolder.Content.setText(mMessageList.get(i).getContext());
        mViewHolder.Time.setText(mMessageList.get(i).getTime());

        return view;
    }

    public final class ViewHolder {
        public TextView Sender;
        public TextView Content;
        public TextView Time;
    }
}
