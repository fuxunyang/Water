package com.sky.water.model;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/26 下午3:53
 */
public class AreaEntity implements Serializable {
//    "ZhenID": 2,
//            "ZhenName": "邦均镇",
//            "ID": 28,
//            "ParentId": 2,
//            "Code": "JXC001",
//            "Name": "西草场村",
//            "AreaLevel": "村",
//            "Status": 1,
//            "ParentName": "邦均镇"

//    "ZhenID": 2,
//            "ZhenName": "邦均镇",
//            "ID": 2,
//            "ParentId": 1,
//            "Code": "JXZ001",
//            "Name": "邦均镇",
//            "AreaLevel": "乡镇",
//            "Status": 1,
//            "ParentName": "蓟县"
    private int ID;
    private int ParentId;
    private String Code;
    private String Name;
    private String AreaLevel;
    private int Status;
    private String ParentName;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setAreaLevel(String AreaLevel) {
        this.AreaLevel = AreaLevel;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }

    public int getID() {
        return ID;
    }

    public int getParentId() {
        return ParentId;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }

    public String getAreaLevel() {
        return AreaLevel;
    }

    public int getStatus() {
        return Status;
    }

    public String getParentName() {
        return ParentName;
    }
}
