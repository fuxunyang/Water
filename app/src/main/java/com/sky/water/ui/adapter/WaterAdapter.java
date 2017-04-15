package com.sky.water.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.utils.SPUtils;
import com.sky.water.R;
import com.sky.water.common.Constants;
import com.sky.water.model.WaterEntity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;


/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/9 下午8:52
 */
public class WaterAdapter extends BaseAdapter<WaterEntity, BaseHolder> {
//    private boolean userOnlineState = false;//是否超标

    public WaterAdapter(int layoutId) {
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
        TextView local = holder.getView(R.id.tv_local);
        holder.setText(R.id.tv_local, datas.get(position).getName());
        holder.setText(R.id.tv_time, datas.get(position).getCollectDate());
//        holder.setText(R.id.tv_water_use, datas.get(position).getWaterValue());
        TextView total = holder.getView(R.id.tv_total_water);
        int userRole = (int) SPUtils.getInstance().get(Constants.USERROLE, 0);
        if (userRole == 0){
            total.setText(datas.get(position).getOneTotalWater());
        } else{
            total.setTextColor(local.getTextColors());
            if (datas.get(position).getWellTotalWaterThreshold().equals("2"))
                total.setTextColor(context.getResources().getColor(R.color.red));
            total.setText(datas.get(position).getWellTotalWater());
        }
    }

//    public void setUserOnlineState(boolean userOnlineState) {
//        this.userOnlineState = userOnlineState;
//    }
}