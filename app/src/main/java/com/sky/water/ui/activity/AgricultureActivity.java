package com.sky.water.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sky.utils.TextUtil;
import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.NewsEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.adapter.MainAdapter;
import com.sky.water.ui.widget.FullyLinearLayoutManager;
import com.sky.water.ui.widget.MyRecycleView;
import com.sky.water.utils.http.HttpDataUtils;
import com.sky.widget.EditTextDel;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2017/4/12.
 */
@ContentView(R.layout.activity_agriculture)
public class AgricultureActivity extends BaseActivity {

    @ViewInject(R.id.edit)
    private EditTextDel edit;
    @ViewInject(R.id.recycle)
    private MyRecycleView recyclerView;

    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipe;
    private MainAdapter adapter;
    private int page = 1;//当前新闻页数
    private int total = 0;//新闻总条数
    //轮播图数组
    private int[] images = {R.mipmap.ic_01, R.mipmap.ic_02, R.mipmap.ic_03, R.mipmap.ic_04, R.mipmap.ic_05};
    private ImageView[] imageViews = new ImageView[100];
    private boolean isRefresh = false;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0://swipe刷新操作
                    isRefresh = true;
                    page = 1;
                    if (adapter.getDatas() != null) adapter.getDatas().clear();
                    getData();
                    swipe.setRefreshing(false);
                    break;
                case 2:
                    page++;
                    getData();
                    setSnack(recyclerView, "正在加载，请稍后", "ok");
                    break;
                case 3:
                    setSnack(recyclerView, "已无更多", "ok");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setRefresh();
        getData();
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length() > 0)
                getData();

            }
        });
    }

    /**
     * 页面刷新
     */
    private void setRefresh() {
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeResources(R.color.red, R.color.white, R.color.black);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0, 000);
            }
        });

        recyclerView.setHasFixedSize(true);
        final FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MainAdapter(R.layout.adapter_main);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AgricultureActivity.this, NewsActivity.class);
                intent.putExtra("news", adapter.getDatas().get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });
    }

    private void getData() {
        String title = TextUtil.getText(edit);
        HttpDataUtils.tbNewPushBTGetList(title, page, new IDataResultImpl<ApiResponse<List<NewsEntity>>>() {
            @Override
            public void onSuccessData(ApiResponse<List<NewsEntity>> data) {
                if (data == null) {
                    Toast.makeText(AgricultureActivity.this, getString(R.string.error_03), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isRefresh) {
                    Toast.makeText(AgricultureActivity.this, "已刷新", Toast.LENGTH_SHORT).show();
                    isRefresh = false;
                }
                total = data.getTotal();
                if (page == 1) adapter.setDatas(data.getRows());
                else adapter.addDatas(data.getRows());
            }
        });
    }

    /**
     * 设置SnackBar
     *
     * @param view
     */
    public void setSnack(final View view, String text1, String text2) {
        Snackbar.make(view, text1, Snackbar.LENGTH_SHORT).setAction(text2, null).show();
    }

}
