package com.sky.water.model;

import java.io.Serializable;

/**
 * Created by 机械革命 on 2016/8/14.
 */

public class User implements Serializable {
    private String ID;
    private String TrueName;
    private String UserID;
    private String UserName;
    private String PHNo;
    private String ZhenID;
    private String ZhenName;
    private String ParentId;
    private String Code;
    private String Name;
    private String AreaLevel;
    private String Status;
    private String ParentName;
    /**
     * ID : 7210648
     * Password : S2SuUsBIVqXIgRe5LJlXkg==
     * IdentityCard : 123456789123456789
     * IPhone : 15900292608
     * IsAuditing : true
     * AreaId : 2
     * RegisterTime : 2016-03-23T14:40:16.26
     * UserRole : 1
     * AreaName : 邦均镇
     * ZhenID : 2
     * ParentId : 1
     * Status : 1
     */

    private String Password;
    private String IdentityCard;
    private String IPhone;
    private boolean IsAuditing;
    private int AreaId;
    private String RegisterTime;
    private int UserRole;
    private String AreaName;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public String getPHNo() {
        return PHNo;
    }

    public void setPHNo(String PHNo) {
        this.PHNo = PHNo;
    }

    public String getZhenID() {
        return ZhenID;
    }

    public void setZhenID(String ZhenID) {
        this.ZhenID = ZhenID;
    }

    public String getZhenName() {
        return ZhenName;
    }

    public void setZhenName(String ZhenName) {
        this.ZhenName = ZhenName;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAreaLevel() {
        return AreaLevel;
    }

    public void setAreaLevel(String AreaLevel) {
        this.AreaLevel = AreaLevel;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

    public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }

    public String getIPhone() {
        return IPhone;
    }

    public void setIPhone(String IPhone) {
        this.IPhone = IPhone;
    }

    public boolean isIsAuditing() {
        return IsAuditing;
    }

    public void setIsAuditing(boolean IsAuditing) {
        this.IsAuditing = IsAuditing;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int AreaId) {
        this.AreaId = AreaId;
    }

    public String getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(String RegisterTime) {
        this.RegisterTime = RegisterTime;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int UserRole) {
        this.UserRole = UserRole;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

}
