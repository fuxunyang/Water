package com.sky.water.api;

/**
 * @author LiBin
 * @ClassName: IBase
 * @Description: TODO Activity的支持类接口，主要定义了Activity中常用的功能
 * @date 2015年3月26日 下午4:01:00
 */
public interface IBase {
    /**
     * 获取用户名
     */
    String getUserName();
    /**
     * 显示dialog
     */
    void showLoading();
    /**
     * 隐藏 dialog
     */
    void hideLoading();
    /**
     * 用户是否在线（当前网络是否重连成功）
     */
    boolean getUserOnlineState();

    /**
     * 设置用户在线状态 true 在线 false 不在线
     *
     * @param isOnline
     */
    void setUserOnlineState(boolean isOnline);

    /**
     * 开启服务
     */
    void startService();

    /**
     * 开启服务
     */
    void bindService();

    /**
     * 停止服务
     */
    void stopService();

    /**
     * 判断是否有网络连接,没有返回false
     */
    boolean hasInternetConnected();
    /**
     * 退出应用
     * 默认主题 ，默认为0
     */
    void isExit();
}
