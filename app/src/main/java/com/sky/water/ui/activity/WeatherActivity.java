package com.sky.water.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ResultsEntity;
import com.sky.water.model.WeatherEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/21 下午4:32
 */
@ContentView(R.layout.activity_weather)
public class WeatherActivity extends BaseActivity {

    @ViewInject(R.id.tv_time_01)
    private TextView tv_time_01;
    @ViewInject(R.id.tv_time_02)
    private TextView tv_time_02;
    @ViewInject(R.id.tv_time_03)
    private TextView tv_time_03;

    @ViewInject(R.id.img_weather)
    private ImageView img_weather;
    @ViewInject(R.id.tv_weather)
    private TextView tv_weather;
    @ViewInject(R.id.tv_local)
    private TextView tv_local;
    @ViewInject(R.id.tv_temp_now)
    private TextView tv_temp_now;

    @ViewInject(R.id.tv_temp_01)
    private TextView tv_temp_01;
    @ViewInject(R.id.tv_date_01)
    private TextView tv_date_01;
    @ViewInject(R.id.tv_temp_02)
    private TextView tv_temp_02;
    @ViewInject(R.id.tv_date_02)
    private TextView tv_date_02;
    @ViewInject(R.id.tv_temp_03)
    private TextView tv_temp_03;
    @ViewInject(R.id.tv_date_03)
    private TextView tv_date_03;
    @ViewInject(R.id.tv_temp_04)
    private TextView tv_temp_04;
    @ViewInject(R.id.tv_date_04)
    private TextView tv_date_04;

    @ViewInject(R.id.wind)
    private TextView wind;
    @ViewInject(R.id.wind01)
    private TextView wind01;


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    public void onCreate() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setTimeOut(10000);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        onCreate();
        initLocation();
        mLocationClient.start();
    }

    private void getWeather(String lnglat) {
        showLoading();//"117.169729,39.119415"
        HttpDataUtils.getWeather(lnglat, new IDataResultImpl<WeatherEntity>() {
            @Override
            public void onSuccessData(WeatherEntity data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                LogUtil.i(data.toString());
                ResultsEntity resultsEntity = data.getResults().get(0);

                tv_time_02.setText(data.getDate());
                tv_time_03.setText(cutWeek(resultsEntity.getWeather_data().get(0).getDate()));
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour > 6 && hour < 18)
                    x.image().bind(img_weather, resultsEntity.getWeather_data().get(0).getDayPictureUrl());
                else
                    x.image().bind(img_weather, resultsEntity.getWeather_data().get(0).getNightPictureUrl());
                tv_weather.setText(resultsEntity.getWeather_data().get(0).getWeather());
//                tv_local.setText(resultsEntity.getCurrentCity());
                tv_local.setText("蓟县");
                tv_temp_now.setText(cutTemp(resultsEntity.getWeather_data().get(0).getDate()));

                tv_temp_01.setText(resultsEntity.getWeather_data().get(0).getTemperature());
                tv_date_01.setText(cutWeek(resultsEntity.getWeather_data().get(0).getDate()));
                tv_temp_02.setText(resultsEntity.getWeather_data().get(1).getTemperature());
                tv_date_02.setText(resultsEntity.getWeather_data().get(1).getDate());
                tv_temp_03.setText(resultsEntity.getWeather_data().get(2).getTemperature());
                tv_date_03.setText(resultsEntity.getWeather_data().get(2).getDate());
                tv_temp_04.setText(resultsEntity.getWeather_data().get(3).getTemperature());
                tv_date_04.setText(resultsEntity.getWeather_data().get(3).getDate());

                wind.setText("今天：" + resultsEntity.getWeather_data().get(0).getWind());
                wind01.setText("明天：" + resultsEntity.getWeather_data().get(1).getWind());
            }
        });
    }

    /**
     * "date" : "周六 01月23日 (实时：-13℃)"
     *
     * @param date
     * @return
     */
    private String cutWeek(String date) {
        return date.substring(0, 2);
    }

    private String cutTemp(String date) {
        int start = date.lastIndexOf("：");
        int last = date.lastIndexOf("℃");
        return date.substring(start + 1, last + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            getWeather(location.getLongitude() + "," + location.getLatitude());
        }
    }
}

