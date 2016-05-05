package com.sky.water.model;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/20 上午11:50
 */
public class NewsEntity implements Serializable {

    private String newstt;
    private String newContent;
    private int R;
    private String newsTitle;
    private String newsImage;
    private String newsTypeName;
    private String newsDate;
    private String newsType;
    private String guid;
    private String newsPerson;

    public void setNewstt(String newstt) {
        this.newstt = newstt;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public void setR(int R) {
        this.R = R;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setNewsPerson(String newsPerson) {
        this.newsPerson = newsPerson;
    }

    public String getNewstt() {
        return newstt;
    }

    public String getNewContent() {
        return newContent;
    }

    public int getR() {
        return R;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public String getNewsDate() {
        return newsDate==null?newsDate:newsDate.replace("T"," ");
    }

    public String getNewsType() {
        return newsType;
    }

    public String getGuid() {
        return guid;
    }

    public String getNewsPerson() {
        return newsPerson;
    }
}
