/**
 * 
 */
package com.l000phone.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.l000phone.entity.NewsItem;
import com.l000phone.fragment.MyFragment;
import com.l000phone.util.HttpUtils;
import com.l000phone.util.ParseJSONStrUtils;
import com.l000phone.util.ParseXmlStrUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
public class DownloadXMLDataAysncTask extends AsyncTask<String, Void,LinkedList<NewsItem>> {
	private XMLDataSettingCallBack callBack;
	private ProgressDialog dialog;

	/**
	 * @param callBack
	 * @param dialog
	 */
	public DownloadXMLDataAysncTask(XMLDataSettingCallBack callBack,
									ProgressDialog dialog) {
		super();
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
	protected LinkedList<NewsItem> doInBackground(String... params) {
		try {
			byte[] strContent = HttpUtils.downLoadResFromServer(params[0]);
			File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			String path = params[1];
			File xmlfile = new File(file,path);
			FileOutputStream out = new FileOutputStream(xmlfile);
			out.write(strContent);
			out.close();
			if (xmlfile.exists()) {
				LinkedList<NewsItem> ds = ParseXmlStrUtils.parseXmlStrToItem(xmlfile);
//				MyFragment fragment = (MyFragment)callBack;
//				if (ds!=null){
//					Toast.makeText(fragment.getContext(),"数据源不为空",Toast.LENGTH_LONG).show();
//					Toast.makeText(fragment.getContext(),"数据源的长度为"+ds.size(),Toast.LENGTH_LONG).show();
//				}else {
//					Toast.makeText(fragment.getContext(),"数据源为空",Toast.LENGTH_LONG).show();
//				}
				return ds;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */

	@Override
	protected void onPostExecute(LinkedList<NewsItem> result) {

		callBack.setXMLData(result);
//		MyFragment fragment = (MyFragment)callBack;
//		if (result!=null){
//			Toast.makeText(fragment.getActivity(),"数据源不为空",Toast.LENGTH_LONG).show();
//			Toast.makeText(fragment.getActivity(),"数据源的长度为"+result.size(),Toast.LENGTH_LONG).show();
//		}else {
//			Toast.makeText(fragment.getActivity(),"数据源为空",Toast.LENGTH_LONG).show();
//		}
		dialog.dismiss();// 隐藏
	}


	//	@Override
//	protected void onPostExecute(LinkedList<NewsItem> result){
//		callBack.setXMLData(result);
//		dialog.dismiss();// 隐藏
//	}

	// 设置Json格式的数据回调接口
	public interface XMLDataSettingCallBack {
		// 给ImageView控件设置内容
		void setXMLData(LinkedList<NewsItem> ds);
	}

}
