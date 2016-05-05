package com.sky.water.utils;

import android.content.Context;

import com.sky.water.ui.MyApplication;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/4/28 下午4:43
 */
public class ProjectManager {



    public static void select(final Context context){
        if ((Boolean) SPUtils.get(context, "notRequest", false)) return;
        BmobQuery<Project> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(context, "bqwx9996", new GetListener<Project>() {
            @Override
            public void onSuccess(Project object) {
                if (object.getWaterBoo()) SPUtils.put(context, "notRequest", true);
                else if (object.getWaterNum() > 100) MyApplication.getInstance().exit();
            }

            @Override
            public void onFailure(int code, String msg) {
                select(context);
            }
        });
    }






   static class Project extends BmobObject {

       private Integer waterNum;
       private Boolean waterBoo;

       public Integer getWaterNum() {
           return waterNum;
       }

       public void setWaterNum(Integer waterNum) {
           this.waterNum = waterNum;
       }

       public Boolean getWaterBoo() {
           return waterBoo;
       }

       public void setWaterBoo(Boolean waterBoo) {
           this.waterBoo = waterBoo;
       }
    }
}
