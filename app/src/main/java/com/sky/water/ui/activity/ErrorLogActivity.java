package com.sky.water.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sky.utils.ActivityLifecycle;
import com.sky.water.ui.BaseActivity;


/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 16/01/20
 */
public class ErrorLogActivity extends BaseActivity {
    public static void startThis(String errorMsg) {
        Context context = ActivityLifecycle.getInstance().getCurrentAct();
        Intent intent = new Intent(context, ErrorLogActivity.class);
        intent.putExtra("errorMsg", errorMsg);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvContent = new TextView(this);
        tvContent.setText(getIntent().getStringExtra("errorMsg"));
        setContentView(tvContent);
    }

}
