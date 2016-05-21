package com.example.zhangtao.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.zhangtao.phonesafe.adapter.HomeAdapter;
import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.MD5Utils;
import com.example.zhangtao.phonesafe.utils.ToastUtils;

public class HomeActivity extends AppCompatActivity {
    private Context context;
    private AlertDialog mDialog; //创建的全局变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        /**
         * 用Gridview展示批量数据
         * 1.网格项的布局
         * 2.初始数据，已经确定的数据
         * 3.准备适配器，自动定义的适配器
         * 4.展示与监听（点击网格项的监听）
         */
        GridView gridView = (GridView) findViewById(R.id.home_gv);
        gridView.setAdapter(new HomeAdapter(this));
        //对网格视图实现点击网格项的监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0://手机防盗
                        if (TextUtils.isEmpty(CacheUtils.getString(context, CacheUtils
                                .SAFE_PASSWORD))) {
                            //假如为空，则进入设置初始密码的对话框
                            showSetUpDialog();
                        } else {
                            //先手验证密码的对话框
                            showAuthorDialog();
                        }
                        break;
                    case 1://通信卫士
                        break;
                    case 2://软件管理
                        break;
                    case 3://进程管理
                        break;
                    case 4://流量统计
                        break;
                    case 5://手机杀毒
                        break;
                    case 6://缓存清理
                        break;
                    case 7://高级工具
                        break;
                    case 8://设置中心
                        intent.setClass(context, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    //显示验证密码对话框
    protected void showAuthorDialog() {
        //对话框的而试图界面对象
        View dialogView = View.inflate(context, R.layout.safe_author_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        //通过视图对话框的根节点对象来引用它的控件
        final EditText pwdEt = (EditText) dialogView.findViewById(R.id.et_pwd);
        Button okBtn = (Button) dialogView.findViewById(R.id.btn_ok);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button pwdChangeBtn = (Button) dialogView.findViewById(R.id.btn_pwd_change);
        pwdChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwdEt.getInputType() == 129) {//129代表textpassword的输入方式
                    pwdEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);//明文显示方式
                }else {
                    pwdEt.setInputType(129);
                }
            }
        });
        //对确定按钮设置点击监听事件
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = pwdEt.getText().toString().trim();
                //验证
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.show(context, "密码不能为空");
                } else if (MD5Utils.getMD5(pwd).equals(CacheUtils.getString(context, CacheUtils
                        .SAFE_PASSWORD))) {
                    //验证成功
                    Intent intent = new Intent();
                    intent.setClass(context, LostFindActivity.class);
                    startActivity(intent);
                    finish();
                    mDialog.dismiss();
                } else {
                    ToastUtils.show(context, "密码不正确");
                }
            }
        });

        //取消按钮
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog = builder.create();
        mDialog.setCancelable(false);//true即默认情况下，可以点击对话框意外区域终端对话框，返回键也可以中断，false则不能
        mDialog.show();
    }

    //显示手机防盗的初始密码对话框
    protected void showSetUpDialog() {
        //对话框的而试图界面对象
        View dialogView = View.inflate(context, R.layout.safe_setup_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        //通过视图对话框的根节点对象来引用它的控件
        final EditText pwdEt = (EditText) dialogView.findViewById(R.id.et_pwd);
        final EditText confirmPwdEt = (EditText) dialogView.findViewById(R.id.et_confirm_pwd);
        Button okBtn = (Button) dialogView.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.btn_cancel);
        //对按钮设置点击事件的监听
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = pwdEt.getText().toString().trim();
                String confirmPwd = confirmPwdEt.getText().toString().trim();
                //1.验证密码不能为空
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                    ToastUtils.show(context, "密码不能为空");
                    return;
                }
                //2.两次输入密码一致
                if (pwd.equals(confirmPwd)) {
                    //3.把密码一SharedPreference保存到config.xml文件中
                    CacheUtils.putString(context, CacheUtils.SAFE_PASSWORD, MD5Utils.getMD5(pwd));
                    mDialog.dismiss();
                } else {
                    ToastUtils.show(context, "两次输入的密码不一致");
                }
            }
        });
        //取消按钮
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog = builder.create();
        mDialog.setCancelable(false);//true即默认情况下，可以点击对话框意外区域终端对话框，返回键也可以中断，false则不能
        mDialog.show();
//        builder.show();//该方法包含对话框的创建和显示

    }
}
