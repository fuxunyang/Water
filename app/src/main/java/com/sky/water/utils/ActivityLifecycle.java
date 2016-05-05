package com.sky.water.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sky
 * @ClassName: ActivityLifecycle
 * @Description: TODO activity管理类
 * @date 2015年4月12日 下午1:33:47
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private Stack<Activity> activityStack;
    private Activity currentActivity;
    private static ActivityLifecycle instance;
    private Lock lock= new ReentrantLock();
    int nub =1;
    //lock测试
    public void nub(){
        lock.lock();//锁定
        try{
            nub++;
        }finally {
            lock.unlock();//解锁
        }
    }
    public static ActivityLifecycle getInstance() {
        if (instance == null)
            //所有加上synchronized 和 块语句，在多线程访问的时候，同一时刻只能有一个线程能够用
            //同步检查，获得锁，先清空工作内存，即子内存；然后从主内存中拷贝变量的新副本到子内存中，
            // 执行后强制刷新主内存，并释放
            synchronized (ActivityLifecycle.class) {
                if (instance == null)
                    instance = new ActivityLifecycle();
            }
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);//堆如activitymanager管理栈中
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityStack.remove(activity);//销毁时从管理栈中移除
        if (currentActivity == activity) currentActivity = null;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void popAllActivity() {
        while (true) {
            if (!activityStack.isEmpty()) {
                activityStack.lastElement().finish();
                activityStack.remove(activityStack.lastElement());
            } else return;
        }
    }

    public void backToAppointActivity(Class cls) {
        while (true)
            if (!activityStack.lastElement().getClass().equals(cls)) {
                activityStack.lastElement().finish();
                activityStack.remove(activityStack.lastElement());
            } else return;
    }
}
