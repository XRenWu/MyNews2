package com.l000phone.mynews;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.l000phone.fragment.FourFragment;
import com.l000phone.fragment.LiveFragment;
import com.l000phone.fragment.MyFragment;
import com.l000phone.fragment.NewsFragment;
import com.l000phone.sqLiteOpenHelper.MySQLiteOpenHelper;
import com.l000phone.util.Commen;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
	private LruCache<String, Object> cache;
	protected ExecutorService pool;
	private RadioGroup mrg;
	private FragmentManager manger;
	private FourFragment fragment2;
	private LiveFragment fragment1;
	private LiveFragment fragment;
	//private Toolbar mtb;
	private DrawerLayout mdl;
	private SQLiteDatabase db;

	public ExecutorService getPool() {
		return pool;
	}

	public LruCache<String, Object> getCache() {
		return cache;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("lifeStyle", "----MainActivity onCreate----");
		setContentView(R.layout.activity_main);
		// 步骤：

		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "mydb.db", null, 1);

		db = helper.getReadableDatabase();
		//获得FragmentManger的实例
		manger = getSupportFragmentManager();
		FragmentTransaction transaction = manger.beginTransaction();
		transaction.replace(R.id.rl_id,new NewsFragment());
		transaction.commit();
		// 获得界面上控件的实例
		aboutView();

		//构建一个线程池
		pool = Executors.newFixedThreadPool(5);

		//LruCache的获取
		long cacheSize = Runtime.getRuntime().maxMemory() / 8;
		cache = new LruCache<>((int) cacheSize);

		//关于ToolBar
		//aboutToolBar();

		//关于抽屉容器控件
		//aboutDrawerLayout();


		//设置按键
		mrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId){
					case R.id.rb_news:
						FragmentTransaction transaction = manger.beginTransaction();
						transaction.replace(R.id.rl_id,new NewsFragment());
						transaction.commit();
						break;
					case R.id.rb_live_id:
						FragmentTransaction transaction2 = manger.beginTransaction();
						fragment = new LiveFragment();
						Bundle bundle = new Bundle();
						bundle.putString("content","这是推荐界面");
						fragment.setArguments(bundle);
						transaction2.replace(R.id.rl_id, fragment);
						transaction2.commit();
						break;
					case R.id.rb_recommend_id:
						FragmentTransaction transaction3 = manger.beginTransaction();
						fragment1 = new LiveFragment();
						Bundle bundle1 = new Bundle();
						bundle1.putString("content","这是直播界面");
						fragment1.setArguments(bundle1);
						transaction3.replace(R.id.rl_id, fragment1);
						transaction3.commit();
						break;
					case R.id.rb_user_id:
						FragmentTransaction transaction4 = manger.beginTransaction();
						fragment2 = new FourFragment();
						Bundle bundle2 = new Bundle();
						fragment2.setArguments(bundle2);
						transaction4.replace(R.id.rl_id, fragment2);
						transaction4.commit();
						break;
					default:
						break;

				}
			}
		});
	}

//	/**
//	 * 关于抽屉容器控件
//	 */
//	private void aboutDrawerLayout() {
//		//监听器
//		ActionBarDrawerToggle  listener = new ActionBarDrawerToggle(this,mdl,mtb,R.string.open,R.string.close);
//
//		//添加监听器
//		mdl.addDrawerListener(listener);
//
//		//和ToolBar相关联
//		listener.syncState();
//	}

//	public void aboutToolBar(){
//		//设置ToolBar
//		setSupportActionBar(mtb);
//
//		//从ToolBar中获得ActionBar设置向左的箭头
//		ActionBar supportActionBar = getSupportActionBar();
//		supportActionBar.setDisplayShowHomeEnabled(true);
//		supportActionBar.setDisplayHomeAsUpEnabled(true);
//
//		supportActionBar.setTitle("ToolBar演示");
//	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("lifeStyle", "----MainActivity onStart----");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("lifeStyle", "----MainActivity onResume----");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("lifeStyle", "----MainActivity onPause----");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("lifeStyle", "----MainActivity onStop----");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("lifeStyle", "----MainActivity onDestroy----");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i("lifeStyle", "----MainActivity onRestart----");
	}

	/**
	 * 界面初始化实例的获取
	 */
	private void aboutView() {
		//mtb = (Toolbar) findViewById(R.id.tb_id);
		mrg = (RadioGroup) findViewById(R.id.rg_id);
		mdl = (DrawerLayout) findViewById(R.id.dl_id);
	}

	/**
	 * 点击登录
	 * @param view
	 */
	public void login(View view){
		Intent intent = new Intent(this,LoginActivity.class);
		startActivityForResult(intent,200);
	}

	/**
	 * 点击注册
	 * @param view
	 */
	public void regist(View view){

	}

	/**
	 * 点击打开关注页面
	 * @param view
	 */
	public void myAttention(View view){

	}

	/**
	 * 点击打开收藏页面
	 * @param view
	 */
	public void myCollect(View view){

	}

	/**
	 * 点击打开评论页面
	 * @param view
	 */
	public void myComment(View view){

	}

	/**
	 * 点击打开设置页面
	 * @param view
	 */
	public void setting(View view){

	}

	/**
	 * 点击打开意见反馈页面
	 * @param view
	 */
	public void feedback(View view){

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200&&resultCode == 400){
			String result = data.getStringExtra("result");
			String name = data.getStringExtra("name");
			Toast.makeText(this,result,Toast.LENGTH_LONG).show();
		}
	}
}
