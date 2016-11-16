/**
 *
 */
package com.l000phone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l000phone.asynctask.DownloadImageAysncTask;
import com.l000phone.entity.BaiduNews;
import com.l000phone.entity.NewsItem;
import com.l000phone.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Description：自定义适配器<br/>
 * Copyright (c) , 2016, Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:MyAdapter.java <br/>
 * Date:2016-9-20
 *
 * @author 徐文波
 * @version : 1.0
 */
public class MyAdapter extends BaseAdapter {
    private static final int TYPE_TOTAL = 2;
    private static final int TYPE_ALL = 0;

    private static final int TYPE_IMAGE = 1;
    private List<BaiduNews> ds;
    private Context context;
    private ViewHolder vh;
    private LruCache cache;
    private ExecutorService pool;
	private ViewHolder2 vh2;

	/**
     * @param ds
     * @param context
     */
    public MyAdapter(List<BaiduNews> ds, Context context, LruCache cache , ExecutorService pool) {
        super();
        this.ds = ds;
        this.context = context;
        this.cache = cache;
        this.pool = pool;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {

        return ds.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {

        return ds.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_TOTAL;
    }

    @Override
    public int getItemViewType(int position) {
        return ds.get(position).getType();
    }

    /*
			 * (non-Javadoc)
			 *
			 * @see android.widget.Adapter#getView(int, android.view.View,
			 * android.view.ViewGroup)
			 */
    @SuppressWarnings("null")
    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vh = null;
		vh2 = null;

        if (convertView == null) {
            switch (getItemViewType(position)){
                case TYPE_IMAGE:
                    Log.i("ImageUrl",ds.get(position).getSrc());
                    convertView = View.inflate(context, R.layout.item, null);
                    ((CardView)convertView).setRadius(20.0f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((CardView)convertView).setElevation(20.0f);
                    }
                    vh = new ViewHolder();
				vh.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo_id);
				vh.tvSubjct = (TextView) convertView
                        .findViewById(R.id.tv_subject_id);
				vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_title_id);
				vh.tvTime = (TextView) convertView.findViewById(R.id.tv_time_id);
				convertView.setTag(vh);
					break;
				case TYPE_ALL:
				convertView = View.inflate(context, R.layout.item2, null);
                    ((CardView)convertView).setRadius(20.0f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((CardView)convertView).setElevation(20.0f);
                    }
                    vh2 = new ViewHolder2();
                    vh2.tvSubjct = (TextView) convertView
                            .findViewById(R.id.tv_subject_id);
                    vh2.tvTitle = (TextView) convertView.findViewById(R.id.tv_title_id);
                    vh2.tvTime = (TextView) convertView.findViewById(R.id.tv_time_id);
                    convertView.setTag(vh2);
                    break;
            }
        } else {
           switch (getItemViewType(position)){
               case TYPE_IMAGE:
                   vh = (ViewHolder) convertView.getTag();
                   break;
               case TYPE_ALL:
                   vh2 = (ViewHolder2) convertView.getTag();
                   break;
           }
        }

        BaiduNews perDS = ds.get(position);
        switch (getItemViewType(position)){
            case TYPE_IMAGE:
                vh.tvSubjct.setText(perDS.getTitle());
                vh.tvTitle.setText(perDS.getAuthor());
                vh.tvTime.setText(perDS.getPubDate());

                // 关于图片
                vh.ivPhoto.setImageResource(R.mipmap.ic_launcher);// 默认的图片
                //开启异步任务加载图片
                new DownloadImageAysncTask(context, vh.ivPhoto,cache, pool).execute(perDS.getSrc());
                break;
            case TYPE_ALL:
                vh2.tvSubjct.setText(perDS.getTitle());
                vh2.tvTitle.setText(perDS.getAuthor());
                vh2.tvTime.setText(perDS.getPubDate());
                break;
        }

        return convertView;
    }

    // 每个条目复用实体类
    private final class ViewHolder {
        private ImageView ivPhoto;
        private TextView tvSubjct;
        private TextView tvTitle;
        private TextView tvTime;
    }
    private final class ViewHolder2 {
        private TextView tvSubjct;
        private TextView tvTitle;
        private TextView tvTime;
    }

}
