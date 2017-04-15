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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sky.water.R;
import com.sky.water.api.IDataResult;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.ui.BaseActivity;
import com.sky.water.utils.http.HttpDataUtils;
import com.sky.water.utils.http.HttpUtilsBase;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 16/01/20
 */
@ContentView(R.layout.activity_mainuser)
public class MainUserActivity extends BaseActivity {

    public static boolean flag = true;

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

    @Event({R.id.lable_02, R.id.lable_03, R.id.lable_04, R.id.lable_05, R.id.lable_006, R.id.lable_07})
    private void tabOnClick(View v) {

        if (v.getTag().equals(getString(R.string.lable_02))) {
            jumpActivity(MainUserActivity.this, WeatherActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_03))) {
            jumpActivity(MainUserActivity.this, SoilActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_04))) {
            jumpActivity(MainUserActivity.this, WaterActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_05))) {
            jumpActivity(MainUserActivity.this, BalanceActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_006))) {
            jumpActivity(MainUserActivity.this, BindCardActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_007))) {
            jumpActivity(MainUserActivity.this, AgricultureActivity.class);
        }
    }
}
