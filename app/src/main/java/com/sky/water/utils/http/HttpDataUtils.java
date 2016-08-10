package com.sky.water.utils.http;


import com.sky.water.BuildConfig;
import com.sky.water.api.IDataResult;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.api.RequestCallBack;
import com.sky.water.common.Constants;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.AreaEntity;
import com.sky.water.model.NewsEntity;
import com.sky.water.model.SoilEntity;
import com.sky.water.model.WaterEntity;
import com.sky.water.model.WeatherEntity;
import com.sky.water.ui.activity.ErrorLogActivity;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * @author sky
 * @ClassName: DensityUtils
 * @Description: TODO 数据请求类，待修改
 * @date 2015年4月12日 下午1:33:47
 */
public class HttpDataUtils extends HttpUtilsBase {
    /**
     * 获取天气信息
     *
     * @param lnglat
     * @param callback
     */
    public static void getWeather(String lnglat, final IDataResultImpl<WeatherEntity> callback) {
        RequestParams params = new RequestParams("http://api.map.baidu.com/telematics/v3/weather");
        params.addQueryStringParameter("location", lnglat);
        params.addQueryStringParameter("output", "json");
        params.addQueryStringParameter("ak", Constants.AK);
        params.addQueryStringParameter("coord_type", "bd09ll");
//        params.addBodyParameter("", "");
        x.http().get(params, new RequestCallBack<WeatherEntity>(callback) {
            @Override
            public void onSuccess(WeatherEntity result) {
                if (result != null) callback.onSuccessData(result);
                else callback.onSuccessData(null);
            }
        });
    }

    /**
     * 登录接口
     *
     * @param name
     * @param pass
     * @param callback
     */
    public static void login(String name, String pass, final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/ManagerLogin");
        params.addBodyParameter("userName", name);
        params.addBodyParameter("passWord", pass);
        x.http().post(params, new RequestCallBack<String>(callback) {
            @Override
            public void onSuccess(String result) {
                LogUtil.d(result);
                if (result != null) callback.onSuccessData(result);
                else callback.onSuccessData(null);
            }
        });
    }

