package com.l000phone.mynews;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.l000phone.entity.User;
import com.l000phone.sqLiteOpenHelper.MySQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LoginActivity extends AppCompatActivity {
    private ViewPager vp;
    private String[] tabNames;
    private List<Fragment> fragments;
    private LruCache<String, Object> cache;
    private ExecutorService pool;
    private String[] strUrl;
    private TabLayout mtl;
    private WebView wv;
    private EditText met_name;
    private EditText met_password;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "mydb.db", null, 1);
//        db = helper.getReadableDatabase();
        Log.i("lifeStyle", "----LoginActivity onCreate----");
        setContentView(R.layout.activity_login);
        // 步骤：
        // 获得界面上控件的实例
        met_name = (EditText) findViewById(R.id.et_userName_id);
        met_password = (EditText) findViewById(R.id.et_userPassword_id);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifeStyle", "----LoginActivity onStart----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifeStyle", "----LoginActivity onResume----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifeStyle", "----LoginActivity onPause----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifeStyle", "----LoginActivity onStop----");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.i("lifeStyle", "----LoginActivity onDestroy----");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifeStyle", "----LoginActivity onRestart----");
    }
    public void enter(View view){
        String result = "";
        String name = met_name.getText().toString();
        String password = met_password.getText().toString();
//        Cursor cursor = db.rawQuery(
//                "select _id,name,passward from tb_user ",
//                new String[] {});
//        List<User> users = new LinkedList<>();

//        while (cursor.moveToNext()) {
//            // 1、依次取出当前记录各个字段的值
//            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String passward = cursor.getString(cursor
//                    .getColumnIndex("passward"));
//
//            // 2、据此构建User的实例
//            User user = new User(_id, name, passward);
//
//            // 3、将实例加入集合中
//            users.add(user);
//
//            if (){
//
//        }
        if ("董卫鹏".equals(name)&&"123".equals(password)){
            result = "恭喜登录成功";
        }else {
            result = "抱歉，登陆失败";
        }
        Intent intent = new Intent();
        intent.putExtra("result",result);
        intent.putExtra("name",name);
        setResult(400,intent);
        finish();
    }
}
