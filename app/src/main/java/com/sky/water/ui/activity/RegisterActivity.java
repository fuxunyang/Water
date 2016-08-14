package com.sky.water.ui.activity;

import android.text.TextUtils;
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
import com.sky.water.model.User;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.dialog.AreaPop;
import com.sky.water.ui.dialog.BasePop;
import com.sky.water.utils.RegexUtils;
import com.sky.water.utils.SPUtils;
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
    @ViewInject(R.id.et_account)
    private EditText et_account;
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

    @Event(value = {R.id.tv_card, R.id.et_account, R.id.et_phone}, type = View.OnFocusChangeListener.class)
    private void OnFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.tv_card:
                if (!hasFocus) {
                    final String card = TextUtil.getText(et_card);
                    if (TextUtil.notNull(card, "身份证号")) return;
                    if (card.length() != 18) {
                        showToast("身份证号位数不正确");
                    }
                    checkReal("UserID=" + "'" + card + "'", "此身份证号已存在");
                }
                break;
            case R.id.et_account:
                if (!hasFocus) {
                    final String zhanghao = TextUtil.getText(et_account);
                    if (TextUtil.notNull(zhanghao, "用户账号")) return;
                    checkReal("UserName=" + "'" + zhanghao + "'", "此账号已存在");
                }
                break;
            case R.id.et_phone:
                if (!hasFocus) {
                    final String phone = TextUtil.getText(et_phone);
                    if (TextUtil.notNull(phone, "手机号")) return;
                    if(!RegexUtils.isChinesePhoneNumber(phone)){
                        showToast("手机格式不正确");return;
                    }
                    checkReal("PHNo=" + "'" + phone + "'", "此手机号已存在");
                }
                break;
        }
    }

    @Event(R.id.et_xiangzhen)
    private void getAreaIDonClick(View view) {
        getArea();
    }

    @Event(R.id.et_cunzhuang)
    private void getCunZhuangonClick(View view) {
        getCunZhuang();
    }

    @Event(R.id.bt_register)
    private void registonClick(View view) {
        final String name = TextUtil.getText(etReal);
        if (TextUtil.notNull(name, "姓名")) return;
//        if (RegexUtils.isChinese(name)){
//            showToast("姓名必须为汉字");return;
//        }
        final String card = TextUtil.getText(et_card);
        if (TextUtil.notNull(card, "身份证号")) return;
        if (card.length() != 18) {
            showToast("身份证号位数不正确");
        }
        final String zhanghao = TextUtil.getText(et_account);
        if (TextUtil.notNull(zhanghao, "用户账号")) return;
        if (!RegexUtils.isIdentifier(zhanghao)){
            showToast("账号为字母数字下滑线的构成");
            return;
        }
        final String phone = TextUtil.getText(et_phone);
        if (TextUtil.notNull(phone, "手机号")) return;
        if(!RegexUtils.isChinesePhoneNumber(phone)){
            showToast("手机格式不正确");return;
        }

        final String pass1 = TextUtil.getText(et_pass1);
        if (TextUtil.notNull(pass1, "密码")) return;
        String pass2 = TextUtil.getText(et_pass2);
        if (TextUtil.notNull(pass2, "确认密码")) return;
        if (pass1.length()!=6||pass2.length()!=6){
            showToast("密码长度为6位");
            return;
        }
        if (!pass1.equals(pass2)) {
            showToast("两次输入的密码不一致");
            return;
        }
        if (TextUtils.isEmpty(areaID)) {
            showToast("请选择城镇");
            return;
        }
        if (TextUtils.isEmpty(cunID)) {
            showToast("请选择村庄");
            return;
        }
        register(name, card, zhanghao, phone, pass1);
    }

    private void checkReal(String name, final String toast) {
        //是否存在 true存在false不存在
        HttpDataUtils.tbAppUsersExGetList(name, new IDataResultImpl<Boolean>() {
            @Override
            public void onSuccessData(Boolean data) {
                if (data) {
                    showToast(toast);
                } else {

                }

            }
        });
    }

    private void register(String name, String card, final String zhanghao, String phone, final String pass1) {
        HttpDataUtils.tbAppUsersAdd(name, card, zhanghao, pass1, cunID, phone, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("true")) {
                    showToast("注册成功");
                    login(zhanghao, pass1);
                } else {
                    showToast("注册失败");
                }
            }
        });
    }

    private void login(String zhanghao, String pass1) {
        HttpDataUtils.login(zhanghao, pass1, new IDataResultImpl<User>() {
            @Override
            public void onSuccessData(User data) {
                showToast(getString(R.string.success));
                setUserOnlineState(true);
                SPUtils.put(RegisterActivity.this, SPUtils.getValue(data));
                ActivityLifecycle.getInstance().backToAppointActivity(MainActivity.class);
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

    private String cunID;
    private BasePop cunPop;

    private void getCunZhuang() {
        if (TextUtils.isEmpty(areaID)) {
            showToast("请选择城镇");
            return;
        }
        showLoading();
        HttpDataUtils.getCunZhuang(areaID, new IDataResultImpl<List<AreaEntity>>() {
            @Override
            public void onSuccessData(List<AreaEntity> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                et_cunzhuang.setText(data.get(0).getName());
                cunID = data.get(0).getID() + "";
                createCunzhuangShowFloder(data);
                if (!cunPop.isShowing())
                    cunPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void createCunzhuangShowFloder(final List<AreaEntity> datas) {
        int[] wh = ScreenUtils.getWidthAndHeight(this);
        if (cunPop == null)
            cunPop = new AreaPop(LayoutInflater.from(this).inflate(R.layout.adapter_area, null),
                    wh[0] * 4 / 5, wh[1] * 2 / 3);
        cunPop.setDatas(datas);
        cunPop.setOnItemClickListener(new BasePop.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaEntity data = datas.get(position);
                et_cunzhuang.setText(data.getName());
                cunID = data.getID() + "";
                cunPop.dismiss();
            }
        });
    }
}
