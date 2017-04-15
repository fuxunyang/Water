package com.sky.water.ui.activity;

import android.os.Bundle;

import com.sky.water.R;
import com.sky.water.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;

/**
 * Created by 李彬 on 2017/4/12.
 */
@ContentView(R.layout.activity_agriculture)
public class AgricultureActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
    }
}
