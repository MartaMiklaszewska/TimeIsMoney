package com.example.timeismoney;

import com.google.firebase.Timestamp;

public class Work {
    private String text;
    private int minutes;
    private boolean completed;
    private Timestamp createdDate;
    private String userId;

    public Work() {
    }
    public Work(String text, int minutes, boolean completed, Timestamp createdDate, String userId) {
        this.text = text;
        this.minutes = minutes;
        this.completed = completed;
        this.createdDate = createdDate;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Work{" +
                "text='" + text + '\'' +
                ", minutes=" + minutes +
                ", completed=" + completed +
                ", createdDate=" + createdDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}
