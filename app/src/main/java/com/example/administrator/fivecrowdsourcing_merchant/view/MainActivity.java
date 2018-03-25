package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.MyFragmentAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.CompletedOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.EnterFragment;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingGoodFragment;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.SendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView phone;
    private TextView title;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;

    private final String[] modules = {"待接单", "待取货","配送中", "已完成"};
    private List<Fragment> mFragments;
    //private static final int SHOW_MERCHANT=1;//显示商家联系人信息
    Merchant merchant=new Merchant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得商家信息
        merchant= (Merchant) getIntent().getSerializableExtra("merchant");
        mFragments = new ArrayList<>();
        //商家入驻状态
        judgeStatus();
//        mFragments.add(new PendingOrderFragment(merchant));
//        mFragments.add(new PendingGoodFragment(merchant));
//        mFragments.add(new SendingOrderFragment(merchant));
//        mFragments.add(new CompletedOrderFragment(merchant));
//        mFragments.add(new PendingOrderFragment());
        initView();
    }

    private void judgeStatus() {
        switch (merchant.getStatus()) {
            case "0":
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                break;
            case "1":
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                mFragments.add(new EnterFragment(merchant));
                break;
            case "2":
                mFragments.add(new PendingOrderFragment(merchant));
                mFragments.add(new PendingGoodFragment(merchant));
                mFragments.add(new SendingOrderFragment(merchant));
                mFragments.add(new CompletedOrderFragment(merchant));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mViewpager.setCurrentItem(0);
//        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(BroConstant.PENDING_DELIVERY));
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        title.setText("Five 商家版本");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //滑动菜单功能
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments, modules);

        //Tablayout
        mTabLayout = findViewById(R.id.tab_layout);
       mViewpager=findViewById(R.id.viewpager);
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
       changeTabView(0,0);
        mViewpager.setCurrentItem(0);


        //初始化商家信息
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        phone  = (TextView)headerLayout.findViewById(R.id.phone);
        phone.setText(merchant.getPhone());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.info_merchant) {
            // 商家信息认证
            Intent intent=new Intent(MainActivity.this, MerchantInfoActivity.class);
            intent.putExtra("merchant",merchant);
            startActivity(intent);
        }
        else if (id == R.id.rule) {
            Intent intent=new Intent(MainActivity.this, WebviewActivity.class);
           // intent.putExtra("merchant",merchant);
            startActivity(intent);
        }

//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeTabView(int position, int count) {
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (tab != null) {
            switch (tab.getPosition()) {
                case 0:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
                case 1:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
                case 2:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
            }
        }
    }
}
