package com.l000phone.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;

import com.l000phone.fragment.MyFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsActivity extends AppCompatActivity {
    private ViewPager vp;
    private String[] tabNames;
    private List<Fragment> fragments;
    private LruCache<String, Object> cache;
    private ExecutorService pool;
    private String[] strUrl;
    private TabLayout mtl;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("lifeStyle", "----NewsActivity onCreate----");
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        // 步骤：
        // 获得界面上控件的实例
        wv = (WebView) findViewById(R.id.wv_id);

        String url = intent.getStringExtra("url");

        WebSettings settings = wv.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        wv.setVerticalScrollbarOverlay(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(url);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifeStyle", "----NewsActivity onStart----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifeStyle", "----NewsActivity onResume----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifeStyle", "----NewsActivity onPause----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifeStyle", "----NewsActivity onStop----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv!=null){
            wv.removeAllViews();
            wv.destroy();
            wv = null;
        }
        Log.i("lifeStyle", "----NewsActivity onDestroy----");
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()){
            wv.goBack();
        }else{
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifeStyle", "----NewsActivity onRestart----");
    }



}
