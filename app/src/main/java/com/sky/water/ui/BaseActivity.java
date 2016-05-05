package com.sky.water.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sky.water.R;
import com.sky.water.api.IBase;
import com.sky.water.common.Constants;
import com.sky.water.ui.dialog.DialogManager;
import com.sky.water.utils.NetworkJudgment;
import com.sky.water.utils.SPUtils;
import com.sky.water.utils.ToastUtils;
import com.sky.water.utils.UIHandler;

import org.xutils.x;

/**
 * @author LiBin
 * @ClassName: BaseActivity
 * @Description: TODO 基类activity
 * @date 2015年3月26日 下午4:01:00
 */
public class BaseActivity extends AppCompatActivity implements IBase, Toolbar.OnMenuItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //判断有无网络
        if (!hasInternetConnected()) showToast("您的网络未连接");
        setHandler();
    }

    public void showToast(String text) {
        ToastUtils.showShort(this, text);
    }//初始化toast提示

    //定义toolbar
    private Toolbar toolbar;
    private TextView tvTitle;

    /**
     * 配合XML文件，设置toolbar在每个需要标题的XML中引用
     * 引用manifest里的label
     */
    public void setToolbar() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            String title = (String) info.nonLocalizedLabel;
            if (TextUtils.isEmpty(title))
                title = (String) info.loadLabel(getPackageManager());
            if (TextUtils.isEmpty(title))
                title = getResources().getString(info.labelRes);
            setToolbar(title + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配合XML文件，设置toolbar
     * 在每个需要标题的XML中引用
     * <include layout="@layout/activity_title"/>
     */
    public void setToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;
        toolbar.setTitle("");//默认为居左,所以隐藏
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(title);//居中的标题
        setSupportActionBar(toolbar);
        //toolbar.setBackground(R.);
//        toolbar.setLogo(R.drawable.div_line_v);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftOnClick();
            }
        });
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 更换左侧图标
     *
     * @param left 为－1时，隐藏图标
     */
    public void setLeftButton(int left) {
        if (left == -1) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(left);
        }
    }

    /**
     * 左侧按钮的点击事件，默认关闭，如需重写，把继承的super删掉
     */
    public void leftOnClick() {
        finish();
    }

    /**
     * 右侧menu的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    //toolbar部分完
    /**
     * 跳转activity，并定义跳转动画
     *
     * @param packageContext A Context of the application package implementing
     *                       this class.
     * @param cls            The component class that is to be used for the intent.
     */
    public void jumpActivity(Context packageContext, Class<?> cls) {
        startActivity(new Intent(packageContext, cls));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        //overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
    }

    public void jumpActivity(Context packageContext, Class<?> cls, String name, String value) {
        startActivity(new Intent(packageContext, cls).putExtra(name, value));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void jumpActivity(Context packageContext, Class<?> cls, String... values) {
        Intent intent = new Intent(packageContext, cls);
        String name = null;
        String value = null;
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) name = values[i];
            else if (i % 2 == 1) value = values[i];
            intent.putExtra(name, value);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    //handler部分
    protected UIHandler handler = new UIHandler(Looper.getMainLooper());

    //设置handler监听
    private void setHandler() {
        handler.setHandler(new UIHandler.IHandler() {
            public void handleMessage(Message msg) {
                handler(msg);//有消息就提交给子类实现的方法
            }
        });
    }

    //让子类处理消息
    protected void handler(Message msg) {
    }
//handler 完

    @Override
    public String getUserName() {
        return (String) SPUtils.get(this, Constants.USERNAME, "");
    }

    @Override
    public void showLoading() {
        DialogManager.showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogManager.disDialog();
    }

    @Override
    public boolean getUserOnlineState() {
        return (boolean) SPUtils.get(this, Constants.ISONLINE, false);
    }

    @Override
    public void setUserOnlineState(boolean isOnline) {
        SPUtils.put(this, Constants.ISONLINE, true);
    }

    /**
     * 在这里开启所有需要开启的服务
     */
    @Override
    public void startService() {
    }

    /**
     * 在这里开启所有需要开启的服务
     */
    @Override
    public void bindService() {
    }

    /**
     * 在这里关闭所有需要开启的服务
     */
    @Override
    public void stopService() {
    }

    @Override
    public boolean hasInternetConnected() {
        return NetworkJudgment.isConnected(this);
    }


    @Override
    public void isExit() {
    }

}