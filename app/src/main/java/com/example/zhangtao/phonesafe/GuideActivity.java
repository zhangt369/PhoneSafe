package com.example.zhangtao.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhangtao.phonesafe.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private List<ImageView> mPagerList;  //ctrl +alt+f将局部变量抽取为成员变量
    private android.content.Context context;
    private Button mEnterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        context = this;
        //引用视图页
        ViewPager mViewPager = (ViewPager) findViewById(R.id.guide_vp);
        mEnterBtn = (Button) findViewById(R.id.guide_btn);
        //Listview展示批量数据步骤1、列表项布局 2、初始数据List<Map> 3、设置适配器 4、监听列表项
        //1、初始化视图页的数据
        initPager();
        //2、通过适配器装配数据
        mViewPager.setAdapter(new PagerAdapter() {
            //视图页的视图项的数量
            @Override
            public int getCount() {
                return mPagerList == null ? 0 : mPagerList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            /*初始化传递过来的position位置的视图项
             * container：ViewGroup 指ViewPager
             * position：指的是当前要销毁的视图项在adapter中的为位置，也是当前MpagerList的索引号
             * Object:返回值，当前添加的视图项对象
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPagerList.get(position));
                return mPagerList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPagerList.get(position));
            }
        });
        //3、对ViewPager做监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面被选中时，调用该方法，position被选着页面在适配器中的位置，索引号
            @Override
            public void onPageSelected(int position) {
                if (position==(mPagerList.size()-1)){
                    //显示按钮
                    mEnterBtn.setVisibility(View.VISIBLE);
                }else {
                    mEnterBtn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化视图页的数据
    private void initPager() {
        mPagerList = new ArrayList<ImageView>();
        //往集合中添加ImageView

        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(R.drawable.guide_1);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        //使用imageview给viewpager添加图片，用词方法可以使图片铺满全屏，避免出现空隙
        mPagerList.add(imageView1);

        ImageView imageView2 = new ImageView(context);
        imageView2.setImageResource(R.drawable.guide_2);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        mPagerList.add(imageView2);

        ImageView imageView3 = new ImageView(context);
        imageView3.setImageResource(R.drawable.guide_3);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        mPagerList.add(imageView3);
    }
    //点击开始体验按钮
    public void enterSystem(View view){
        Intent intent = new Intent(this,SplashActivity.class);
        startActivity(intent);
        CacheUtils.putBoolean(context,"is_first_use",false);
        finish();
    }

}
