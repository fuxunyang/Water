package com.sky.water.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sky.utils.SPUtils;
import com.sky.utils.TextUtil;
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
 * Created by sky on 2016/7/29.
 */
@ContentView(R.layout.activity_bindcard)
public class BindCardActivity extends BaseActivity {

    @ViewInject(R.id.ed_card)
    private EditText edCard;

    @ViewInject(R.id.recycle)
    private MyRecycleView recycle;

    private BaseAdapter<Card, BaseHolder> adapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setAdapter();
        userId = (String) SPUtils.get(BindCardActivity.this, Constants.ID, "");
        if (TextUtils.isEmpty(userId)) {
            showToast("请先登录");
            return;
        }
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
                        creatDialog(datas.get(position).getID() + "");
                    }
                });

            }
        };
        recycle.setAdapter(adapter);

    }

    private void creatDialog(final String id) {
        new AlertDialog.Builder(this).setTitle("确定删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                unBindCard(id);
            }
        }).setNegativeButton("取消", null).show();


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

    @Event(R.id.bt_bind)
    private void onClick(View view) {
        final String card = TextUtil.getText(edCard);
        if (TextUtil.notNull(card, "卡号")) return;
        final String treaName = (String) SPUtils.get(BindCardActivity.this, Constants.TrueName, "");
        final String areaId = (String) SPUtils.get(BindCardActivity.this, Constants.AreaID, "");

        if (treaName == null || treaName.length() == 0 || areaId == null || areaId.length() == 0) {
            showToast("请先登录");
            return;
        }
        //卡号是否存在
        HttpDataUtils.tbMachineWellsCommunicationNoExists(treaName, areaId, card, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    isBind(card, treaName, areaId);
                } else {
                    showToast("卡号不存在");
                }
            }
        });
    }

    //是否已绑定
    private void isBind(final String card, String treaName, String areaId) {
        HttpDataUtils.tbAppUsersExExistsName(treaName, areaId, card, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    showToast("卡号已绑定");
                } else {
                    bindCard(card);
                }
            }
        });
    }

    //绑定卡号
    private void bindCard(String card) {
        HttpDataUtils.tbAppUsersExAdd(userId, card, new IDataResultImpl<String>() {
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
