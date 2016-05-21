package com.example.zhangtao.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

public class Setup4Activity extends BaseSetupActivity {
    @ViewInject(R.id.cb_setup4_protect)
    private CheckBox protectCheckBox;//复选控件，实现手机防盗的开启和关闭

    //绑定状态改变的监听
    @OnCompoundButtonCheckedChange(R.id.cb_setup4_protect)
    public void test_saveProtectState(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            CacheUtils.putBoolean(this, CacheUtils.IS_PROTECT, true);
            ToastUtils.show(this, "开启手机保护");
        } else {
            CacheUtils.putBoolean(this, CacheUtils.IS_PROTECT, false);
            ToastUtils.show(this, "关闭手机保护");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        setTitleBarTv("4 设置完成");
        ViewUtils.inject(this);
        //初始化CheckBox
        if (CacheUtils.getBoolean(this, CacheUtils.IS_PROTECT)) {
            protectCheckBox.setChecked(true);
        } else {
            protectCheckBox.setChecked(false);
        }
    }

    //下一个activity
    @Override
    public void nextActivity() {
        //保存已经设置完成
        CacheUtils.putBoolean(this, CacheUtils.PROTECT_SETTING, true);
        ToastUtils.show(this, "设置完成");
        finish();
    }

    //上一个Activity
    @Override
    public void preActivity() {
        Intent intent = new Intent();
        intent.setClass(this, Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
}
