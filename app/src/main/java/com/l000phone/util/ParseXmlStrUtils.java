package com.l000phone.util;

import android.os.Environment;

import com.l000phone.entity.BaiduNews;
import com.l000phone.entity.NewsItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ParseXmlStrUtils {
    public static LinkedList<NewsItem> parseXmlStrToItem(File str) {
        try {
//            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            String urlStr = "http://news.qq.com/newsgn/rss_newsgn.xml";
//            String path = urlStr.substring(urlStr.lastIndexOf("/")+1);
//            File xmlfile = new File(file,path);
            //解析器工厂实例的获取
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();

            //解析器实例的获取
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

            try {
                //开始解析

                xmlPullParser.setInput(new FileInputStream(str), "gb2312");
                int eventType = xmlPullParser.getEventType();

                //否建实例将解析后的结果设置到实例中
                LinkedList<NewsItem> newsItems = null;
                NewsItem newsItem = null;
                String name = null;
                EXIT_FLG:while (true) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT://文档解析开始
                            newsItems = new LinkedList<>();
                            break;
                        case XmlPullParser.END_DOCUMENT://文档解析结束
                            break EXIT_FLG;
                        case XmlPullParser.START_TAG://标签解析开始
                            name = xmlPullParser.getName();
                            if ("item".equals(name)) {
                                newsItem = new NewsItem();
                            }
                            break;
                        case XmlPullParser.END_TAG://解析到标签结束将新闻的实例加入到集合中
                            if ("item".equals(xmlPullParser.getName())) {
                                newsItems.add(newsItem);
                            }
                            name = null;
                            break;
                        case XmlPullParser.TEXT:
                            if (newsItem != null){
                                if ("title".equals(name)){
                                    newsItem.setTitle(xmlPullParser.getText());
                                }else if ("link".equals(name)){
                                    newsItem.setLink(xmlPullParser.getText());
                                }else if ("author".equals(name)){
                                    newsItem.setAuthor(xmlPullParser.getText());
                                }else if ("pubDate".equals(name)){
                                    newsItem.setPubDate(xmlPullParser.getText());
                                }
                            }
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
                //并将解析后的数据返回
                return newsItems;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static LinkedList<BaiduNews> parseXmlStrToBaiduItem(File str) {
        try {
//            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            String urlStr = "http://news.qq.com/newsgn/rss_newsgn.xml";
//            String path = urlStr.substring(urlStr.lastIndexOf("/")+1);
//            File xmlfile = new File(file,path);
            //解析器工厂实例的获取
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();

            //解析器实例的获取
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

            try {
                //开始解析

                xmlPullParser.setInput(new FileInputStream(str), "gb2312");
                int eventType = xmlPullParser.getEventType();

                //否建实例将解析后的结果设置到实例中
                LinkedList<BaiduNews> baiduNews = null;
                BaiduNews baiduNewsItem = null;
                String name = null;
                EXIT_FLG:while (true) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT://文档解析开始
                            baiduNews = new LinkedList<>();
                            break;
                        case XmlPullParser.END_DOCUMENT://文档解析结束
                            break EXIT_FLG;
                        case XmlPullParser.START_TAG://标签解析开始
                            name = xmlPullParser.getName();
                            if ("item".equals(name)) {
                                baiduNewsItem = new BaiduNews();
                            }
                            break;
                        case XmlPullParser.END_TAG://解析到标签结束将新闻的实例加入到集合中
                            if ("item".equals(xmlPullParser.getName())) {
                                baiduNews.add(baiduNewsItem);
                            }
                            name = null;
                            break;
                        case XmlPullParser.TEXT:
                            if (baiduNewsItem != null){
                                if ("title".equals(name)){
                                    baiduNewsItem.setTitle(xmlPullParser.getText());
                                }else if ("link".equals(name)){
                                    baiduNewsItem.setLink(xmlPullParser.getText());
                                }else if ("author".equals(name)){
                                    baiduNewsItem.setAuthor(xmlPullParser.getText());
                                }else if ("pubDate".equals(name)){
                                    baiduNewsItem.setPubDate(xmlPullParser.getText());
                                }else if ("description".equals(name)){
                                    String content = xmlPullParser.getText();
                                    baiduNewsItem.setDescription(content);
                                    if (content.contains("src=")){
                                        baiduNewsItem.setType(1);
                                        String src = StringUtils.CutStringGetImgUrl(content);
                                        baiduNewsItem.setSrc(src);
                                    }
                                }
                            }
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
                //并将解析后的数据返回
                return baiduNews;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }
}
