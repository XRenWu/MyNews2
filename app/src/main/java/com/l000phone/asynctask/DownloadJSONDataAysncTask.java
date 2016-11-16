/**
 * 
 */
package com.l000phone.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.l000phone.entity.NewsItem;
import com.l000phone.util.HttpUtils;
import com.l000phone.util.ParseJSONStrUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description：自定义下载json格式数据的异步任务类<br/>
 * Copyright (c) , 2016, Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:DownloadJSONDataAysncTask.java <br/>
 * Date:2016-9-20
 * 
 * @author 徐文波
 * @version : 1.0
 */
public class DownloadJSONDataAysncTask extends AsyncTask<String, Void, String> {
	private JsonDataSettingCallBack callBack;
	private ProgressDialog dialog;

	/**
	 * @param callBack
	 * @param dialog
	 */
	public DownloadJSONDataAysncTask(JsonDataSettingCallBack callBack,
			ProgressDialog dialog) {
		this.callBack = callBack;
		this.dialog = dialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		dialog.setMessage("数据加载中...");
		dialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
		return HttpUtils.downLoadServer(params[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			LinkedList<NewsItem> ds = ParseJSONStrUtils
					.parseJsonStrToEntiy(result);
			callBack.setJsonData(ds);

			dialog.dismiss();// 隐藏
		}

	}

	// 设置Json格式的数据回调接口
	public interface JsonDataSettingCallBack {
		// 给ImageView控件设置内容
		void setJsonData(LinkedList<NewsItem> ds);
	}

}
