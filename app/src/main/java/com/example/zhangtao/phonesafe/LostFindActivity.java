package com.example.zhangtao.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LostFindActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_safe_num)
    private TextView safeNumTv;  //安全号码的文本框
    @ViewInject(R.id.protected_iv)
    private ImageView lockIv;//枷锁图片
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (CacheUtils.getBoolean(context, CacheUtils.PROTECT_SETTING)) { //初始时没有纸的情况，默认为假
            //进入手机防盗的展示界面
            setContentView(R.layout.activity_lost_find);
            ViewUtils.inject(this);
        } else {
            //进入设置向导的界面
            Intent intent = new Intent();
            intent.setClass(context, Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    public void resetup(View v) {
        Intent intent = new Intent();
        intent.setClass(this, Setup1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        //重置手机安全号码
        safeNumTv.setText(CacheUtils.getString(this,CacheUtils.SAFE_NUMBER));
        safeNumTv.setEnabled(false);//不可用

        //初始化是否开启手机防盗功能
        if (CacheUtils.getBoolean(this,CacheUtils.IS_PROTECT)){
            lockIv.setImageResource(R.drawable.lock);
        }else {
            lockIv.setImageResource(R.drawable.unlock);

        }
        super.onStart();
    }
}
