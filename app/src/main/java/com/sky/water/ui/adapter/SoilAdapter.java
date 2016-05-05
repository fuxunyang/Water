package com.sky.water.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.sky.water.R;
import com.sky.water.model.SoilEntity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;


/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/9 下午8:52
 */
public class SoilAdapter extends BaseAdapter<SoilEntity, BaseHolder> {
    private int tempOrWater = 1;

    public SoilAdapter(int layoutId) {
        super(layoutId);
    }

    public int getTempOrWater() {
        return tempOrWater;
    }

    /**
     * 温度与含水率的切换
     *
     * @param tempOrWater 1温度，2含水率
     */
    public void setTempOrWater(int tempOrWater) {
        this.tempOrWater = tempOrWater;
        notifyDataSetChanged();
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
        //返回结果中DeviceID为ID,Name为所属区域，
        //CollectDate为采集时间。
        holder.setText(R.id.tv_local, datas.get(position).getName());
        holder.setText(R.id.tv_caiji, datas.get(position).getCollectDate());
        if (tempOrWater == 1) {
            //土壤温度10cm、20cm、40cm分别对应字段Channel2、Channel4、Channel6，
            holder.setText(R.id.tv_10, datas.get(position).getChannel2());
            holder.setText(R.id.tv_20, datas.get(position).getChannel4());
            holder.setText(R.id.tv_40, datas.get(position).getChannel6());
        } else if (tempOrWater == 2) {
            //土壤湿度10cm、20cm、40cm分别对应字段Channel1、Channel3、Channel5，
            holder.setText(R.id.tv_10, datas.get(position).getChannel1());
            holder.setText(R.id.tv_20, datas.get(position).getChannel3());
            holder.setText(R.id.tv_40, datas.get(position).getChannel5());
        }

    }
}