package com.xiaoyaoprefecture.fourthday;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fragment.ContentFragment;

import static com.xiaoyaoprefecture.fourthday.R.mipmap.ic_drawer;

/**
 * 今天玩一玩DrawerLayout
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout mDrawerLayout;
    FrameLayout mframeLayout;
    ListView mListView;
    List<String>data=new ArrayList<>();//适配器的数据源
    ArrayAdapter<String>adater;
    String Title;//碎片页面的标题
    ActionBarDrawerToggle drawertoggle;//drawerLayout的开关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    /**
     * 初始化数据
     */
    private void init() {
        Title= (String) getTitle();
        findview();
        initData();
        setAdapter();
        setListener();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        //listview的item点击事件
        mListView.setOnItemClickListener(this);

    }

    /**
     * 绑定适配器
     */
    private void setAdapter() {
        adater=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,data);
        mListView.setAdapter(adater);
    }

    /**
     * 初始化数据源，这里用城市名来代替
     */
    private void initData() {
        String []citys={"上海","苏州","杭州","重庆","成都","北京"};
        data.addAll(Arrays.asList(citys));
        //创建菜单控制开关
        drawertoggle=new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,
                R.mipmap.ic_drawer,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("请选择城市");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(Title);
                invalidateOptionsMenu();
            }
        };
       // Log.e("---------",getSupportActionBar()+"");
        //drawerlayout的开关事件
        mDrawerLayout.setDrawerListener(drawertoggle);
        //开启actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使控制开关的点击事件得到响应
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * 找控件
     */
    private void findview() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        mframeLayout= (FrameLayout) findViewById(R.id.mFrameLayout);
        mListView= (ListView) findViewById(R.id.mListView);
    }

    /**
     * listview的item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContentFragment fragment=new ContentFragment();
        Bundle arg=new Bundle();
        arg.putString("text",data.get(position));
        fragment.setArguments(arg);
        getSupportFragmentManager().beginTransaction().replace(R.id.mFrameLayout,fragment).commit();
        //关闭菜单
        mDrawerLayout.closeDrawer(mListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawOpen=mDrawerLayout.isDrawerOpen(mListView);
        menu.findItem(R.id.action_websearch).setVisible(!isDrawOpen);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //屏蔽掉drawertoggle的点击事件
        if (drawertoggle.onOptionsItemSelected(item)){
            return  true;
        }
        if (item.getItemId()==R.id.action_websearch){
            Intent intent=new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri=Uri.parse("http://www.baidu.com/");
            intent.setData(uri);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        //将drawertogle与drawerLayout状态同步
        drawertoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawertoggle.onConfigurationChanged(newConfig);
    }
}
