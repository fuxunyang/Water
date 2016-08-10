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
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sky.water.R;
import com.sky.water.api.IDataResult;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.fragment.NewsFragment;
import com.sky.water.ui.widget.SlidingMenu;
import com.sky.water.utils.http.HttpDataUtils;
import com.sky.water.utils.http.HttpUtilsBase;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 16/01/20
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static boolean flag = true;
    @ViewInject(R.id.slidingMenu)
    private SlidingMenu slidingMenu;
    @ViewInject(R.id.menu)
    private ViewGroup menu;
    @ViewInject(R.id.frameLayout)
    private FrameLayout frameLayout;

    @ViewInject(R.id.lable_01)
    private RelativeLayout lable_01;
    @ViewInject(R.id.lable_02)
    private RelativeLayout lable_02;
    @ViewInject(R.id.lable_03)
    private RelativeLayout lable_03;
    @ViewInject(R.id.lable_04)
    private RelativeLayout lable_04;
    @ViewInject(R.id.lable_05)
    private RelativeLayout lable_05;
    @ViewInject(R.id.lable_006)
    private RelativeLayout lable_006;

    @ViewInject(R.id.lable_06)
    private RelativeLayout lable_06;
    @ViewInject(R.id.lable_07)
    private RelativeLayout lable_07;
    @ViewInject(R.id.lable_08)
    private RelativeLayout lable_08;
    @ViewInject(R.id.lable_09)
    private RelativeLayout lable_09;
    @ViewInject(R.id.lable_10)
    private RelativeLayout lable_10;
    private List<RelativeLayout> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setLeftButton(R.mipmap.ic_menu_left);
        initMenu();
        setFragment();

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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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

    private void setFragment() {
        tabs = new ArrayList<>();
        tabs.add(lable_06);
        tabs.add(lable_07);
        tabs.add(lable_08);
        tabs.add(lable_09);
        tabs.add(lable_10);
        lable_01.setBackgroundResource(R.color.alpha_66);

        replaceFragment(new NewsFragment().setType(NewsFragment.ONE), lable_06);
    }

    @Event({R.id.lable_06, R.id.lable_07, R.id.lable_08, R.id.lable_09, R.id.lable_10})
    private void setBottomClick(View view) {
        resetAllTabs();
        switch (view.getId()) {
            case R.id.lable_06:
                replaceFragment(new NewsFragment().setType(NewsFragment.ONE), lable_06);
                break;
            case R.id.lable_07:
                replaceFragment(new NewsFragment().setType(NewsFragment.TWO), lable_07);
                break;
            case R.id.lable_08:
                replaceFragment(new NewsFragment().setType(NewsFragment.THREE), lable_08);
                break;
            case R.id.lable_09:
                replaceFragment(new NewsFragment().setType(NewsFragment.FOUR), lable_09);
                break;
            case R.id.lable_10:
                replaceFragment(new NewsFragment().setType(NewsFragment.FIVE), lable_10);
                break;

        }

    }

    private void replaceFragment(Fragment fragment, RelativeLayout lable) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
        lable.setBackgroundResource(R.mipmap.ic_bot_bgpre_06);
    }

    private void resetAllTabs() {
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).setBackgroundResource(R.color.white_alpha);
        }
    }

    //侧滑栏
    private void initMenu() {
        int count = menu.getChildCount();
        for (int i = 0; i < count; i++) {
            menu.getChildAt(i).setOnClickListener(this);
        }
        slidingMenu.setOnMenuState(new SlidingMenu.MenuState() {
            @Override
            public void OnOpen() {
                flag = false;
            }

            @Override
            public void OnClose() {
                flag = true;
            }
        });
    }//侧滑栏

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                jumpActivity(MainActivity.this, LoginActivity.class);
                break;
        }
        return false;
    }

    @Override
    public void leftOnClick() {
        menu.getChildAt(0).setBackgroundResource(R.color.alpha_66);
        slidingMenu.toggleMenu();
    }

    private long lastBack;

    @Override
    public void onBackPressed() {
        if (slidingMenu.isOpen()) {
            slidingMenu.close();
            return;
        }
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit));
            lastBack = nowCurrent;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getTag().equals(getString(R.string.lable_01))) {
            slidingMenu.close();
        } else if (v.getTag().equals(getString(R.string.lable_02))) {
            jumpActivity(MainActivity.this, WeatherActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_03))) {
            jumpActivity(MainActivity.this, SoilActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_04))) {
            jumpActivity(MainActivity.this, WaterActivity.class);
        } else if (v.getTag().equals(getString(R.string.lable_05))) {
            jumpActivity(MainActivity.this, BalanceActivity.class);
        }else if (v.getTag().equals(getString(R.string.lable_006))) {
            jumpActivity(MainActivity.this, BindCardActivity.class);
        }
    }
}
