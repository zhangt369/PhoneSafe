package com.example.zhangtao.phonesafe;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zhangtao on 2016/5/15.
 */
public class Setup1Activity extends BaseSetupActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        setTitleBarTv("1 欢迎使用手机防盗功能");
    }

    //进入下一个Activity
    @Override
    public void nextActivity() {
        Intent intent = new Intent();
        intent.setClass(this, Setup2Activity.class);
        startActivity(intent);
        finish();
        /**
         * 复写Activity的平移动画，该方法在startActivity和finish方法之后执行
         * 第一个参数，进入的动画
         * 第二个参数，退出的动画
         */
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
    }

    @Override
    public void preActivity() {
        finish();
    }
}
