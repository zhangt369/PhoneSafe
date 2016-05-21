package com.example.zhangtao.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Setup3Activity extends BaseSetupActivity {
    @ViewInject(R.id.et_safe_num)
    private EditText safeNumEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        setTitleBarTv("3 设置安全号码");
        com.lidroid.xutils.ViewUtils.inject(this);
        safeNumEv.setText(CacheUtils.getString(this, CacheUtils.SAFE_NUMBER));
    }

    //下一个activity,把用户设置的安全号码保存或更新
    @Override
    public void nextActivity() {
        if (TextUtils.isEmpty(safeNumEv.getText().toString().trim())) {
            ToastUtils.show(this, "未设置安全号码");
        } else {//通过SharedPreference保存或更新安全号码
            CacheUtils.putString(this, CacheUtils.SAFE_NUMBER, safeNumEv.getText().toString()
                    .trim());
        }
        Intent intent = new Intent();
        intent.setClass(this, Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
    }

    //上一个Activity
    @Override
    public void preActivity() {
        Intent intent = new Intent();
        intent.setClass(this, Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }

    //选择联系人
    public void selectContact(View v) {
        ToastUtils.show(this, "选择联系人");
    }
}
