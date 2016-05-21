package com.example.zhangtao.phonesafe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.view.SettingView;

public class SettingActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_setting);
        final SettingView updateSv = (SettingView) findViewById(R.id.update_sv);
//        if (CacheUtils.getBoolean(context, CacheUtils.APK_UPDATE)) {
//            settingView.setChecked(true);
//            settingView.setDes("自动更新开启");
//        } else {
//            settingView.setChecked(false);
//            settingView.setDes("自动更新关闭 ");
//        }
        //设置自定义的点击监听事件
        updateSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateSv.getChecked()) {
                    updateSv.setChecked(false);
//                    settingView.setDes("自动更新关闭 ");
                    CacheUtils.putBoolean(context, CacheUtils.APK_UPDATE, false);
                } else {
                    updateSv.setChecked(true);
//                    settingView.setDes("自动更新开启");
                    CacheUtils.putBoolean(context, CacheUtils.APK_UPDATE, true);
                }
            }
        });
       final SettingView softSv = (SettingView) findViewById(R.id.soft_sv);
        //设置自定义的点击监听事件
        softSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (softSv.getChecked()) {
                    softSv.setChecked(false);
//                    settingView.setDes("自动更新关闭 ");
                    CacheUtils.putBoolean(context, CacheUtils.APK_UPDATE, false);
                } else {
                    softSv.setChecked(true);
//                    settingView.setDes("自动更新开启");
                    CacheUtils.putBoolean(context, CacheUtils.APK_UPDATE, true);
                }
            }
        });
    }
}
