package com.example.zhangtao.phonesafe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.R;
import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.LogUtil;

/**
 * Created by zhangtao on 2016/5/12.
 */
public class SettingView extends RelativeLayout {
    private static final String TAG = "SettingView";
    private View rootView;//组合自定义控件对象的根节点
    private TextView mTitleTv;
    private TextView mDesTv;
    private CheckBox mCheckBox;
    private String title;
    private String desOn;
    private String desOff;


    /**
     * 自定义方法操作组合控件的子控件
     * 吧自定义组合控件的xml文件示例化为对象，并且添加到当前对象中作为当前对象的子控件
     */
    public SettingView(Context context) {
        super(context);
        LogUtil.i(TAG, "SettingView,context");
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LogUtil.i(TAG, "SettingView,context, attrs, defStyleAttr, defStyleRes");
        init();
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.i(TAG, "SettingView,context, attrs, defStyleAttr");
        init();
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.i(TAG, "SettingView,context, attrs");
        init();

//        //取得自定义属性
//        int count=attrs.getAttributeCount();
//        for (int i=0;i<count;i++){
//            LogUtil.i(TAG,attrs.getAttributeValue(i));
//        }
        /**
         * 通过命名空间和属性名来获取属性值
         *
         */
        title=attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
               "safe_setting_title");
        desOn=attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","des_on");
        desOff=attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","des_off");

        //初始化自定义控件的属性值
        mTitleTv.setText(title);
        if (CacheUtils.getBoolean(context, CacheUtils.APK_UPDATE)) {
            mCheckBox.setChecked(true);
            mDesTv.setText(desOn);
        } else {
            mCheckBox.setChecked(false);
            mDesTv.setText(desOff );
        }
    }

    //初始化自定义组合控件
    private void init() {
        rootView = View.inflate(getContext(), R.layout.setting_view, this);
        mTitleTv = (TextView) rootView.findViewById(R.id.tv_setting_item_title);
        mDesTv = (TextView) rootView.findViewById(R.id.tv_setting_item_des);
        mCheckBox = (CheckBox) rootView.findViewById(R.id.checkBox);
    }

    //自定义方法
    //设置标题
    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    //设置描述
    public void setDes(String des) {
        mDesTv.setText(des);
    }

    //设置复选项
    public void setChecked(boolean isChecked) {
        mCheckBox.setChecked(isChecked);
        if (isChecked){
            mDesTv.setText(desOn);
        }else {
            mDesTv.setText(desOff);
        }
    }
    //取得组合控件的复选状态
    public boolean getChecked(){
       return mCheckBox.isChecked();
    }
}
