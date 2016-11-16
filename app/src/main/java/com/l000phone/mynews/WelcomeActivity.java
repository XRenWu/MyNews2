package com.l000phone.mynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.l000phone.fragment.MyFragment;
import com.l000phone.util.Commen;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager mvp;
    private LinkedList<View> ds;
    private SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("lifeStyle", "----WecomeActivity onCreate----");
        //判断是否是第一次打开如果是第一次打开就加载欢迎界面
        login = getSharedPreferences("login", MODE_PRIVATE);
        setTitle("现在是欢迎界面");
        if (login.getBoolean("isFirstUse",true)){
            setContentView(R.layout.activity_welcome);
            // 步骤：
            // 获得界面上控件的实例
            mvp = (ViewPager) findViewById(R.id.iv_vp_id);

            //关于ViewPager
            aboutViewPager();
        }else {
            //否则就跳转到主界面
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

	/**
	 * 进入主页面并且存储状态到偏好设置里面
     * @param view
     */
    public void enter(View view){
        SharedPreferences.Editor edit = login.edit();
        edit.putBoolean("isFirstUse",false);
        edit.commit();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

	/**
     * 关于ViewPager
     */
    private void aboutViewPager() {
        //数据源
        ds = new LinkedList<>();
        fillds();

        //适配器
        MyPagerAdapter adapter = new MyPagerAdapter();

        //绑定适配器
        mvp.setAdapter(adapter);
    }

	/**
	 * 填充数据源
     */
    private void fillds() {
        Resources resources = getResources();
        String[] imageName = resources.getStringArray(R.array.imageName);
        for (int i =0;i<imageName.length;i++){
            if (i==imageName.length-1){
                View view = View.inflate(this, R.layout.viewpageritem, null);
                ds.add(view);
            }else {
                ImageView view = (ImageView) View.inflate(this, R.layout.welcome_item, null);
                int imageId = resources.getIdentifier(imageName[i], "mipmap", getPackageName());
                view.setImageResource(imageId);
                ds.add(view);
            }

        }

    }

    private final class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return ds.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

		/**
         * 将数据源的每一项和Viewpager中的pager绑定
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //获得当前位置的View实例
            View view = ds.get(position);

            //将获得View实例与ViewPager绑定
            container.addView(view);

            //返回View
            return view;
        }

		/**
         * 移除Viewpager中的View
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(ds.get(position));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifeStyle", "----WecomeActivity onStart----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifeStyle", "----WecomeActivity onResume----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifeStyle", "----WecomeActivity onPause----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifeStyle", "----WecomeActivity onStop----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifeStyle", "----WecomeActivity onDestroy----");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifeStyle", "----WecomeActivity onRestart----");
    }


}