package com.l000phone.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.l000phone.mynews.MainActivity;
import com.l000phone.mynews.R;
import com.l000phone.util.Commen;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NewsFragment extends Fragment {
	private List<Fragment> fragments;
	private String[] tabNames;
	private ViewPager mvp;
	private String[] strUrl;
	private MainActivity activity;
	private TabLayout mtl;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("lifeStyle","----NewsFragment_onAttach----");
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		if (getActivity() instanceof MainActivity){
			activity = (MainActivity)getActivity();
		}
		super.onCreate(savedInstanceState);
		Log.i("lifeStyle","----NewsFragment_onCreate----");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_item, null);
		mvp = (ViewPager) inflate.findViewById(R.id.vp_id);
		mtl = (TabLayout) inflate.findViewById(R.id.tl_id);
		strUrl = getResources().getStringArray(R.array.newsUrl);
		tabNames = getResources().getStringArray(R.array.tabNames);
		Log.i("lifeStyle","----NewsFragment_onCreateView----");
		return inflate;
	}

	private void aboutViewPager() {
		// 数据源
		fragments = new LinkedList<>();
		for (int i = 0; i < tabNames.length; i++) {
			MyFragment fragment = new MyFragment(activity.getCache(), activity.getPool());
			Bundle args = new Bundle();
			args.putString("url", String.format(Commen.commenUrl, strUrl[i]));
			Log.i("StrUrl", String.format(Commen.commenUrl, strUrl[i]));
			args.putString("name", strUrl[i] + ".xml");
			fragment.setArguments(args);
			fragments.add(fragment);
		}

		// 适配器
		FragmentStatePagerAdapter adapter = new MyPagerAdapter(
				getActivity().getSupportFragmentManager());

		// 绑定适配器
		mvp.setAdapter(adapter);

		//设置监视器
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// 关于ViewPager的操作
		aboutViewPager();
		mtl.setupWithViewPager(mvp);
		super.onActivityCreated(savedInstanceState);
		Log.i("lifeStyle","----NewsFragment_onActivityCreated----");
	}

	// FragmentStatePagerAdapter自定义子类
	private final class MyPagerAdapter extends FragmentStatePagerAdapter {

		/**
		 * @param fm
		 */
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int position) {

			return fragments.get(position);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {

			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames[position];
		}

	}
	@Override
	public void onPause() {
		super.onPause();
		Log.i("lifeStyle","----NewsFragment_onPause----");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("lifeStyle","----NewsFragment_onResume----");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i("lifeStyle","----NewsFragment_onStart----");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i("lifeStyle","----NewsFragment_onStop----");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("lifeStyle","----NewsFragment_onDestroy----");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i("lifeStyle","----NewsFragment_onDestroyView----");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i("lifeStyle","----NewsFragment_onDetach----");
	}
}
