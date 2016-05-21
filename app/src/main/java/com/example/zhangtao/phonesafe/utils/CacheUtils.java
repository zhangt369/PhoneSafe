package com.example.zhangtao.phonesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangtao on 2016/4/27.
 */
//cache时缓存的意思，utils是工具类的意思,通过SharedPrefence缓存数据到xml文件中
public class CacheUtils {
    public static final String CONFIG_SP = "config_sp";//config_sp.xml文件存放位置：（私有数据）
    // /data/data/<<包名>>/shared_prefes
    public static final String IS_FIRST_USE = "is_first_use";//是否第一次使用应用
    public static final java.lang.String APK_UPDATE ="apk_update" ;//是否更新版本
    public static final String SAFE_PASSWORD = "safe_password";//保存手机防盗密码的key
    public static final java.lang.String PROTECT_SETTING ="protect_setting" ;//是否设置手机防盗
    public static final java.lang.String SIM ="sim" ;//sim卡的序列号
    public static final java.lang.String SAFE_NUMBER = "safe_number";
    public static final String IS_PROTECT ="is_protect" ;//是否开启手机防盗
    private static SharedPreferences mSp;

    private static SharedPreferences getPrefences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(CONFIG_SP, Context.MODE_PRIVATE);
        }
        return mSp;
    }

    //保存boolean数据
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getPrefences(context);
        sp.edit().putBoolean(key, value).commit();
    }

    //取出boolean数据，默认返回flase
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getPrefences(context);
        return sp.getBoolean(key, false);
    }

    //取出boolean数据，返回
    public static boolean getBoolean(Context context, String key, boolean defvalue) {
        SharedPreferences sp = getPrefences(context);
        return sp.getBoolean(key, defvalue);
    }

    //保存String
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = getPrefences(context);
        sp.edit().putString(key, value).commit();
    }

    //取出字符串数据，默认返回null
    public static String getString(Context context, String key) {
        SharedPreferences sp = getPrefences(context);
        return sp.getString(key, null);
    }

    //取出字符串数据，返回的时传递过来的值
    public static String getString(Context context, String key, String defvalue) {
        SharedPreferences sp = getPrefences(context);
        return sp.getString(key, defvalue);//获得缺省值
    }
}
