package com.example.zhangtao.phonesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.HomeActivity;
import com.example.zhangtao.phonesafe.R;

/**
 * Created by zhangtao on 2016/5/11.
 */
public class HomeAdapter extends BaseAdapter {
    //图片志愿id数组
    private int[] iconsId = {R.drawable.safe, R.drawable.callmsgsafe,
            R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
            R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
            R.drawable.settings
    };
    //功能项名称数组
    private String[] names = {"手机防盗", "手机卫士", "软件管理", "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"};
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;  //返回names数组长度
    }

    @Override
    public Object getItem(int position) {
        return names[position];//返回功能项名称
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.home_grid_item, parent,
                false);
        //功能名称文本框
        TextView nameTv = (TextView) itemView.findViewById(R.id.tv_grid_name);
        ImageView iconIv = (ImageView) itemView.findViewById(R.id.iv_grid_icon);
        //装配数据
        nameTv.setText(names[position]);
        iconIv.setImageResource(iconsId[position]);
        return itemView;
    }
}
