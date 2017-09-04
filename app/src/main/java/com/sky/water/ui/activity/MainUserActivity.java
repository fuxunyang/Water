package com.sky.water.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.sky.water.R;
import com.sky.water.api.IDataResult;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.dialog.User01Pop;
import com.sky.water.ui.dialog.User02Pop;
import com.sky.water.ui.dialog.User03Pop;
import com.sky.water.utils.http.HttpDataUtils;
import com.sky.water.utils.http.HttpUtilsBase;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 16/01/20
 */
@ContentView(R.layout.activity_mainuser)
public class MainUserActivity extends BaseActivity {

    @ViewInject(R.id.layout)
    private LinearLayout layout;
    @ViewInject(R.id.lable_06)
    private RelativeLayout layout06;
    @ViewInject(R.id.lable_07)
    private RelativeLayout layout07;
    @ViewInject(R.id.lable_08)
    private RelativeLayout layout08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setLeftButton(-1);
        checkUpdate();
    }

    private void checkUpdate() {
        HttpDataUtils.getVersion(new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                PackageManager packageManager = getPackageManager();
                try {
                    JSONObject json = new JSONObject(data);
                    int codeVersion = json.getInt("Version");
                    PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                    int code = packageInfo.versionCode;
                    if (code < codeVersion) {
                        creatDialog("http://www.jxncsl.com.cn:8090/AppDownLoad.aspx?type=Android");
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    //创建dialog
    public void creatDialog(final String URL) {
        new AlertDialog.Builder(this).setTitle("下载APP")
//                .setMessage(list.get(0))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                            getApp(URL);
                        else showToast(getResources().getString(R.string.down_checksd));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                })
                .setCancelable(false).show();
    }

    //获取app
    private void getApp(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(MainUserActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMax(100);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        final HttpUtilsBase.RequestHandler handler = HttpDataUtils.getApp(url, new IDataResult<String>() {
            @Override
            public void onFailure(HttpException arg0, int arg1) {
                progressDialog.dismiss();
                showToast(getResources().getString(R.string.down_check));
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                int i = (int) (current * 1.0f / total * 100);
                progressDialog.setProgress(i);
            }

            @Override
            public void onSuccessData(String data) {
                progressDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(data)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            }

            @Override
            public void onStart() {
                progressDialog.show();
            }

            @Override
            public void onCancel() {
                progressDialog.dismiss();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        });
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                handler.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (getUserOnlineState())
                    jumpActivity(MainUserActivity.this, UserActivity.class);
                else
                    jumpActivity(MainUserActivity.this, LoginActivity.class);
                break;
        }
        return false;
    }


    private long lastBack;

    @Override
    public void onBackPressed() {
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit));
            lastBack = nowCurrent;
        } else {
            super.onBackPressed();
        }
    }

    @Event({R.id.lable_06, R.id.lable_07, R.id.lable_08})
    private void tabOnClick(View v) {
        resetAllTabs();
        switch (v.getId()) {
            case R.id.lable_06:
                showSharePopupWindow01(layout06, 0);
                break;
            case R.id.lable_07:
                showSharePopupWindow02(layout07, layout06.getWidth());
                break;
            case R.id.lable_08:
                showSharePopupWindow03(layout08, layout06.getWidth() * 2);
                break;
        }
    }

    private void resetAllTabs() {
        layout06.setBackgroundResource(R.color.white_alpha);
        layout07.setBackgroundResource(R.color.white_alpha);
        layout08.setBackgroundResource(R.color.white_alpha);
    }

    private void showSharePopupWindow01(View view, int left) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_user01, null);
        User01Pop pop = new User01Pop(popView, view.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = popView.getMeasuredHeight();

        backgroundAlpha(0.5f);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, left, location[1] - popupHeight);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        view.setBackgroundResource(R.mipmap.ic_bot_bgpre_06);
    }

    private void showSharePopupWindow02(View view, int left) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_user02, null);
        User02Pop pop = new User02Pop(popView, view.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popView.getMeasuredWidth();
        int popupHeight = popView.getMeasuredHeight();
        backgroundAlpha(0.5f);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, left, location[1] - popupHeight);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        view.setBackgroundResource(R.mipmap.ic_bot_bgpre_06);
    }

    private void showSharePopupWindow03(View view, int left) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        View popView = LayoutInflater.from(this).inflate(R.layout.pop_user03, null);
        User03Pop pop = new User03Pop(popView, view.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popView.getMeasuredWidth();
        int popupHeight = popView.getMeasuredHeight();
        backgroundAlpha(0.5f);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, left, location[1] - popupHeight);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        view.setBackgroundResource(R.mipmap.ic_bot_bgpre_06);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
