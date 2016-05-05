package com.sky.water.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.WaterEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.adapter.WaterAdapter;
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
@ContentView(R.layout.activity_water)
public class WaterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.et_card)
    private EditText et_card;
    @ViewInject(R.id.imgbt_search)
    private ImageButton search;

    @ViewInject(R.id.swipe)//下拉刷新的框架
    private SwipeRefreshLayout swipe;
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private WaterAdapter adapter;
    private int lastVisibleItem;
    private int total = 0;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        toRefresh();
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
        adapter = new WaterAdapter(R.layout.adapter_water_item);
        adapter.setUserOnlineState(getUserOnlineState());
        recyclerView.setAdapter(adapter);

        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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

    @Event(R.id.imgbt_search)
    private void searchOnClick(View view) {
        page = 1;
        getData();
    }


    private void getData() {
        String card = et_card.getText().toString().trim();
        if (TextUtils.isEmpty(card)) {
            showToast("卡号不能为空");
            return;
        }
        showLoading();
        HttpDataUtils.getIrrigationWaters(card, page + "", new IDataResultImpl<ApiResponse<List<WaterEntity>>>() {
            @Override
            public void onSuccessData(ApiResponse<List<WaterEntity>> data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                total = data.getTotal();
                if (page == 1) adapter.setDatas(data.getRows());
                else adapter.addDatas(data.getRows());

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
}
