package com.sky.water.model;

import java.io.Serializable;

/**
 * Created by 机械革命 on 2016/8/10.
 */

public class Card implements Serializable {


    /**
     * TrueName : 哦哦
     * UserID : 120199901011818
     * UserName : zs
     * Pwd : zs
     * AreaID : 1
     * PHNo : 13802278893
     * RemoveFlag : 0
     * NoAreaID : 460
     * MachineName : 村委会
     * MachineNo : 255
     * MachineAuit : 0.00
     * AppUsersID : 1
     * MachineWellsCommunicationNoID : 255
     * ID : 5
     */

    private String TrueName;
    private String UserID;
    private String UserName;
    private String Pwd;
    private int AreaID;
    private String PHNo;
    private int RemoveFlag;
    private int NoAreaID;
    private String MachineName;
    private String MachineNo;
    private String MachineAuit;
    private int AppUsersID;
    private int MachineWellsCommunicationNoID;
    private int ID;

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String TrueName) {
        this.TrueName = TrueName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int AreaID) {
        this.AreaID = AreaID;
    }

    public String getPHNo() {
        return PHNo;
    }

    public void setPHNo(String PHNo) {
        this.PHNo = PHNo;
    }

    public int getRemoveFlag() {
        return RemoveFlag;
    }

    public void setRemoveFlag(int RemoveFlag) {
        this.RemoveFlag = RemoveFlag;
    }

    public int getNoAreaID() {
        return NoAreaID;
    }

    public void setNoAreaID(int NoAreaID) {
        this.NoAreaID = NoAreaID;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String MachineName) {
        this.MachineName = MachineName;
    }

    public String getMachineNo() {
        return MachineNo;
    }

    public void setMachineNo(String MachineNo) {
        this.MachineNo = MachineNo;
    }

    public String getMachineAuit() {
        return MachineAuit;
    }

    public void setMachineAuit(String MachineAuit) {
        this.MachineAuit = MachineAuit;
    }

    public int getAppUsersID() {
        return AppUsersID;
    }

    public void setAppUsersID(int AppUsersID) {
        this.AppUsersID = AppUsersID;
    }

    public int getMachineWellsCommunicationNoID() {
        return MachineWellsCommunicationNoID;
    }

    public void setMachineWellsCommunicationNoID(int MachineWellsCommunicationNoID) {
        this.MachineWellsCommunicationNoID = MachineWellsCommunicationNoID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
