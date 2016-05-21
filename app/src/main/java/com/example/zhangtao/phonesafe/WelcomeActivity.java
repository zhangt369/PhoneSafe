package com.example.zhangtao.phonesafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;
import com.example.zhangtao.phonesafe.utils.ToastUtils;

public class WelcomeActivity extends AppCompatActivity {
//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //引用图片控件
        ImageView welcomeImageView = (ImageView) findViewById(R.id.iv_Welcome);
        //设置动画，动画的集合
        AnimationSet animationSet = new AnimationSet(false);

        //动画1、旋转动画
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(3000);
        ra.setFillAfter(true);   //设置最终的状态为填充效果
        animationSet.addAnimation(ra);

        //动画2、缩放动画
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(3000);
        sa.setFillAfter(true);
        animationSet.addAnimation(sa);

        /*动画3、透明度动画
         *使用xml布局动画
         */
        Animation alpha = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        animationSet.addAnimation(alpha);

        //监听动画
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                Toast.makeText(getApplicationContext(),"喜羊羊",Toast.LENGTH_SHORT).show();
                ToastUtils.show(getApplicationContext(), "喜羊羊");
                if (CacheUtils.getBoolean(WelcomeActivity.this,CacheUtils.IS_FIRST_USE,true)){
                //动画结束进入导航界面
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();  //欢迎界面设置standard模式 ，销毁
                }else {
                    //进入SplashActivity界面
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();  //欢迎界面设置standard模式 ，销毁
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //展示动画
        welcomeImageView.startAnimation(animationSet);
    }
}
