package com.sky.water.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.AreaEntity;
import com.sky.water.model.SoilEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.adapter.SoilAdapter;
import com.sky.water.ui.dialog.AreaPop;
import com.sky.water.ui.dialog.BasePop;
import com.sky.water.utils.ScreenUtils;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/21 下午4:32
 */
@ContentView(R.layout.activity_soil)
public class SoilActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.tv_card)
    private TextView et_card;
    @ViewInject(R.id.img_01)
    private ImageView img_01;
//    @ViewInject(R.id.imgbt_search)
//    private ImageButton search;

    @ViewInject(R.id.bt_temp)
    private Button bt_temp;
    @ViewInject(R.id.bt_water)
    private Button bt_water;
    @ViewInject(R.id.tv_temporwater)
    private TextView tv_temporwater;


    @ViewInject(R.id.swipe)//下拉刷新的框架
    private SwipeRefreshLayout swipe;
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SoilAdapter adapter;
    private int lastVisibleItem;

    private BasePop areaPop;
    private List<AreaEntity> areas;
    private int total = 0;
    private boolean isDown;
    private String areaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        toRefresh();

        getArea();
        bt_temp.setBackgroundResource(R.mipmap.ic_soil_pressed);
    }

    private void getArea() {
        showLoading();
        HttpDataUtils.getArea(new IDataResultImpl<List<AreaEntity>>() {
            @Override
            public void onSuccessData(List<AreaEntity> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                areas = data;
                et_card.setText(data.get(0).getName());
                createAreaShowFloder(data);
                if (!areaPop.isShowing())
                    areaPop.showAsDropDown(et_card);
            }
        });
    }

    /**
     * 页面刷新
     */
    private void toRefresh() {
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeColors(R.color.red, R.color.white, R.color.black);
        swipe.setEnabled(false);
        swipe.setOnRefreshListener(this);//监听

        recyclerView.setHasFixedSize(true);
        adapter = new SoilAdapter(R.layout.adapter_soil_item);
        recyclerView.setAdapter(adapter);

        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpActivity(SoilActivity.this, LineChartActivity.class,
                        "id", adapter.getDatas().get(position).getID(),
                        "tempOrWater", adapter.getTempOrWater() + "");
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (total > adapter.getItemCount()) handler.sendEmptyMessageDelayed(1, 000);
                    else if (isDown) showToast("已无更多");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动
                if (dy < 0) isDown = true;
                else isDown = false;

                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

//    @Event(R.id.imgbt_search)
//    private void searchOnClick(View view) {
//        getSoils();
//    }

//    private void getSoils() {
//        String areaName = et_card.getText().toString().trim();
//        if (TextUtils.isEmpty(areaName)) {
//            showToast("地名不能为空");
//            return;
//        }
////        for (AreaEntity area : areas) {
////            if (area.getName().contains(areaName)) {
////                getSoil(area.getID() + "");
////                return;
////            }
////        }
//    }

    @Event({R.id.tv_card, R.id.img_01})
    private void AreaOnClick(View view) {
        if (!areaPop.isShowing())
            areaPop.showAsDropDown(et_card);
    }

    @Event({R.id.bt_temp, R.id.bt_water})
    private void tempORWaterOnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_temp:
                tv_temporwater.setText(getString(R.string.list_10));
                bt_temp.setBackgroundResource(R.mipmap.ic_soil_pressed);
                bt_water.setBackgroundResource(R.mipmap.ic_soil_normal);
                adapter.setTempOrWater(1);

                break;
            case R.id.bt_water:
                tv_temporwater.setText(getString(R.string.list_11));
                bt_temp.setBackgroundResource(R.mipmap.ic_soil_normal);
                bt_water.setBackgroundResource(R.mipmap.ic_soil_pressed);
                adapter.setTempOrWater(2);
                break;

        }
    }

    private void getSoil(String areaId) {
        showLoading();
        HttpDataUtils.getSoil(areaId, new IDataResultImpl<ApiResponse<List<SoilEntity>>>() {
            @Override
            public void onSuccessData(ApiResponse<List<SoilEntity>> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                total = data.getTotal();
                adapter.setDatas(data.getRows());
//                if (page == 1) adapter.setDatas(data.getRows());
//                else adapter.addDatas(data.getRows());
            }
        });
    }

    @Override
    public void onRefresh() {
        //swipe刷新
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void handler(Message msg) {
        super.handler(msg);
        switch (msg.what) {
            case 0:
                swipe.setRefreshing(false);
                break;
            case 1:
                setSnack(recyclerView);
//                getSoils();
                break;
            case 0x99:
                getSoil(msg.arg1 + "");
                break;

        }
    }

    /**
     * 设置SnackBar
     *
     * @param view
     */
    public void setSnack(final View view) {
        Snackbar.make(view, "正在加载，请稍后", Snackbar.LENGTH_SHORT)
                .setAction("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("已取消");
                    }
                }).show();
    }


    private void createAreaShowFloder(final List<AreaEntity> datas) {
        int[] wh = ScreenUtils.getWidthAndHeight(this);
        if (areaPop == null)
            areaPop = new AreaPop(LayoutInflater.from(this).inflate(R.layout.adapter_area, null),
                    wh[0], wh[1]);
        areaPop.setDatas(datas);
        areaPop.setOnItemClickListener(new BasePop.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaEntity data = datas.get(position);
                et_card.setText(data.getName());
                Message message = handler.obtainMessage();
                message.what = 0x99;
                message.arg1 = data.getID();
                handler.sendMessage(message);
                areaPop.dismiss();
            }
        });
    }
}
