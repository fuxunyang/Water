package com.sky.water.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sky.utils.TextUtil;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.Card;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;
import com.sky.water.ui.widget.MyRecycleView;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by sky on 2016/7/29.
 */
@ContentView(R.layout.activity_bindcard)
public class BindCardActivity extends BaseActivity {

    @ViewInject(R.id.ed_card)
    private EditText edCard;

    @ViewInject(R.id.recycle)
    private MyRecycleView recycle;

    private BaseAdapter<Card, BaseHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setAdapter();
        getBindCard();
    }

    private void setAdapter() {
        adapter = new BaseAdapter<Card, BaseHolder>(R.layout.item_card) {
            @Override
            protected BaseHolder onCreateBodyHolder(View view) {
                return new BaseHolder(view);
            }

            @Override
            protected void onAchieveHolder(BaseHolder holder, final int position) {
                Card card = datas.get(position);
                holder.setText(R.id.tv_name, "卡号" + (position + 1));
                holder.setText(R.id.tv_card, card.getMachineNo() + "");
                ImageButton bt = holder.getView(R.id.img_delete);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unBindCard(datas.get(position).getMachineWellsCommunicationNoID() + "");
                    }
                });

            }
        };
        recycle.setAdapter(adapter);

    }

    /**
     * 查询用户关联卡号
     */
    private void getBindCard() {
        HttpDataUtils.tbAppUsersExGetList("1", new IDataResultImpl<List<Card>>() {
            @Override
            public void onSuccessData(List<Card> data) {
                adapter.setDatas(data);
            }
        });
    }

    /**
     * 解除绑定
     * @param id
     */
    private void unBindCard(String id) {
        HttpDataUtils.tbAppUsersExDeleteUpdate(id, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    showToast("解绑成功");
                    getBindCard();
                } else {
                    showToast("解绑失败");
                }
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
                    getBindCard();
                } else {
                    showToast("绑卡失败");
                }
            }
        });
    }


}
