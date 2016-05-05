package com.sky.water.ui.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.water.R;
import com.sky.water.model.AreaEntity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/11 下午1:14
 */
public class AreaPop extends BasePop<AreaEntity> {
    private RecyclerView recycle;

    private BaseAdapter<AreaEntity, BaseHolder> adapter;

    public AreaPop(View view, int width, int height) {
        super(view, width, height);
    }

    @Override
    protected void initView() {
        super.initView();
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        adapter = new BaseAdapter<AreaEntity, BaseHolder>(R.layout.pop_area_item) {
            @Override
            protected BaseHolder onCreateBodyHolder(View view) {
                return new BaseHolder(view);
            }

            @Override
            protected void onAchieveHolder(BaseHolder holder, int position) {
                holder.setText(R.id.tv, datas.get(position).getName());
            }
        };
        recycle.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                TextView tv= (TextView) view;
                if (itemClickListener != null)
                    itemClickListener.onItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initDatas() {
        adapter.setDatas(popDatas);
    }
}
