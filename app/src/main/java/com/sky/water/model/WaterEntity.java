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
//    private String PARENTNAME;
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
    private String WaterFlag;
    private String WaterValue;
    private String InstantaneousFlowRate;
    private Object InstantaneousFlowRateThreshold;
    private String CardNumber;
    private String YearTotalWaterIntake;
    private Object YearTotalWaterIntakeThreshold;
    private Object WellTotalElectricityThreshold;
    private String PumpUseTime;
    private String RecordDate;
    private String PackageNumber;
    private String BeginTime;
    private String EndTime;
    private String OneTotalWater;
    private String OneTotalElectricity;
    private String tbMachineWellsCommunicationID;
    private String GprsCode;
    private String PulseCoeffcient;
    private String PumpType;
    private String WaterDepth;
    private String WaterIdentification;
    private String Coordinate;
    private int LayoutType;
    private String AreaLevel;
    private int Status;
    private String ParentName;
    private String Remark;
    private int RemoveFlag;
    private String MachineName;


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


    public String getWaterFlag() {
        return WaterFlag;
    }

    public void setWaterFlag(String WaterFlag) {
        this.WaterFlag = WaterFlag;
    }

    public String getWaterValue() {
        return WaterValue;
    }

    public void setWaterValue(String WaterValue) {
        this.WaterValue = WaterValue;
    }

    public String getInstantaneousFlowRate() {
        return InstantaneousFlowRate;
    }

    public void setInstantaneousFlowRate(String InstantaneousFlowRate) {
        this.InstantaneousFlowRate = InstantaneousFlowRate;
    }

    public Object getInstantaneousFlowRateThreshold() {
        return InstantaneousFlowRateThreshold;
    }

    public void setInstantaneousFlowRateThreshold(Object InstantaneousFlowRateThreshold) {
        this.InstantaneousFlowRateThreshold = InstantaneousFlowRateThreshold;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public String getYearTotalWaterIntake() {
        return YearTotalWaterIntake;
    }

    public void setYearTotalWaterIntake(String YearTotalWaterIntake) {
        this.YearTotalWaterIntake = YearTotalWaterIntake;
    }

    public Object getYearTotalWaterIntakeThreshold() {
        return YearTotalWaterIntakeThreshold;
    }

    public void setYearTotalWaterIntakeThreshold(Object YearTotalWaterIntakeThreshold) {
        this.YearTotalWaterIntakeThreshold = YearTotalWaterIntakeThreshold;
    }

    public Object getWellTotalElectricityThreshold() {
        return WellTotalElectricityThreshold;
    }

    public void setWellTotalElectricityThreshold(Object WellTotalElectricityThreshold) {
        this.WellTotalElectricityThreshold = WellTotalElectricityThreshold;
    }

    public String getPumpUseTime() {
        return PumpUseTime;
    }

    public void setPumpUseTime(String PumpUseTime) {
        this.PumpUseTime = PumpUseTime;
    }

    public String getRecordDate() {
        return RecordDate;
    }

    public void setRecordDate(String RecordDate) {
        this.RecordDate = RecordDate;
    }

    public String getPackageNumber() {
        return PackageNumber;
    }

    public void setPackageNumber(String PackageNumber) {
        this.PackageNumber = PackageNumber;
    }

    public String getBeginTime() {
        return TextUtils.isEmpty(BeginTime) ? BeginTime : BeginTime.replace("T", " ");
    }

    public void setBeginTime(String BeginTime) {
        this.BeginTime = BeginTime;
    }

    public String getEndTime() {
        return TextUtils.isEmpty(EndTime) ? EndTime : EndTime.replace("T", " ");
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getOneTotalWater() {
        return OneTotalWater;
    }

    public void setOneTotalWater(String OneTotalWater) {
        this.OneTotalWater = OneTotalWater;
    }

    public String getOneTotalElectricity() {
        return OneTotalElectricity;
    }

    public void setOneTotalElectricity(String OneTotalElectricity) {
        this.OneTotalElectricity = OneTotalElectricity;
    }

    public String getTbMachineWellsCommunicationID() {
        return tbMachineWellsCommunicationID;
    }

    public void setTbMachineWellsCommunicationID(String tbMachineWellsCommunicationID) {
        this.tbMachineWellsCommunicationID = tbMachineWellsCommunicationID;
    }

    public String getGprsCode() {
        return GprsCode;
    }

    public void setGprsCode(String GprsCode) {
        this.GprsCode = GprsCode;
    }

    public String getPulseCoeffcient() {
        return PulseCoeffcient;
    }

    public void setPulseCoeffcient(String PulseCoeffcient) {
        this.PulseCoeffcient = PulseCoeffcient;
    }

    public String getPumpType() {
        return PumpType;
    }

    public void setPumpType(String PumpType) {
        this.PumpType = PumpType;
    }

    public String getWaterDepth() {
        return WaterDepth;
    }

    public void setWaterDepth(String WaterDepth) {
        this.WaterDepth = WaterDepth;
    }

    public String getWaterIdentification() {
        return WaterIdentification;
    }

    public void setWaterIdentification(String WaterIdentification) {
        this.WaterIdentification = WaterIdentification;
    }

    public String getCoordinate() {
        return Coordinate;
    }

    public void setCoordinate(String Coordinate) {
        this.Coordinate = Coordinate;
    }

    public int getLayoutType() {
        return LayoutType;
    }

    public void setLayoutType(int LayoutType) {
        this.LayoutType = LayoutType;
    }

    public String getAreaLevel() {
        return AreaLevel;
    }

    public void setAreaLevel(String AreaLevel) {
        this.AreaLevel = AreaLevel;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getRemoveFlag() {
        return RemoveFlag;
    }

    public void setRemoveFlag(int RemoveFlag) {
        this.RemoveFlag = RemoveFlag;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String MachineName) {
        this.MachineName = MachineName;
    }
}
