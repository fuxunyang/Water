package com.sky.water.ui.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.utils.ActivityLifecycle;
import com.sky.utils.TextUtil;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.AreaEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.dialog.AreaPop;
import com.sky.water.ui.dialog.BasePop;
import com.sky.water.utils.ScreenUtils;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by sky on 2016/7/29.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewInject(R.id.et_real)
    private EditText etReal;
    @ViewInject(R.id.tv_card)
    private EditText et_card;
    @ViewInject(R.id.et_nick)
    private EditText et_nick;
    @ViewInject(R.id.et_pass1)
    private EditText et_pass1;
    @ViewInject(R.id.et_pass2)
    private EditText et_pass2;
    @ViewInject(R.id.et_xiangzhen)
    private TextView et_xiangzhen;
    @ViewInject(R.id.et_cunzhuang)
    private TextView et_cunzhuang;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    private String areaID;


    private BasePop areaPop;

    @Event(R.id.et_xiangzhen)
    private void getAreaIDonClick(View view) {
        getArea();
    }

    @Event(R.id.bt_register)
    private void registonClick(View view) {
        String name = TextUtil.getText(etReal);
        if (TextUtil.notNull(name, "姓名")) return;
        String card = TextUtil.getText(et_card);
        if (TextUtil.notNull(card, "身份证号")) return;
        String nick = TextUtil.getText(et_nick);
        if (TextUtil.notNull(nick, "昵称")) return;
        String phone = TextUtil.getText(et_phone);
        if (TextUtil.notNull(phone, "手机号")) return;

        String pass1 = TextUtil.getText(et_pass1);
        if (TextUtil.notNull(pass1, "密码")) return;
        String pass2 = TextUtil.getText(et_pass2);
        if (TextUtil.notNull(pass2, "确认密码")) return;
        if (!pass1.equals(pass2)) {
            showToast("两次输入的密码不一致");
            return;
        }
        if (areaID.equals("") || areaID.length() == 0) {
            showToast("请选择城镇");
            return;
        }

        HttpDataUtils.tbAppUsersAdd(name, card, nick, pass1, areaID, phone, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    showToast("注册成功");
                    ActivityLifecycle.getInstance().backToAppointActivity(MainActivity.class);
                } else {
                    showToast("注册失败");
                }
            }
        });
    }

    private void getArea() {
        showLoading();
        HttpDataUtils.getArea(new IDataResultImpl<List<AreaEntity>>() {
            @Override
            public void onSuccessData(List<AreaEntity> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                et_xiangzhen.setText(data.get(0).getName());
                areaID = data.get(0).getID() + "";
                createAreaShowFloder(data);
                if (!areaPop.isShowing())
                    areaPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void createAreaShowFloder(final List<AreaEntity> datas) {
        int[] wh = ScreenUtils.getWidthAndHeight(this);
        if (areaPop == null)
            areaPop = new AreaPop(LayoutInflater.from(this).inflate(R.layout.adapter_area, null),
                    wh[0] * 4 / 5, wh[1] * 2 / 3);
        areaPop.setDatas(datas);
        areaPop.setOnItemClickListener(new BasePop.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaEntity data = datas.get(position);
                et_xiangzhen.setText(data.getName());
                areaID = data.getID() + "";
                areaPop.dismiss();
            }
        });
    }
}
