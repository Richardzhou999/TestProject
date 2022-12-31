package com.uwei.uwproject;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    /**
     * 设备策略服务
     */
    private DevicePolicyManager dpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //绑定操作
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
    }

    /**
     * 锁屏
     *
     * @param view
     */
    public void lockcreen(View view) {
        ComponentName who = new ComponentName(this, MyDeviceAdminReceiver.class);
        // 推断是否已经开启管理员权限
        if (dpm.isAdminActive(who)) {
            // 锁屏
            dpm.lockNow();
            // 设置屏幕password 第一个是password 第二个是附加參数
            dpm.resetPassword("123", 0);

            // 清楚数据
            // WIPE_EXTERNAL_STORAGE 清楚sdcard的数据
            // 0 恢复出厂设置
            //dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
        } else {
            // 假设为未开启 提示
            Toast.makeText(TestActivity.this, "请先开启管理员权限!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 代码开启管理权限
     *
     * @param view
     */
    public void openAdmin(View view) {
        // 创建一个Intent 加入设备管理员
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        // 激活MyAdmin广播接收着
        ComponentName who = new ComponentName(this, MyDeviceAdminReceiver.class);

        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
        // 说明用户开启管理员权限的优点
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "开启能够一键锁屏，防止勿碰");
        startActivity(intent);

        Toast.makeText(TestActivity.this, "管理员权限已开启!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 卸载当前的软件 设备管理数据特殊应用 所以不能普通卸载
     */

    public void uninstall(View view) {
        // 1. 先清除管理员权限
        ComponentName who = new ComponentName(this, MyDeviceAdminReceiver.class);
        dpm.removeActiveAdmin(who);

        // 2. 普通应用的卸载
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);

    }


}