package com.sky.water.ui;

import android.app.Application;

import com.sky.water.BuildConfig;
import com.sky.water.utils.ActivityLifecycle;
import com.sky.water.utils.ToastUtils;

import org.xutils.x;


/**
 * @author sky QQ:1136096189
 * @Description: 基础MyApplication
 * @date 15/11/28 下午12:38
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        // 初始化自定义Activity管理器
        registerActivityLifecycleCallbacks(ActivityLifecycle.getInstance());
    }

    public void exit() {
        ActivityLifecycle.getInstance().popAllActivity();
    }

    public void showErroe(int code) {
        ToastUtils.showError(getApplicationContext(),code);
    }
}
