package com.example.zhangtao.phonesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangtao on 2016/4/26.
 */
public class ToastUtils {
    public static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();

    }
}
