package com.example.zhangtao.phonesafe;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhangtao on 2016/5/18.
 */
public abstract class BaseSetupActivity extends Activity {
    private TextView titleBarTv;//标题栏的文本框
    //下一个Activity
    public void next(View v) {
        nextActivity();
    }

    //上一个Activity
    public void pre(View v) {
        preActivity();
    }

    public abstract void nextActivity();

    public abstract void preActivity();

    //设置标题
    public void setTitleBarTv(String title){
        titleBarTv= (TextView) this.findViewById(R.id.title_bar);
        titleBarTv.setText(title);
    }

    //监听返回键,监听按键，返回键，不销毁当前的Activity，而是返回上一个Activity

//    @Override
//    public void onBackPressed() {
//            super.onBackPressed();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            //返回上一个activity
            preActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
