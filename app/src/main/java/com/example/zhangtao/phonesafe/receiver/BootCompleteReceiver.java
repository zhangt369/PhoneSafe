package com.example.zhangtao.phonesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.example.zhangtao.phonesafe.utils.CacheUtils;

/**
 * Created by zhangtao on 2016/5/17.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    /**
     * 在主线程中运行，且生命周期比较短，所以不适合生命周期长的业务
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //取得当前sim卡的串号
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        String storedSimSerialNumber = CacheUtils.getString(context, CacheUtils.SIM, "");
        if (!simSerialNumber.equals(storedSimSerialNumber)) {
            //发短信给安全号码
            String safeNum = CacheUtils.getString(context, CacheUtils.SAFE_NUMBER, "");
            SmsManager smsManager = SmsManager.getDefault();//取得短信管理器
            smsManager.sendTextMessage(safeNum,null,"Boss,I lost",null,null);//发短信需要扣费
        }
    }
}
