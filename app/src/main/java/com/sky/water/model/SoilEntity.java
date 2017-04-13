package com.sky.water.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/26 下午2:53
 */
public class SoilEntity implements Serializable {

    //        private int R;
    private int AreaID;
    //        private String Address;
//        private String simNO;
//        private String Line;
//        private String Down;
//        private String Cai;
//        private String Coordinate;
//        private String ADate;
//        private String UnismType;
//        private int ParentId;
//        private String Code;
    private String Name;
    //        private String AreaLevel;
//        private int Status;
//        private String ParentName;
    private String ID;
    //        private String JingWater;
//        private String DongWater;
//        private String Machine;
//        private String WaterPump;
//        private boolean ISSoil;
    private String CodeNO;
    //        private String Equipment;
//        private String Flora;
//        private String Properties;
//        private String LayoutType;
//        private String UnismName;
//        private String Floor;
//        private String Remark;
//        private int RemoveFlag;
//        private int tbUnismDeviceRecordID;
    private String CollectDate;
    //        private String RecordDate;
    private String Channel1;
    //        private String ChannelThreshold1;
//        private String ChannelT1;
    private String Channel2;
    //        private String ChannelThreshold2;
    private String Channel3;
    //        private String ChannelThreshold3;
//        private String ChannelT2;
    private String Channel4;
    //        private String ChannelThreshold4;
    private String Channel5;
    //        private String ChannelThreshold5;
//        private String ChannelT3;
    private String Channel6;
    private String ChannelT1;
    private String ChannelT2;
    private String ChannelT3;
    //        private String ChannelThreshold6;
//        private String Channel7;
//        private String ChannelThreshold7;
//        private String Channel8;
//        private String ChannelThreshold8;
//        private String Channel9;
//        private String ChannelThreshold9;
//        private String UnismTypeName;
//        private String WaterDepth;
//        private String WaterElevation;
    private String DeviceID;

    private String WaterElevationThreshold;

    public String getCollectDate() {
        return TextUtils.isEmpty(CollectDate) ? CollectDate : CollectDate.replace("T", " ");
    }

    public int getAreaID() {
        return AreaID;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public String getCodeNO() {
        return CodeNO;
    }

    public String getChannel1() {
        return Channel1;
    }

    public String getChannel2() {
        return Channel2;
    }

    public String getChannel3() {
        return Channel3;
    }

    public String getChannel4() {
        return Channel4;
    }

    public String getChannel5() {
        return Channel5;
    }

    public String getChannel6() {
        return Channel6;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public String getWaterElevationThreshold() {
        return WaterElevationThreshold;
    }

    public String getChannelT1() {
        return ChannelT1;
    }

    public String getChannelT2() {
        return ChannelT2;
    }

    public String getChannelT3() {
        return ChannelT3;
    }
}
