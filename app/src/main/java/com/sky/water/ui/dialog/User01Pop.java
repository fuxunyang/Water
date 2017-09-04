package com.sky.water.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.sky.api.OnRequestCallback;
import com.sky.water.R;
import com.sky.water.ui.activity.AgricultureActivity;

import static com.sky.utils.JumpAct.jumpActivity;


/**
 * Created by sky on 2017/6/15.
 * 分享弹出框
 */
public class User01Pop extends PopupWindow {
    private OnRequestCallback<Boolean> callback;

    public User01Pop(View contentView) {
        this(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public User01Pop(View contentView, int width, int height) {
        this(contentView, width, height, true);
    }

    public User01Pop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);

        setAnimationStyle(R.style.AnimationBottomFade);
//        setAnimationStyle(R.style.AnimationFade);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        final Context context = contentView.getContext();
        contentView.findViewById(R.id.layout_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(context, AgricultureActivity.class);
                dismiss();
            }
        });
    }

    public void setCallback(OnRequestCallback<Boolean> callback) {
        this.callback = callback;
    }
}
