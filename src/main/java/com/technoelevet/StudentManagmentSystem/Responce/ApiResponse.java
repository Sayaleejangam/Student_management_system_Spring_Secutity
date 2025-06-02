package com.technoelevet.StudentManagmentSystem.Responce;

import java.util.List;

public class ApiResponse<T> {
    private boolean error;
    private String message;
    private int count;
    private List<T> data;

    public ApiResponse() {}

    public ApiResponse(boolean error, String message, int count, List<T> data) {
        this.error = error;
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
