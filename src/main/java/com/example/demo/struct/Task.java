package com.example.demo.struct;

public class Task {
    String user;
    String title;
    TaskStatus status;
    
    public Task(String user, String title) {
        this.user = user;
        this.title = title;
        this.status = TaskStatus.CREATED;
    }    

    public String getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}