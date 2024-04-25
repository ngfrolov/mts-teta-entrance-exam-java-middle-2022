package com.example.demo.storage;

import java.util.*;
import java.util.stream.Collectors;
import com.example.demo.struct.*;


public class TaskStorage {
    Map<String, Task> tasks = new LinkedHashMap<String, Task>();   

    public Result processCommand(Command request) {

        if (request.getCommandType() == CommandType.__DELETE_ALL) {
            tasks.clear();
            return new Result(ResultType.__ALL_DELETED, null);
        } 

        if (request.getCommandType() == CommandType.CREATE_TASK || 
            request.getCommandType() == CommandType.CLOSE_TASK || 
            request.getCommandType() == CommandType.REOPEN_TASK || 
            request.getCommandType() == CommandType.DELETE_TASK) {

            String name = request.getArgs()[0];      
            Task storedTask = tasks.get(name); 

            if (storedTask == null && !(request.getCommandType() == CommandType.CREATE_TASK)) {
                return new Result(ResultType.ERROR, null);
            }

            if (request.getCommandType() == CommandType.CREATE_TASK) {
                if (tasks.get(name) != null) {
                    return new Result(ResultType.ERROR, null);
                }
                Task newTask = new Task(request.getUser(), name);
                tasks.put(name, newTask);
                return new Result(ResultType.CREATED, null);
            }

            if (!request.getUser().equals(storedTask.getUser())) {
                return new Result(ResultType.ACCESS_DENIED, null);
            }      
                    
            if (request.getCommandType() == CommandType.CLOSE_TASK) {
                if (storedTask.getStatus().equals(TaskStatus.CLOSED) || storedTask.getStatus().equals(TaskStatus.DELETED)) {
                    return new Result(ResultType.ERROR, null);
                } else {
                    storedTask.setStatus(TaskStatus.CLOSED);
                    return new Result(ResultType.CLOSED, null);
                }
            }

            if (request.getCommandType() == CommandType.REOPEN_TASK) {
                if (storedTask.getStatus().equals(TaskStatus.CREATED) || storedTask.getStatus().equals(TaskStatus.DELETED)) {
                    return new Result(ResultType.ERROR, null);
                } else {
                    storedTask.setStatus(TaskStatus.CREATED);
                    return new Result(ResultType.REOPENED, null);
                }
            }

            if (request.getCommandType() == CommandType.DELETE_TASK) {
                if (storedTask.getStatus().equals(TaskStatus.CREATED) || storedTask.getStatus().equals(TaskStatus.DELETED)) {
                    return new Result(ResultType.ERROR, null);
                } else {
                    storedTask.setStatus(TaskStatus.DELETED);
                    tasks.remove(name);
                    return new Result(ResultType.DELETED, null);
                }      
            }
        }

        if (request.getCommandType() == CommandType.LIST_TASK) {
            if (request.getUser() == null || request.getArgs() == null) {
                return new Result(ResultType.WRONG_FORMAT, null);
            }
            String targetUser = request.getArgs()[0];
            String temp = tasks.values().stream().filter(x -> x.getUser().equals(targetUser)).map(x -> x.getName()).collect(Collectors.joining(", "));            
            return new Result(ResultType.TASKS, temp);
        }

        return null;
    }

     
}
