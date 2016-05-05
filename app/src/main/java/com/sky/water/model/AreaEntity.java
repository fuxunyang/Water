package com.sky.water.model;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/26 下午3:53
 */
public class AreaEntity implements Serializable {
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
