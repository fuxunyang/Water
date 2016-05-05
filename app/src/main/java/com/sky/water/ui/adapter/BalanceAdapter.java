package com.sky.water.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.sky.water.R;
import com.sky.water.model.WaterEntity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;


/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/9 下午8:52
 */
public class BalanceAdapter extends BaseAdapter<WaterEntity, BaseHolder> {
    public BalanceAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected BaseHolder onCreateBodyHolder(View view) {
        return new BaseHolder(view);//19139763,1215232
    }

    @Override
    protected void onAchieveHolder(BaseHolder holder, int position) {
        LinearLayout layout = holder.getView(R.id.layout);
        if (position % 2 == 1) {
            layout.setBackgroundResource(R.color.darkgray);
        } else if (position % 2 == 0) {
            layout.setBackgroundResource(R.color.lightgray);
        }
        //Name为所属区域，CollectDate为采集时间，
        // WellTotalWater为总用水量，MachineWellsNum为机井编号（名称），
        // balance为当前余额，total为全部数据总条数。
//        holder.setText(R.id.tv_local, position + 1 + "." + datas.get(position).getName());
        holder.setText(R.id.tv_local,datas.get(position).getName());
        holder.setText(R.id.tv_time, datas.get(position).getCollectDate() + "");
        holder.setText(R.id.tv_well, datas.get(position).getMachineWellsNum() + "");
//        holder.setText(R.id.tv_water_use, datas.get(position).getWaterValue() + "");
//        holder.setText(R.id.tv_spend, datas.get(position).getWaterDepth() + "");
    }
}