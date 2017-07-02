package com.sky.water.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.NewsEntity;
import com.sky.water.ui.BaseFragment;
import com.sky.water.ui.activity.MainActivity;
import com.sky.water.ui.activity.NewsActivity;
import com.sky.water.ui.adapter.MainAdapter;
import com.sky.water.ui.widget.FullyLinearLayoutManager;
import com.sky.water.ui.widget.MyRecycleView;
import com.sky.water.ui.widget.MyScrollview;
import com.sky.water.utils.ScreenUtils;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_main)
public class NewsFragment extends BaseFragment {
    @ViewInject(R.id.scrollView)
    private MyScrollview scroll;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_Group_point)
    private LinearLayout group_point;

    @ViewInject(R.id.recycle)
    private MyRecycleView recyclerView;

    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipe;
    private MainAdapter adapter;

    public static final String ONE = "2";//新闻请求类型
    public static final String TWO = "3";//新闻请求类型
    public static final String THREE = "4";//新闻请求类型
    public static final String FOUR = "5";//新闻请求类型
    public static final String FIVE = "6";//新闻请求类型

    private String type;

    public Fragment setType(String type) {
        this.type = type;
        return this;
    }

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
                case 1://viewpager的轮播图
                    int position = viewPager.getCurrentItem() + 1;
                    if (position == imageViews.length) position = 0;
                    if (MainActivity.flag) viewPager.setCurrentItem(position);
                    handler.sendEmptyMessageDelayed(1, 5000);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();
        setRefresh();
        getData();
    }


    //设置轮播图
    private void setViewPager() {
        ViewGroup.LayoutParams viewPagerLayoutParamsp = viewPager.getLayoutParams();
        viewPagerLayoutParamsp.height = ScreenUtils.getWidthAndHeight(getActivity())[0] * 2 / 5;
        viewPager.setLayoutParams(viewPagerLayoutParamsp);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageDrawable(getResources().getDrawable(images[position % images.length]));
                container.addView(imageView);
                imageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews[position]);
                imageViews[position] = null;
            }
        });
        final View[] points = new View[images.length];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
        lp.setMargins(0, 0, 15, 0);
        for (int i = 0; i < images.length; i++) {
            points[i] = new View(getActivity());
            if (i == 0) points[i].setBackgroundResource(R.drawable.shape_indicator_selected_oval);
            else points[i].setBackgroundResource(R.drawable.shape_indicator_unselected_oval);
            points[i].setLayoutParams(lp);
            group_point.addView(points[i]);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < points.length; i++) {
                    if (i == position % images.length)
                        points[i].setBackgroundResource(R.drawable.shape_indicator_selected_oval);
                    else
                        points[i].setBackgroundResource(R.drawable.shape_indicator_unselected_oval);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state=1时正在滑动，2滑动完毕，0什么都没做
//                if (flag && state == 1) {
//                    handler.removeMessages(Constants.handler_pull);
//                } else if (flag && state == 0) {
//                    handler.sendEmptyMessageDelayed(Constants.handler_pull, 5000);
//                    flag=true;
//                }
            }
        });
        viewPager.setCurrentItem(2 * images.length);
        handler.sendEmptyMessageDelayed(1, 5000);
    }

    /**
     * 页面刷新
     */
    private void setRefresh() {
        scroll.setOnScrollToBottomLintener(new MyScrollview.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                if (isBottom)
                    if (total > adapter.getItemCount()) handler.sendEmptyMessageDelayed(2, 000);
                    else handler.sendEmptyMessageDelayed(3, 000);
            }
        });
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeColors(R.color.red, R.color.white, R.color.black);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0, 000);
            }
        });

        recyclerView.setHasFixedSize(true);
        final FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MainAdapter(R.layout.adapter_main);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("news", adapter.getDatas().get(position));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });
    }

    private void getData() {
        HttpDataUtils.getNewsByType(type, page + "", new IDataResultImpl<ApiResponse<List<NewsEntity>>>() {
            @Override
            public void onSuccessData(ApiResponse<List<NewsEntity>> data) {
                if (!isAdded()) return;
                if (data == null) {
                    Toast.makeText(getActivity(), getString(R.string.error_03), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isRefresh) {
                    Toast.makeText(getActivity(), "已刷新", Toast.LENGTH_SHORT).show();
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
