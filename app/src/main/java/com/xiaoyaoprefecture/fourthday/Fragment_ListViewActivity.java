package com.xiaoyaoprefecture.fourthday;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.MyAdapter;
import fragment.FruitFragment;

/**
 * 把listview和fragment联合使用
 */
public class Fragment_ListViewActivity extends AppCompatActivity {
    ListView mListView;
    FrameLayout mFrameLayout;
    List<String>list=new ArrayList<>();
    FruitFragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment__list_view);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        findView();
        initData();
        setAdapter();
        addFragment();
        setListener();
    }

    /**
     * 给listview的item增加点击事件
     */
    private void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FruitFragment fragment=new FruitFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                fragment.setArguments(bundle);
                transaction.replace(R.id.mFrameLayout,fragment);
                transaction.commit();
            }
        });
    }

    /**
     * 动态加载碎片
     */
    private void addFragment() {
        FruitFragment fragment=new FruitFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mFrameLayout,fragment);
        transaction.commit();

    }

    /**
     * 初始化适配器的数据源
     */
    private void initData() {
        String []fruit={"apple","banana","cherry","durian","grape","kiwi","lemon","orange",
                "peach","pineapple","strawberry","tomato","watermelon"};
        list.addAll(Arrays.asList(fruit));

    }

    /**
     *给listview设置适配器
     */
    private void setAdapter() {
        mListView.setAdapter(new MyAdapter(list,Fragment_ListViewActivity.this));
    }

    /**
     * 找控件
     */
    private void findView() {
        mListView= (ListView) findViewById(R.id.mListView);
        mFrameLayout= (FrameLayout) findViewById(R.id.mFrameLayout);

    }
}
