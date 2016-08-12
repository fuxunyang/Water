package com.sky.water.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/22 上午11:34
 */
public class WaterEntity implements Serializable {

    private String Name;
    private String Code;
    private int ParentId;
    //    private int tbMachineWellsCommunicationRecordID;
//    private int LayoutType;
//    private String PulseCoeffcient;
//    private String WaterIdentification;
//    private String WaterValue;
//    private String WellTotalElectricityThreshold;
//    private int RemoveFlag;
//    private String WaterFlag;
//    private String CardNumber;
//    private String ParentName;
//    private int R;
    private String WellTotalWaterThreshold;
    //    private Object Coordinate;
//    private String YearTotalWaterIntakeThreshold;
//    private String PumpUseTime;
//    private String WaterDepth;
    private String WellTotalWater;
    //    private int Status;
    private String CollectDate;
    private int AreaID;
    //    private String YearTotalWaterIntake;
    private String ID;
    private String WellTotalElectricity;
    //    private String InstantaneousFlowRate;
//    private String AreaLevel;
    private String MachineWellsNum;
    //    private String InstantaneousFlowRateThreshold;
    private String Balance;
    private String WellNumber;
    //    private String GprsCode;
//    private String RecordDate;
    private String DeviceID;
//    private String PackageNumber;
//    private Object PumpType;
//    private Object Remark;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getWellTotalWater() {
        return WellTotalWater;
    }

    public void setWellTotalWater(String wellTotalWater) {
        WellTotalWater = wellTotalWater;
    }

    public String getCollectDate() {
        return TextUtils.isEmpty(CollectDate) ? CollectDate : CollectDate.replace("T", " ");
    }

    public void setCollectDate(String collectDate) {
        CollectDate = collectDate;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWellTotalElectricity() {
        return WellTotalElectricity;
    }

    public void setWellTotalElectricity(String wellTotalElectricity) {
        WellTotalElectricity = wellTotalElectricity;
    }

    public String getMachineWellsNum() {
        return MachineWellsNum;
    }

    public void setMachineWellsNum(String machineWellsNum) {
        MachineWellsNum = machineWellsNum;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getWellNumber() {
        return WellNumber;
    }

    public void setWellNumber(String wellNumber) {
        WellNumber = wellNumber;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getWellTotalWaterThreshold() {
        return WellTotalWaterThreshold==null?"-1":WellTotalWaterThreshold;
    }

    public void setWellTotalWaterThreshold(String wellTotalWaterThreshold) {
        WellTotalWaterThreshold = wellTotalWaterThreshold;
    }
}
