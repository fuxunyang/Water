package com.sky.water.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sky.utils.TextUtil;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.ui.BaseActivity;
import com.sky.water.utils.http.HttpDataUtils;

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
        getBindCard();
    }

    private void getBindCard() {
        HttpDataUtils.tbAppUsersExGetList("1", new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {

            }
        });
    }

    @Event(R.id.bt_bind)
    private void onClick(View view) {
        String card = TextUtil.getText(edCard);
        if (TextUtil.notNull(card, "卡号")) return;
        HttpDataUtils.tbAppUsersExAdd("1", card, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    showToast("绑卡成功");
                } else {
                    showToast("绑卡失败");
                }
            }
        });
    }


}
