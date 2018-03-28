package com.aacdemo.base;

import java.util.List;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class BaseResult<T> {
    private int status_code;
    private String status;
    private List<T> results;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
