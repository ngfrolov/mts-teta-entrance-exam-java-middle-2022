package com.example.demo.storage;

import java.util.*;
import java.util.stream.Collectors;
import com.example.demo.struct.*;


public class TaskStorage {

    /* task title -> task object */
    Map<String, Task> tasks = new LinkedHashMap<String, Task>();

    /* user name -> (task title -> task object) */
    Map<String, Map<String, Task>> tasksCache = new LinkedHashMap<String, Map<String, Task>>();

    
    public Result processCommand(Command request) {
        if (request.getCommandType() == CommandType.__DELETE_ALL) {
            return this.deleteAll();
        } 
        if (request.getCommandType() == CommandType.LIST_TASK) {
            return this.listTask(request);
        }
        String title = request.getArgs()[0];      
        Task storedTask = tasks.get(title); 
        Map<String, Task> userTasks = tasksCache.get(request.getUser());
        if (request.getCommandType() == CommandType.CREATE_TASK) {
            if (storedTask != null) {
                return new Result(ResultType.ERROR, null);
            }
            Task newTask = new Task(request.getUser(), title);
            tasks.put(title, newTask);              
            
            if (userTasks == null) {
                userTasks = new LinkedHashMap<String, Task>();                
                tasksCache.put(request.getUser(), userTasks);
            } 
            userTasks.put(title, newTask);

            return new Result(ResultType.CREATED, null);
        } 
        if (storedTask == null) {
            return new Result(ResultType.ERROR, null);
        }
        if (!request.getUser().equals(storedTask.getUser())) {
            return new Result(ResultType.ACCESS_DENIED, null);
        }                                                     
        if (request.getCommandType() == CommandType.DELETE_TASK) {
            if (storedTask.getStatus().equals(TaskStatus.CREATED) || storedTask.getStatus().equals(TaskStatus.DELETED)) {
                return new Result(ResultType.ERROR, null);
            } else {
                storedTask.setStatus(TaskStatus.DELETED);
                tasks.remove(title);                    
                System.out.print("Before " + userTasks.size() + ", ");
                userTasks.remove(title);
                System.out.println("After " + userTasks.size());

                return new Result(ResultType.DELETED, null);
            }      
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
        return null;
    }

    private Result deleteAll() {
        tasks.clear();
        tasksCache.clear();
        return new Result(ResultType.__ALL_DELETED, null);
    }

    private Result listTask(Command request) {        
        String targetUser = request.getArgs()[0];
        //String temp = tasks.values().stream().filter(x -> x.getUser().equals(targetUser)).map(x -> x.getTitle()).collect(Collectors.joining(", "));            
        //return new Result(ResultType.TASKS, temp);

        String taskList = tasksCache.get(targetUser).values().stream().filter(x -> x.getStatus() != TaskStatus.DELETED).map(x -> x.getTitle()).collect(Collectors.joining(", "));
        return new Result(ResultType.TASKS, taskList);

    }     
}
