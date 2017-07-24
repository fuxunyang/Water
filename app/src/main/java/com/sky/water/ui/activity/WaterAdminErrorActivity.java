package com.sky.water.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.sky.water.R;
import com.sky.water.api.IDataResultImpl;
import com.sky.water.model.ApiResponse;
import com.sky.water.model.WaterEntity;
import com.sky.water.utils.http.HttpDataUtils;

import org.xutils.view.annotation.ContentView;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/21 下午4:32
 */
@ContentView(R.layout.activity_wateradmin)
public class WaterAdminErrorActivity extends BaseWaterActivity implements SwipeRefreshLayout.OnRefreshListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cunName.setText("所属村庄");
    }

    @Override
    protected void getWater(String well) {
        showLoading();
        HttpDataUtils.getWaterAdminError(areaID, cunID, well, page + "", new IDataResultImpl<ApiResponse<List<WaterEntity>>>() {
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
}
