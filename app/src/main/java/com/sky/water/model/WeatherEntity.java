package com.sky.water.model;

import com.sky.water.api.JsonToResponse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/22 上午11:34
 */
@HttpResponse(parser = JsonToResponse.class)
public class WeatherEntity implements Serializable {

    private String status;
    private int error;
    private String date;

    private List<ResultsEntity> results;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public int getError() {
        return error;
    }

    public String getDate() {
        return date;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

}
