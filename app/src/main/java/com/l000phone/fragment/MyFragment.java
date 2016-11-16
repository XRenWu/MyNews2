package com.l000phone.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.l000phone.adapter.MyAdapter;
import com.l000phone.asynctask.DownloadBaiduXMLDataAysncTask;
import com.l000phone.asynctask.DownloadImageAysncTask;
import com.l000phone.asynctask.DownloadXMLDataAysncTask;
import com.l000phone.entity.BaiduNews;
import com.l000phone.entity.NewsItem;
import com.l000phone.mynews.NewsActivity;
import com.l000phone.mynews.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MyFragment extends Fragment implements DownloadBaiduXMLDataAysncTask.XMLDataSettingCallBack {

	private ListView mlv;
	private LinkedList<BaiduNews> newsItems;
	private LruCache<String, Object> cache;
	private ExecutorService pool;
	private TextView mtv;
	private MyAdapter adapter;
	private String strUrl;
	private String name;
//	private RecyclerView mrv;
	private RecyclerView mrv1;
	private Myadapter2 myadapter2;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(name + "lifeStyle", "----MyFragment_onAttach----");
	}

	/*
			 * (non-Javadoc)
			 *
			 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
			 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 从宿主中获取传递过来的值
		Bundle arguments = getArguments();
		strUrl = arguments.getString("url");
		name = arguments.getString("name");
		super.onCreate(savedInstanceState);
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onCreate----");
	}

	public MyFragment(LruCache<String, Object> cache, ExecutorService pool) {
		super();
		this.cache = cache;
		this.pool = pool;
	}

	public MyFragment() {
		super();
	}

	/*
			 * (non-Javadoc)
			 *
			 * @see
			 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
			 * android.view.ViewGroup, android.os.Bundle)
			 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_content, null);
		//mlv = (ListView) view.findViewById(R.id.lv_id);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		mrv1 = (RecyclerView) view.findViewById(R.id.rv_id);
		mrv1.setLayoutManager(linearLayoutManager);
		//mtv = (TextView) view.findViewById(R.id.info_id);
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onCreateView----");
		return view;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// 将从宿主传递过来的值，设置到Fragment对应的视图相应的控件中，予以展现
		//数据源
		newsItems = new LinkedList<>();
		fillDataSource();

		//关于RecyclerView的操作
		aboutRecyclerView();

		//关于ListView的操作

		//适配器
		//adapter = new MyAdapter(newsItems, getActivity(), cache, pool);

		//绑定适配器
		//mlv.setAdapter(adapter);
		//mlv.setEmptyView(mtv);

		//绑定监视器
//		mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent = new Intent(getActivity(), NewsActivity.class);
//				intent.putExtra("url", newsItems.get(position).getLink());
//				startActivity(intent);
//			}
//		});
//		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onActivityCreated----");
		super.onActivityCreated(savedInstanceState);
	}


	/**
	 * 关于RecyclerView的操作
	 */
	private void aboutRecyclerView() {
		//适配器
		myadapter2 = new Myadapter2(newsItems, getActivity(), cache, pool);
		//绑定适配器
		mrv1.setAdapter(myadapter2);
	}

	private class Myadapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
		private static final int TYPE_TOTAL = 2;
		private static final int TYPE_ALL = 0;
		private static final int TYPE_IMAGE = 1;
		private final List<BaiduNews> ds;
		private final Context context;
		private final LruCache cache;
		private final ExecutorService pool;

		public Myadapter2(List<BaiduNews> ds, Context context, LruCache cache , ExecutorService pool){
			this.ds = ds;
			this.context = context;
			this.cache = cache;
			this.pool = pool;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			RecyclerView.ViewHolder viewHolder = null;
			switch (viewType){
				case TYPE_ALL:
					View inflate = View.inflate(getActivity(), R.layout.item2, null);
					((CardView)inflate).setRadius(20.0f);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						((CardView)inflate).setElevation(20.0f);
					}
					viewHolder = new MyViewHolder2(inflate);
					break;
				case TYPE_IMAGE:
					View inflate2 = View.inflate(getActivity(), R.layout.item, null);
					((CardView)inflate2).setRadius(20.0f);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						((CardView)inflate2).setElevation(20.0f);
					}
					viewHolder = new MyViewHolder(inflate2);
					break;
			}
			return viewHolder;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
			switch (getItemViewType(position)){
				case TYPE_ALL:
					View view = ((MyViewHolder2) holder).getView();
					view.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), NewsActivity.class);
							intent.putExtra("url", ds.get(position).getLink());
							startActivity(intent);
						}
					});
					((MyViewHolder2)holder).getMtvsub().setText(ds.get(position).getTitle());
					((MyViewHolder2)holder).getMtv_title().setText(ds.get(position).getAuthor());
					((MyViewHolder2)holder).getMtv_time().setText(ds.get(position).getPubDate());
					break;
				case TYPE_IMAGE:
					View view2 = ((MyViewHolder) holder).getView();
					view2.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), NewsActivity.class);
							intent.putExtra("url", ds.get(position).getLink());
							startActivity(intent);
						}
					});
					((MyViewHolder)holder).getMtvsub().setText(ds.get(position).getTitle());
					((MyViewHolder)holder).getMtv_title().setText(ds.get(position).getAuthor());
					((MyViewHolder)holder).getMtv_time().setText(ds.get(position).getPubDate());

					// 关于图片
					((MyViewHolder)holder).getMiv().setImageResource(R.mipmap.ic_launcher);
					//开启异步任务加载图片
					new DownloadImageAysncTask(context, ((MyViewHolder)holder).getMiv(),cache, pool).execute(ds.get(position).getSrc());
					break;
			}
		}

		@Override
		public int getItemCount() {
			return ds.size();
		}

		@Override
		public int getItemViewType(int position) {
			return ds.get(position).getType();
		}

		class MyViewHolder extends RecyclerView.ViewHolder {
			private final View View;
			private ImageView miv;
			private TextView mtvsub;
			private TextView mtv_title;
			private TextView mtv_time;

			public ImageView getMiv() {
				return miv;
			}

			public android.view.View getView() {
				return View;
			}

			public TextView getMtvsub() {
				return mtvsub;
			}

			public TextView getMtv_title() {
				return mtv_title;
			}

			public TextView getMtv_time() {
				return mtv_time;
			}

			public MyViewHolder(View itemView) {
				super(itemView);
				this.View = itemView;
				this.miv = (ImageView) itemView.findViewById(R.id.iv_photo_id);
				this.mtvsub = (TextView) itemView.findViewById(R.id.tv_subject_id);
				this.mtv_title = (TextView) itemView.findViewById(R.id.tv_title_id);
				this.mtv_time = (TextView) itemView.findViewById(R.id.tv_time_id);
			}
		}
		class MyViewHolder2 extends RecyclerView.ViewHolder{
			private final View View;
			private TextView mtvsub;
			private TextView mtv_title;
			private TextView mtv_time;

			public TextView getMtvsub() {
				return mtvsub;
			}

			public android.view.View getView() {
				return View;
			}

			public TextView getMtv_title() {
				return mtv_title;
			}

			public TextView getMtv_time() {
				return mtv_time;
			}

			public MyViewHolder2(View itemView) {
				super(itemView);
				this.View = itemView;
				this.mtvsub = (TextView) itemView.findViewById(R.id.tv_subject_id);
				this.mtv_title = (TextView) itemView.findViewById(R.id.tv_title_id);
				this.mtv_time = (TextView) itemView.findViewById(R.id.tv_time_id);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onPause----");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onResume----");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onStart----");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onStop----");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onDestroy----");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onDestroyView----");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(name + "lifeStyle", "----" + name + "MyFragment_onDetach----");
	}

	/**
	 * 填充数据源
	 */
	private void fillDataSource() {
		ProgressDialog dialog = new ProgressDialog(getActivity());
		new DownloadBaiduXMLDataAysncTask(this, dialog).execute(new String[]{strUrl, name});
	}


	@Override
	public void setXMLData(LinkedList<BaiduNews> ds) {
		if (ds != null && ds.size() != 0) {
			newsItems.addAll(ds);
		}
		myadapter2.notifyDataSetChanged();
	}
}
