package com.sky.water.ui.activity;

import android.os.Bundle;
import android.os.Message;

import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.RateDate;
import com.sky.water.model.SoilEntity;
import com.sky.water.ui.BaseActivity;
import com.sky.water.ui.widget.LineChartView;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/24 下午5:17
 */
@ContentView(R.layout.activity_linechart)
public class LineChartActivity extends BaseActivity {

    @ViewInject(R.id.linechart)
    private LineChartView linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        final String tempOrWater = getIntent().getStringExtra("tempOrWater");
        if (tempOrWater.equals("1")) setToolbar(getString(R.string.lable_15));
        else if (tempOrWater.equals("2")) setToolbar(getString(R.string.lable_16));
        showLoading();
        HttpDataUtils.getDiagramData(id, new IDataResultImpl<List<SoilEntity>>() {
            @Override
            public void onSuccessData(List<SoilEntity> data) {
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    hideLoading();
                    return;
                }
                Message message = handler.obtainMessage();
                message.what = 0x100;
                message.arg1 = Integer.parseInt(tempOrWater);
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void handler(Message msg) {
        switch (msg.what) {
            case 0x100:
                List<SoilEntity> soils = (List<SoilEntity>) msg.obj;
                List<RateDate> rateDates = new ArrayList<>();
                RateDate rateDate = null;
                for (SoilEntity soil : soils) {
                    rateDate = new RateDate();
                    rateDate.setDate(soil.getCollectDate());
                    if (msg.arg1 == 1) {
                        rateDate.setSoil10(Float.parseFloat(soil.getChannel2()));
                        rateDate.setSoil20(Float.parseFloat(soil.getChannel4()));
                        rateDate.setSoil40(Float.parseFloat(soil.getChannel6()));
                        rateDate.setType("℃");
                    } else if (msg.arg1 == 2) {
                        rateDate.setSoil10(Float.parseFloat(soil.getChannel1()));
                        rateDate.setSoil20(Float.parseFloat(soil.getChannel3()));
                        rateDate.setSoil40(Float.parseFloat(soil.getChannel5()));
                        rateDate.setType("%");
                    }
                    rateDates.add(rateDate);
                }
                Collections.sort(rateDates, sDisplayNameComparator);
                for (RateDate rate : rateDates) {
                    rate.setDate(cutdate(rate.getDate()));
                }
                linechart.setRateDates(rateDates);
                hideLoading();
                break;
        }

    }

    private String cutdate(String date) {
        int last = date.lastIndexOf("-");
        return date.substring(last-2, last + 3);
    }

    /**
     * 为筛选出的act进行排序
     */
    private final static Comparator<RateDate> sDisplayNameComparator =
            new Comparator<RateDate>() {
                private final Collator collator = Collator.getInstance();

                @Override
                public int compare(RateDate lhs, RateDate rhs) {
                    return collator.compare(lhs.getDate(), rhs.getDate());
                }
            };

}
