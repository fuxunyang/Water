package com.sky.water.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.utils.TextUtil;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.AreaEntity;
import com.sky.water.model.WaterEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.adapter.WaterAdapter;
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
@ContentView(R.layout.activity_wateradmin)
public abstract class BaseWaterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.tv_area)
    protected TextView tv_area;
    @ViewInject(R.id.tv_cun)
    protected TextView tv_cun;
    @ViewInject(R.id.tv_well)
    protected EditText tvWell;

    @ViewInject(R.id.tv_cunname)
    protected TextView cunName;

    @ViewInject(R.id.swipe)//下拉刷新的框架
    protected SwipeRefreshLayout swipe;
    @ViewInject(R.id.recycle)
    protected RecyclerView recyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected WaterAdapter adapter;
    protected int lastVisibleItem;
    protected int total = 0;
    protected int page = 1;
    protected BasePop areaPop;
    protected List<AreaEntity> areas;

    protected String areaID;
    protected String cunID;
    protected BasePop cunPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        toRefresh();
        createAreaShowFloder();
        createCunzhuangShowFloder();
        getArea();
    }

    /**
     * 页面刷新
     */
    protected void toRefresh() {
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeResources(R.color.red, R.color.white, R.color.black);
        swipe.setEnabled(false);
        swipe.setOnRefreshListener(this);//监听

        recyclerView.setHasFixedSize(true);
        adapter = new WaterAdapter(R.layout.adapter_water_item);
        recyclerView.setAdapter(adapter);

        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialog(adapter.getDatas().get(position));
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
                    else showToast("已无更多");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void showDialog(WaterEntity entity) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_water, null);// 得到加载view
        ((TextView) view.findViewById(R.id.tv_title)).setText(entity.getName());
        ((TextView) view.findViewById(R.id.tv_well)).setText(entity.getMachineWellsNum());
        ((TextView) view.findViewById(R.id.tv_well_begin)).setText(entity.getBeginTime());
        ((TextView) view.findViewById(R.id.tv_well_end)).setText(entity.getEndTime());
        ((TextView) view.findViewById(R.id.tv_total01)).setText(entity.getOneTotalWater());
        ((TextView) view.findViewById(R.id.tv_total)).setText(entity.getWellTotalWater());

        final Dialog dialog = new Dialog(this);
        int[] wh = ScreenUtils.getWidthAndHeight(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wh[0] / 5 * 4, wh[1] / 2);
        dialog.setContentView(view, lp);
        dialog.show();
        view.findViewById(R.id.bt_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Event({R.id.layout01, R.id.layout02})
    private void searchOnClick(View view) {
        switch (view.getId()) {
            case R.id.layout01:

                if (areaPop == null) getArea();
                else if (!areaPop.isShowing())
                    areaPop.showAsDropDown(tv_area);
                break;
            case R.id.layout02:
                if (!cunPop.isShowing())
                    cunPop.showAsDropDown(tv_cun);
                break;

        }
    }

    @Event(R.id.img_search)
    private void OnClick(View view) {
        getData();
    }


    private void getData() {
        String well = tvWell.getText().toString().trim();
        if (TextUtil.notNull(areaID, "请选择区域")) return;
//        if (TextUtil.notNull(well, "请填写机井编号")) return;
        getWater(well);
    }

    protected abstract void getWater(String well);

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
                page = 1;
                getData();
                swipe.setRefreshing(false);
                break;
            case 1:
                page++;
                getData();
                setSnack(recyclerView);
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
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("正在加载，请稍后");
                    }
                }).show();
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
                tv_area.setText(data.get(0).getName());
                areaID = data.get(0).getID() + "";
                getCunZhuang();
                areaPop.setDatas(data);
                if (!areaPop.isShowing())
                    areaPop.showAsDropDown(tv_area);
            }
        });
    }

    private void createAreaShowFloder() {
        if (areaPop == null)
            areaPop = new AreaPop(LayoutInflater.from(this).inflate(R.layout.adapter_area, null));
        areaPop.setOnItemClickListener(new BasePop.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaEntity data = (AreaEntity) areaPop.getDatas().get(position);
                tv_area.setText(data.getName());
                areaID = data.getID() + "";
                getCunZhuang();
                areaPop.dismiss();
            }
        });
    }

    private void getCunZhuang() {
        if (TextUtils.isEmpty(areaID)) {
            showToast("请选择城镇");
            return;
        }
        HttpDataUtils.getCunZhuang(areaID, new IDataResultImpl<List<AreaEntity>>() {
            @Override
            public void onSuccessData(List<AreaEntity> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                tv_cun.setText(data.get(0).getName());
                cunID = data.get(0).getID() + "";
                cunPop.setDatas(data);
            }
        });
    }

    private void createCunzhuangShowFloder() {
        if (cunPop == null)
            cunPop = new AreaPop(LayoutInflater.from(this).inflate(R.layout.adapter_area, null));
        cunPop.setOnItemClickListener(new BasePop.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaEntity data = (AreaEntity) cunPop.getDatas().get(position);
                tv_cun.setText(data.getName());
                cunID = data.getID() + "";
                cunPop.dismiss();
            }
        });
    }
}
