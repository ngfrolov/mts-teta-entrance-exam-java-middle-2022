package com.example.demo.struct;

public class Task {
    String user;
    String name;
    TaskStatus status;
    
    public Task(String user, String name) {
        this.user = user;
        this.name = name;
        this.status = TaskStatus.CREATED;
    }    

    public String getUser() {
        return this.user;
    }

    public String getName() {
        return this.name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}