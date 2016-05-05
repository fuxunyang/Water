package com.sky.water.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 彬 QQ 1136096189
 * @Description: Toast统一管理类
 * @date 2015/8/17 15:30
 */
public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;//是否提示

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) Toast.makeText(context, message, duration).show();
    }

    public static void showWithCustomView(Context context, int contentLayoutId, int textViewId,
                                          String msg) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(contentLayoutId, null);
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView tv = (TextView) toastRoot.findViewById(textViewId);
        tv.setText(msg);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public static void showError(Context context, int code) {
        String errorMsg = code + "";
        switch (code) {
            case 404:
                errorMsg = "文件找不到";
                break;

            default:
                int codes = code / 100;
                if (codes == 1 || codes == 2 || codes == 3 || codes == 5) {
                    return;
                } else if (codes == 4) {
                    // 4xx系列http错误，需要处理
                    errorMsg = "未知错误";
                }

                break;
        }

        showShort(context, errorMsg);
    }

}