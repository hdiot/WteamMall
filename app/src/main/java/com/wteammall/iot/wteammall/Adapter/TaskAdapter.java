package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Bean.TaskBean.Tasks;
import com.wteammall.iot.wteammall.R;

/**
 * Created by I0T on 2016/11/25.
 */
public class TaskAdapter  extends BaseAdapter {

    private Context mContext;
    private Tasks mTasks;
    private LayoutInflater mLayoutInflater;
    private ViewHolder mViewHolder;


    public TaskAdapter(Context context , Tasks tasks){
        this.mContext = context;
        this.mTasks = tasks;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTasks.getTaskList().size();
    }

    @Override
    public Object getItem(int i) {
        return mTasks.getTaskList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        mViewHolder = new ViewHolder();

        view = mLayoutInflater.inflate(R.layout.listview_item_per_task,null);
        mViewHolder.TV_AcceptTime = (TextView) view.findViewById(R.id.tv_task_item_acceptime);
        mViewHolder.TV_Name  = (TextView) view.findViewById(R.id.tv_task_item_name);
        mViewHolder.TV_Points = (TextView) view.findViewById(R.id.tv_task_item_points);

        mViewHolder.TV_Name.setText(mTasks.getTaskList().get(1).getmTask().getName());
        mViewHolder.TV_AcceptTime.setText(mTasks.getTaskList().get(i).getAcceptTime());
        mViewHolder.TV_Points.setText(mTasks.getTaskList().get(i).getmTask().getPoints()+"");

        return view;
    }

    public class ViewHolder{
        TextView TV_Name;
        TextView TV_AcceptTime;
        TextView TV_Points;
    }
}
