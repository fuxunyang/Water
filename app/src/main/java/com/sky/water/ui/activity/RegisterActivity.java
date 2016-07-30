package com.sky.water.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.water.R;
import com.sky.water.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by sky on 2016/7/29.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity{
    @ViewInject(R.id.et_real)
    private EditText etReal;
    @ViewInject(R.id.et_card)
    private EditText et_card;
     @ViewInject(R.id.et_nick)
    private EditText et_nick;
     @ViewInject(R.id.et_pass1)
    private EditText et_pass1;
     @ViewInject(R.id.et_pass2)
    private EditText et_pass2;
     @ViewInject(R.id.et_xiangzhen)
    private TextView et_xiangzhen ;
    @ViewInject(R.id.et_cunzhuang)
    private TextView et_cunzhuang;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @Event(R.id.bt_register)
    private void onClick(View view){

    }
}
