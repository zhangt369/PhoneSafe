package com.example.zhangtao.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.ToastUtils;
import com.example.zhangtao.phonesafe.view.SettingView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.security.PublicKey;

public class Setup2Activity extends BaseSetupActivity {
    //注解式申明，取代findViewById引用
    @ViewInject(R.id.bind_sim_sv)
    private SettingView bindSv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        setTitleBarTv("2 绑定SIM卡");
        context = this;
        /**
         *采用Xutils框架的viewUtils模块实现控件的注入
         * 首先引用当前Activity关联的布局的空间的对象，再注入到已经申明注解的控件中
         * 要在 setContentView(R.layout.activity_setup2);之后，先实例化布局布局控件为对象，再注入
         */
        com.lidroid.xutils.ViewUtils.inject(this);

        //对绑定Sim控件SettingView初始化
        if (TextUtils.isEmpty(CacheUtils.getString(this, CacheUtils.SIM, ""))) {
            bindSv.setChecked(false);
        } else {
            bindSv.setChecked(true);
        }

        //对SettingView设置点击监听事件
        bindSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindSv.getChecked()) {
                    bindSv.setChecked(false);
                    CacheUtils.getString(context, CacheUtils.SIM, "");
                } else {
                    bindSv.setChecked(true);
                    //通过系统提供服务来取得sim卡的序列号,先获取电话管理服务
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService
                            (Context.TELEPHONY_SERVICE);
                    String simSerialNumBer = telephonyManager.getSimSerialNumber();
                    //保存sim卡的序列号
                    CacheUtils.getString(context, CacheUtils.SIM, simSerialNumBer);
                    ToastUtils.show(context, "保存的sim序列号：" + simSerialNumBer);
                }
            }
        });
    }

    //下一个activity
    @Override
    public void nextActivity() {
        //要求必须绑定sim卡才能执行下一步
        if (bindSv.getChecked()) {
            Intent intent = new Intent();
            intent.setClass(this, Setup3Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
        } else {
            ToastUtils.show(context, "未绑定sim卡");
        }
    }

    //上一个Activity
    @Override
    public void preActivity() {
        Intent intent = new Intent();
        intent.setClass(this, Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
}
