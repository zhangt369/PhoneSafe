package com.example.zhangtao.phonesafe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.utils.LogUtil;

/**
 * 自定义可以获取焦点的TextView
 * Created by zhangtao on 2016/5/11.
 */
public class FocusableTextView extends TextView {
    private static final String TAG = "FocusableTextView";

    //在代码中国调用，比如new FocysableTextView();
    public FocusableTextView(Context context) {
        super(context);
        LogUtil.i(TAG, "FocusableTextView:content");
    }
    //在布局中吧xml文件转换为对象，则调用该构造方法
    public FocusableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.i(TAG, "FocusableTextView:attrs ");
    }

    public FocusableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.i(TAG, "FocusableTextView:3333 ");

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FocusableTextView(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LogUtil.i(TAG, "FocusableTextView:4444444 ");

    }

    //默认返回的时假，不获取焦点
    @Override
    @ViewDebug.ExportedProperty(category = "focus" )
    public boolean isFocused() {
        return true; 
    }
}