    /**
     * 注册
     *
     * @param TrueName 姓名
     * @param UserID   身份证号
     * @param UserName 昵称
     * @param Pwd      密码
     * @param AreaID   区域id
     * @param PHNo     手机
     * @param callback
     */
    public static void tbAppUsersAdd(String TrueName, String UserID, String UserName,
                                     String Pwd, String AreaID, String PHNo,
                                     final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/tbAppUsersAdd");
        params.addBodyParameter("TrueName", TrueName);
        params.addBodyParameter("UserID", UserID);
        params.addBodyParameter("UserName", UserName);
        params.addBodyParameter("Pwd", Pwd);
        params.addBodyParameter("AreaID", AreaID);
        params.addBodyParameter("PHNo", PHNo);
//        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<String>(callback) {

            @Override
            public void onSuccess(String result) {
                if (result != null) callback.onSuccessData(result);
                else callback.onSuccessData(null);
            }
        });
    }

    /**
     * 获取新闻列表
     * 1	水利概况
     * 2	农田灌溉
     * 3	农村排水
     * 4	农村供水
     * 5	水环境
     * 6	水土保持
     * 7	节水技术推广
     * 8	工作简报
     * 9	政策法规
     * 10	公告通知
     * 11	领导讲话
     * 12	体制改革及推广
     *
     * @param newsType
     * @param page
     * @param callback
     * @return
     */
    public static RequestHandler getNewsByType(String newsType, String page, final IDataResultImpl<ApiResponse<List<NewsEntity>>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetNewsByType");
        params.setCharset("gbk");
        params.addBodyParameter("newsType", newsType);
        params.addBodyParameter("page", page);
//        LogUtil.i("newsType="+newsType);
//        params.addHeader();
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<ApiResponse<List<NewsEntity>>>(callback) {
                    @Override
                    public void onSuccess(ApiResponse<List<NewsEntity>> result) {
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * 获取地区
     *
     * @param callback
     * @return
     */
    public static RequestHandler getArea(final IDataResultImpl<List<AreaEntity>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetArea");
        params.setCharset("gbk");
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<ApiResponse<List<AreaEntity>>>(callback) {
                    @Override
                    public void onSuccess(ApiResponse<List<AreaEntity>> result) {
                        if (result != null) callback.onSuccessData(result.getRows());
                        else callback.onSuccessData(null);
                    }
                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * 土壤墒情
     *
     * @param areaId
     * @param callback
     * @return
     */
    public static RequestHandler getSoil(String areaId, final IDataResultImpl<ApiResponse<List<SoilEntity>>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetSoilInformation");
        params.addBodyParameter("areaID", areaId);
        params.setCharset("gbk");

        // 请求
        final Callback.Cancelable request = x.http().get(params,
                new RequestCallBack<ApiResponse<List<SoilEntity>>>(callback) {
                    @Override
                    public void onSuccess(ApiResponse<List<SoilEntity>> result) {
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }
                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * @param DeviceID
     * @param callback
     * @return
     */
    public static RequestHandler getDiagramData(String DeviceID, final IDataResultImpl<List<SoilEntity>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetDiagramData");
        params.addBodyParameter("ID", DeviceID);
        params.setCharset("gbk");

        // 请求
        final Callback.Cancelable request = x.http().get(params,
                new RequestCallBack<ApiResponse<List<SoilEntity>>>(callback) {
                    @Override
                    public void onSuccess(ApiResponse<List<SoilEntity>> result) {
                        if (result != null) callback.onSuccessData(result.getRows());
                        else callback.onSuccessData(null);
                    }
                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * 获取灌溉水量
     *
     * @param cardNO
     * @param page
     * @param callback
     * @return
     */
    public static RequestHandler getIrrigationWaters(String cardNO, String page, final IDataResultImpl<ApiResponse<List<WaterEntity>>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetIrrigationWaters");
        params.addBodyParameter("cardNO", cardNO);
        params.addBodyParameter("page", page);
        params.setCharset("gbk");
//        params.addHeader();
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<ApiResponse<List<WaterEntity>>>(callback) {
                    @Override
                    public void onSuccess(ApiResponse<List<WaterEntity>> result) {
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * 获取灌溉水量
     *
     * @param cardNO
     * @param callback
     * @return
     */
    public static RequestHandler getBalance(String cardNO, final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetBalance");
        params.setCharset("gbk");
        params.addBodyParameter("cardNO", cardNO);
//        params.addHeader();
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    public static RequestHandler getVersion(final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/GetVersion");
        params.setCharset("gbk");
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    public static RequestHandler getApp(String url, final IDataResult<String> callback) {
        RequestParams params = new RequestParams(url);
        params.setCharset("gbk");
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        callback.onSuccessData(result.getPath());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException e = (HttpException) ex;
                            callback.onFailure(e, e.getCode());
                            if (BuildConfig.DEBUG)
                                ErrorLogActivity.startThis(ex.getMessage() + "");
                        } else if (ex instanceof NullPointerException) {
                            callback.onFailure(null, 0);

                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        callback.onCancel();
                    }

                    @Override
                    public void onFinished() {
                        callback.onFinish();
                    }

                    @Override
                    public void onWaiting() {
                    }

                    @Override
                    public void onStarted() {
                        callback.onStart();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        callback.onLoading(total, current, isDownloading);
                    }
                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }


    public static RequestHandler tbAppUsersExAdd(String AppUsersID, String MachineWellsCommunicationNoID
            , final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/tbAppUsersExAdd");
        params.setCharset("gbk");
        params.addBodyParameter("AppUsersID", AppUsersID);
        params.addBodyParameter("MachineWellsCommunicationNoID", MachineWellsCommunicationNoID);
//        params.addHeader();
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(result);
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    public static RequestHandler tbAppUsersExGetList(String where, final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/tbAppUsersExGetList");
        params.setCharset("gbk");
        params.addBodyParameter("where", where);
        // 请求
        final Callback.Cancelable request = x.http().post(params,
                new RequestCallBack<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(result);
                        if (result != null) callback.onSuccessData(result);
                        else callback.onSuccessData(null);
                    }

                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }
}