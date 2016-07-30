package com.sky.water.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sky.water.R;
import com.sky.water.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by sky on 2016/7/29.
 */
@ContentView(R.layout.activity_bindcard)
public class BindCardActivity extends BaseActivity {

    @ViewInject(R.id.ed_card)
    private EditText edCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
    }

    @Event(R.id.bt_bind)
    private void onClick(View view) {

    }
}
