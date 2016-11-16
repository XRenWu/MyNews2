/**
 * 
 */
package com.l000phone.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description：访问网路工具类<br/>
 * Copyright (c) , 2016, Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:HttpUtils.java <br/>
 * Date:2016-9-20
 * 
 * @author 徐文波
 * @version : 1.0
 */
public class HttpUtils {
	/**
	 * 下载数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static byte[] downLoadResFromServer(String urlStr)
			throws ClientProtocolException, IOException {
//		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//		String path = urlStr.substring(urlStr.lastIndexOf("/")+1);
//		File xmlfile = new File(file,path);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(urlStr);
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			//FileOutputStream out = new FileOutputStream(xmlfile);
			byte[] bytes = EntityUtils.toByteArray(entity);
			//out.write(bytes);
			//out.close();
			Log.i("byte121314",bytes.length+"");
			return bytes;
		}

		return null;
	}


	/**
	 * 从服务器下载数据
	 * 
	 * @return
	 */
	public static String downLoadServer(String urlStr) {

		try {
			return new String(downLoadResFromServer(urlStr));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从服务器下载图片
	 * 
	 * @return
	 */
	public static Bitmap downLoadPicFromServer(String urlStr) {

		try {
			byte[] data = downLoadResFromServer(urlStr);

			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			return bm;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
