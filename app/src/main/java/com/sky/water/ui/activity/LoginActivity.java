package com.sky.water.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sky.water.BuildConfig;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.ui.BaseActivity;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/21 下午4:32
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_pass)
    private EditText et_pass;
    @ViewInject(R.id.bt_login)
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            et_name.setText("admin");
            et_pass.setText("admin");
        }
    }

    @Event(R.id.bt_login)
    private void loginOnClick(View view) {
        String name = et_name.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
            showToast(getString(R.string.error_01));
            return;
        }
        showLoading();
        HttpDataUtils.login(name, pass, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_02));
                    return;
                }
                if (data.contains("true")) {
                    showToast(getString(R.string.success));
                    setUserOnlineState(true);
                    finish();
                } else if (data.contains("false")) {
                    showToast(getString(R.string.error_01));
                }
            }
        });

    }

    //手指按下的点为(downX, downY)手指离开屏幕的点为(x2, y2)
    float downX = 0;
    float downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当手指按下的时候
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP://当手指离开的时候
                if (event.getX() - downX > 50) {//向右滑
                    finish();
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
