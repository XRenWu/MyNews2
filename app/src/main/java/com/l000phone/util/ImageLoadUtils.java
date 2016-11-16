package com.l000phone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * Description： 图片加载工具类<br/>
 * Copyright (c)   2016,  Jansonxu <br/>
 * This program is protected by copyright laws <br/>
 * Date: 2016年10月20  上午 9:25
 *
 * @author 徐文波
 * @version : 1.0
 */
public class ImageLoadUtils {

    private static LruCache<String, Bitmap> cache;
    private static String picName;


    /**
     * 动态加载图片（从网络上，或者是设备上（sd卡或者是内部存储空间））
     *
     * @param picUrl
     * @param context
     * @param ivPic
     * @return
     */
    public static void loadImageFromServerOrDevice(final String picUrl, final Context context, final ImageView ivPic, LruCache tempCache, ExecutorService pool) {
        //初始化
        cache = tempCache;

        //图片的url
        picName = picUrl.substring(picUrl.lastIndexOf('/') + 1);

        final Bitmap bitmap = (Bitmap) cache.get(picName);
       /*
        2、在下载按钮点击后，后台对应的方法，核心代码思路：
        ①首先从LruCache中去查找，若存在，直接从中取出Bitmap的实例，展现到ImageView控件上
        */
        if (bitmap != null) {
            ivPic.post(new Runnable() {
                @Override
                public void run() {
                    ivPic.setImageBitmap(bitmap);
                }
            });
        } else {
           /*②若LruCache中不存在图片，去sd卡或者是内部存储中查找*/

            File picFile = getDevicePicFile(context);
            if (picFile.exists() && picFile.getTotalSpace() > 0) {
                final Bitmap bitmapFromDevice = BitmapFactory.decodeFile(picFile.getAbsolutePath());
                //找到后，将该Bitmap实例置于到LruCache中缓存起来，
                if(bitmapFromDevice!=null){
                    cache.put(picName, bitmapFromDevice);

                    // 然后，Bitmap的实例，展现到ImageView控件上
                    ivPic.post(new Runnable() {
                        @Override
                        public void run() {
                            ivPic.setImageBitmap(bitmapFromDevice);
                        }
                    });
                }


            } else {
                downladPicFromRemoteServer(picUrl, context, ivPic, pool);
            }
        }
    }

    /**
     * 若sd卡或者是内部存储中没有图片，开启子线程，去网络上下载
     */
    private static void downladPicFromRemoteServer(final String picUrl, final Context context, final ImageView ivPic, ExecutorService pool) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL uri = new URL(picUrl);

                    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

                    conn.setConnectTimeout(3000);
                    conn.setReadTimeout(3000);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = conn.getInputStream();

                       /* 下载完毕之后，经过二次采样，将该Bitmap实例置于到LruCache中缓存起来*/
                        final Bitmap optionAfterBm = getSecondOptionBitmap(inputStream, ivPic);
                        cache.put(picName, optionAfterBm);

                        /*存储到sd卡或者是内部存储空间中*/
                        saveOptionAfterBitmapToDevice(optionAfterBm, context);

                        /*展现到ImageView控件上。*/
                        ivPic.post(new Runnable() {
                            @Override
                            public void run() {
                                ivPic.setImageBitmap(optionAfterBm);
                            }
                        });

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 将采样之后的图片，存储到sd卡或者是内部存储空间中
     *
     * @param bm
     */
    private static void saveOptionAfterBitmapToDevice(Bitmap bm, Context context) {
        OutputStream os = null;

        try {
            File picFile = getDevicePicFile(context);
            os = new FileOutputStream(picFile);

            // compress(Bitmap.CompressFormat format, int quality, OutputStream stream)

            Bitmap.CompressFormat format = getPicCompressFormat();

            bm.compress(format, 100, os);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得图片的压缩格式
     *
     * @return
     */
    private static Bitmap.CompressFormat getPicCompressFormat() {
        String picSuffix = picName.substring(picName.lastIndexOf('.') + 1);
        if (picSuffix != null) {
            if (picSuffix.equalsIgnoreCase("png")) {
                return Bitmap.CompressFormat.PNG;
            } else if (picSuffix.equalsIgnoreCase("jpg") || picSuffix.equalsIgnoreCase("jpeg")) {
                return Bitmap.CompressFormat.JPEG;
            } else {
                return Bitmap.CompressFormat.WEBP;
            }
        }

        return null;
    }

    /**
     * 返回二次采样之后，与控件大小匹配的图片
     *
     * @return
     */
    private static Bitmap getSecondOptionBitmap(InputStream is, ImageView ivPic) {
        //decodeStream(InputStream is, Rect outPadding, Options opts)

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] b = new byte[4 * 1024];
        try {
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = baos.toByteArray();
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);


        int realWidth = opts.outWidth;
        int realHeight = opts.outHeight;
        int width = ivPic.getWidth();
        int height = ivPic.getHeight();

        int widthScale = realWidth / width;
        int heightScale = realHeight / height;
        int maxScale = Math.max(Math.max(widthScale, heightScale), 1);

        Log.i("Opttions", "maxScale = " + maxScale);


        //正式开始获得缩小版的Bitmap实例
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = maxScale;
        Bitmap optionAfterBm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        return optionAfterBm;
    }


    /**
     * 判断设备上是否存在图片(去sd卡或者是内部存储中查找指定的图片是否存在)
     *
     * @return
     */
    private static File getDevicePicFile(Context context) {
        File picFile = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //若sd卡挂载了，从sd中查找（公共目录下pictures查找）
            File picPubDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            picFile = new File(picPubDir, picName);
        } else {
            //否则，去手机内部存储空间(缓存)中查找
            File cacheDir = context.getCacheDir();
            picFile = new File(cacheDir, picName);
        }

        return picFile;
    }
}
