package com.sky.water.api;


import com.sky.water.ui.MyApplication;

import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;

/**
 * @author sky QQ:1136096189
 * @Description: idataresult的实现类
 * @date 15/11/20 下午1:59
 */
public abstract class IDataResultImpl<T> implements IDataResult<T>{
    @Override
    public void onFailure(HttpException exception, int code) {
        LogUtil.d("请求失败" + code);
        MyApplication.getInstance().showErroe(code);
        onSuccessData(null);
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
    }

    @Override
    public abstract void onSuccessData(T data);

    @Override
    public void onStart() {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFinish() {
    }
}
