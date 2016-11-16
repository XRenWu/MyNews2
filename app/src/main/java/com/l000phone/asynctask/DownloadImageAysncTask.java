/**
 *
 */
package com.l000phone.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.l000phone.util.ImageLoadUtils;

import java.util.concurrent.ExecutorService;

/**
 * Description：自定义下载图片的异步任务类<br/>
 * Copyright (c) , 2016, Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:DownloadImageAysncTask.java <br/>
 * Date:2016-9-20
 *
 * @author 徐文波
 * @version : 1.0
 */
public class DownloadImageAysncTask extends AsyncTask<String, Void, Void> {
    private Context context;
    private ImageView ivPic;
    private LruCache cache;
    private ExecutorService pool;


    public DownloadImageAysncTask(Context context, ImageView ivPic, LruCache cache, ExecutorService pool) {
        this.context = context;
        this.ivPic = ivPic;
        this.cache = cache;
        this.pool = pool;
    }

    /*
             * (non-Javadoc)
             *
             * @see android.os.AsyncTask#doInBackground(Params[])
             */
    @Override
    protected Void doInBackground(String... params) {
        //loadImageFromServerOrDevice(final String picUrl, final Context context, ImageView ivPic)
        ImageLoadUtils.loadImageFromServerOrDevice(params[0], context, ivPic, cache, pool);
        return null;
    }
}
