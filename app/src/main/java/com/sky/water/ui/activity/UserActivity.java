package com.sky.water.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sky.utils.SPUtils;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.common.Constants;
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
 * Created by 机械革命 on 2016
 */
@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity {

    @ViewInject(R.id.et_real)
    private TextView etReal;
    @ViewInject(R.id.et_account)
    private TextView et_nick;
    @ViewInject(R.id.et_xiangzhen)
    private TextView et_xiangzhen;
    @ViewInject(R.id.et_cunzhuang)
    private TextView et_cunzhuang;
    @ViewInject(R.id.et_phone)
    private TextView et_phone;

    @ViewInject(R.id.recycle)
    private MyRecycleView recycle;

    private BaseAdapter<Card, BaseHolder> adapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setText();
        setAdapter();

        userId = (String) SPUtils.getInstance().get(Constants.ID, "");
        if (TextUtils.isEmpty(userId)) {
            showToast("请先登录");
            return;
        }
        getBindCard();
    }

    private void setText() {
        etReal.setText((String) SPUtils.getInstance().get(Constants.TrueName, ""));
        et_nick.setText((String) SPUtils.getInstance().get(Constants.UserName, ""));
        et_xiangzhen.setText((String) SPUtils.getInstance().get(Constants.ParentName, ""));
        et_cunzhuang.setText((String) SPUtils.getInstance().get(Constants.Name, ""));
        et_phone.setText((String) SPUtils.getInstance().get(Constants.PHNo, ""));
    }

    @Event(R.id.bt_Cancellation)
    private void cancelOnClick(View view) {
        SPUtils.getInstance().clear();
        finish();
    }

    private void setAdapter() {
        adapter = new BaseAdapter<Card, BaseHolder>(R.layout.item_card2) {
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
                        creatDialog(datas.get(position).getID() + "");
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
        HttpDataUtils.tbAppUsersExGetListByAppUsersID(userId, new IDataResultImpl<List<Card>>() {
            @Override
            public void onSuccessData(List<Card> data) {
                adapter.setDatas(data);
            }
        });
    }

    /**
     * 解除绑定
     *
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

    private void creatDialog(final String id) {
        new AlertDialog.Builder(this).setTitle("确定删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                unBindCard(id);
            }
        }).setNegativeButton("取消", null).show();
    }
}
