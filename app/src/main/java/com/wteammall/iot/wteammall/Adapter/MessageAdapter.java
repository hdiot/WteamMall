package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Bean.MessageBean.UserMessages;
import com.wteammall.iot.wteammall.R;

/**
 * Created by I0T on 2016/11/23.
 */
public class MessageAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    UserMessages mUserMessages;
    ViewHolder mViewHolder;

    public MessageAdapter(Context context,UserMessages userMessages) {
        this.mContext = context;
        this.mUserMessages = userMessages;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mUserMessages.getmMessageList().size();
    }

    @Override
    public Object getItem(int i) {
        return mUserMessages.getmMessageList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        mViewHolder = new ViewHolder();

        view  = mLayoutInflater.inflate(R.layout.listview_item_per_msg,null);
        mViewHolder.TV_Name = (TextView) view.findViewById(R.id.tv_per_msg_name);
        mViewHolder.TV_Time = (TextView) view.findViewById(R.id.tv_per_msg_time);
        mViewHolder.TV_Type = (TextView) view.findViewById(R.id.tv_per_msg_type);

        mViewHolder.TV_Time.setText(mUserMessages.getmMessageList().get(i).getAcceptTime());
        mViewHolder.TV_Name.setText(mUserMessages.getmMessageList().get(i).getmMessage().getName());
        mViewHolder.TV_Type.setText(mUserMessages.getmMessageList().get(i).getmMessage().getType());


        return view;
    }

    public final class ViewHolder {
        TextView TV_Type;
        TextView TV_Name;
        TextView TV_Time;
    }
}
