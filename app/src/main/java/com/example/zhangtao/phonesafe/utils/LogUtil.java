package com.example.zhangtao.phonesafe.utils;

import android.util.Log;

/**
 * 日志输出的工具类
 * Created by zhangtao on 2016/5/12.
 */
public class LogUtil {
    private static boolean is_debug = true;

    public static void i(String tag, String msg) {
        if (is_debug) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (is_debug) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (is_debug) {
            Log.d(tag, msg);
        }
    }
}
