package com.example.zhangtao.phonesafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.Constants;
import com.example.zhangtao.phonesafe.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private static final int MSG_SHOW_DIALOG = 1;//显示升级对话框
    private static final int MSG_ENTER_HOME = 100;//进入home界面的标记
    private static final int MSG_SERVER_ERROR = 10000;//显示服务器连接异常
    private TextView mVersionTv;
    private ProgressBar mProgressBar;  //下载apk进度条
    private TextView mTvProcess;  //进度百分比
    private Context context;  //上下文
    private int newVersionCode;  //服务端的版本号
    private String apkUrl;//服务端apk的url地址
    private String versionDes; //服务端apk的版本描述
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_DIALOG:
                    showUpdateDialog();
                    break;
                case MSG_ENTER_HOME:
                    enterHome();
                    break;
                case MSG_SERVER_ERROR:
                    enterHome();
                    break;
                default:
                    break;
            }
        }
    };

    //显示是否要升级APK的对话框
    private void showUpdateDialog() {
        new AlertDialog.Builder(context).setIcon(R.mipmap.ic_launcher)
                .setTitle("新版本：" + newVersionCode)
                .setMessage(versionDes)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //升级apk
                        downloadApk();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterHome();
                    }
                })
                .create()
                .show();
    }

    //下载APK
    protected void downloadApk() {
        HttpUtils httpUtils = new HttpUtils();
        /*
         * url:下载地址
         * target:下载的目标存放地址---  --下载到/mnt/sdcard/android/data/<包名>/cache:外部存储的私有缓存路劲
         * callback：网络请求反馈
         */
        httpUtils.download(apkUrl, getExternalCacheDir().getAbsolutePath() + "/safe.apk",
                new RequestCallBack<File>() {
                    //网络访问成功，返回码200，该方法在主线程进行
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        System.out.println("download-success");
                        installApk();
                    }

                    //网络访问失败，在主线程进行
                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println("download-onFailure");
                        ToastUtils.show(context,"下载安装包失败");
                        enterHome();
                    }

                    /**
                     * 正在下载的方法
                     * @param total：要下载的总的字节数
                     * @param current：当前已经下载的字节数
                     * @param isUploading：是否为上传
                     */
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        //设置进度
                        mProgressBar.setVisibility(View.VISIBLE);
                        mProgressBar.setMax((int)total); //设置进度条的最大值
                        mProgressBar.setProgress((int)current);  //设置进度条的当前值
                        float currentF=current;
                        int process = (int) ((currentF/total)*100);
                        mTvProcess.setText(process+"/100");

                    }
                });
    }

    //安装新下载的Apk
    protected void installApk() {
        //调用系统的安装器安装
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);  //查看
        intent.addCategory(Intent.CATEGORY_DEFAULT);  //添加要访问的组件类别，activity的类别
        /**
         *   设置要传输的数据
         *   data:uri格式的数据
         *   type:数据mime的类型
         */
        intent.setDataAndType(Uri.fromFile(new File(getExternalCacheDir(), "safe.apk")),
                "application/vnd.android.package-archive");
        startActivityForResult(intent, 6);  //要求返回结果
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //希望安装完毕后，跳转到HomeActivity界面
        if (requestCode==6&&resultCode==RESULT_OK){
            enterHome();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initview();
        //根据用户配置判断是否更新版本,缺省值-->默认更新版本
        if (CacheUtils.getBoolean(context, CacheUtils.APK_UPDATE, true)) {
            checkUpdate();//检测是否更新
        } else {
            new Thread() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);  //休眠两秒
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterHome();//进入到HomeActivity
                        }
                    });
                }
            }.start();
        }
    }

    //检测服务端是否有心版本,耗时的操作，开辟一个子线程
    private void checkUpdate() {
        new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();//取得开始访问时间
                //访问网络
                Message message = new Message();
                try {
                    URL url = new URL(Constants.SERVER_VERSION_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int resultCode = conn.getResponseCode();
                    if (resultCode == HttpURLConnection.HTTP_OK) {//访问正常
                        InputStream is = conn.getInputStream();//获取输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String jsonResult = br.readLine();
                        //解析json数据
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        newVersionCode = jsonObject.getInt("code");
                        apkUrl = jsonObject.getString("apkurl");
                        versionDes = jsonObject.getString("des");

                        //比较服务器端的版本号是否比当前的版本号大
                        if (newVersionCode > getVersionCode()) {
                            //弹出是否更新对话框
                            message.what = MSG_SHOW_DIALOG;
                        } else {
                            message.what = MSG_ENTER_HOME;
                        }
                    } else {//访问服务器端有异常
                        message.what = MSG_SERVER_ERROR;
                    }
                } catch (Exception e) {
                    message.what = MSG_SERVER_ERROR;
                    e.printStackTrace();
                } finally {
                    long durationTime = System.currentTimeMillis() - startTime;//访问网络耗时
                    if (durationTime < 2000) {
                        SystemClock.sleep(2000 - durationTime);
                    }
                    //向主线程发消息
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //初始化视图界面
    private void initview() {
        context = this;
        mVersionTv = (TextView) findViewById(R.id.version_tv);
        mProgressBar = (ProgressBar) findViewById(R.id.download_apk_pb);
        mVersionTv.setText("版本号：" + getVersionCode());
        mTvProcess = (TextView) findViewById(R.id.tv_process);
    }

    public int getVersionCode() {
        //取得包管理器
        PackageManager packageManager = getPackageManager();
        /*
         *取得包的信息
         * packName：当前包的名字
         */
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //跳转到HomeActivity界面
    private void enterHome() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish(); //跳转完之后就没有用了，就finish
    }
}