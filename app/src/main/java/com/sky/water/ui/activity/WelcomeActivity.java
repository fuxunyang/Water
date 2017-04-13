package com.sky.water.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sky.utils.SPUtils;
import com.sky.water.R;
import com.sky.water.common.Constants;
import com.sky.water.ui.BaseActivity;

/**
 * @author LiBin
 * @ClassName: WelcomeActivity
 * @Description: 欢迎页
 * @date 2015年3月26日 下午4:19:07
 */
public class WelcomeActivity extends BaseActivity {

    public int flag;//是否首次运行，true代表首次
    private int TIME = 400;//handler延迟时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏,在v7中AppCompatActivity下无用
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        ImageView view = new ImageView(this);
        view.setBackgroundResource(R.mipmap.welcome);
        setContentView(view);
        //SPUtils.put(WelcomeActivity.this, "isfirst", true);
        flag = (int) SPUtils.getInstance().get(Constants.USERROLE, 0);
        //加载动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 判断程序与第几次运行，如果是第一次运行引导页面
                        if (!getUserOnlineState()) {
                            jumpActivity(WelcomeActivity.this, LoginActivity.class);
                            finish();
                        } else if (flag == 1) {
                            jumpActivity(WelcomeActivity.this, MainActivity.class);
                            finish();
                        } else {
                            jumpActivity(WelcomeActivity.this, MainUserActivity.class);
                            finish();
                        }
                    }
                }, TIME);
            }
        });
    }

}