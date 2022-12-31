package com.uwei.uwproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.uwei.base.UWBaseFragment;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.uwproject.R;
import com.uwei.uwproject.view.home.HomeFragment;
import com.uwei.uwproject.view.mine.MineFragment;
import com.uwei.uwproject.view.payment.model.IModel;

/**
 * @Author Charlie
 * @Date 2022/7/19 11:21
 */
public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener,
        IModel {

    private long mExitTime;
    private BottomNavigationView navView;
    private UWBaseFragment homeFragment, mineFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initFragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.nav_host_fragment, homeFragment);
        }
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.nav_host_fragment, mineFragment);
        }
        transaction.hide(homeFragment);
        transaction.hide(mineFragment);
        transaction.show(homeFragment);
        transaction.commit();
    }

    protected void initView() {

        navView = findViewById(R.id.nav_view);


        navView.setItemIconTintList(null);
        navView.setOnItemSelectedListener(this);

        initFragment();





    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                SharedPrefUtils.INSTANCE.remove("token");
                SharedPrefUtils.INSTANCE.remove("tokenTime");
                SharedPrefUtils.INSTANCE.remove("phone");
                SharedPrefUtils.INSTANCE.remove("address");
                SharedPrefUtils.INSTANCE.remove("userId");
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","完全推出");
        SharedPrefUtils.INSTANCE.remove("token");
        SharedPrefUtils.INSTANCE.remove("tokenTime");
        SharedPrefUtils.INSTANCE.remove("phone");
        SharedPrefUtils.INSTANCE.remove("address");
        SharedPrefUtils.INSTANCE.remove("userId");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        resetDefaultIcon();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(transaction);
        switch (item.getItemId()){
            case R.id.navigation_home:{
                item.setIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.icon_home_check));
                homeFragment = new HomeFragment();
                transaction.add(R.id.nav_host_fragment,homeFragment);
                transaction.show(homeFragment);
                break;
            }
            case R.id.navigation_mine:{
                item.setIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.icon_mine_check));
                mineFragment = new MineFragment();
                transaction.add(R.id.nav_host_fragment,mineFragment);
                transaction.show(mineFragment);
                break;
            }
            default:break;
        }

        transaction.commit();
        return true;
    }

    private void resetDefaultIcon() {
        MenuItem home =  navView.getMenu().findItem(R.id.navigation_home);
        MenuItem mine =  navView.getMenu().findItem(R.id.navigation_mine);
        home.setIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.icon_home));
        mine.setIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.icon_mine));
    }


    public void hideAllFragment(FragmentTransaction transaction){
        if(homeFragment!=null){
            transaction.remove(homeFragment);
        }
        if(mineFragment!=null){
            transaction.remove(mineFragment);
        }
    }


    @Override
    public void paySuccess() {

    }

    @Override
    public void payError(String error) {

    }
}