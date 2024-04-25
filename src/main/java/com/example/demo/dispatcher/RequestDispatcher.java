package com.example.demo.dispatcher;

import com.example.demo.storage.*;
import com.example.demo.struct.*;

public class RequestDispatcher {
    TaskStorage storage = new TaskStorage();

    public String handle(String request) {
        Command command = SWPConverter.parseRequest(request);
        if (command == null) {
            return SWPConverter.buildResponse(ResultType.WRONG_FORMAT, null); 
        }  
        Result result = storage.processCommand(command);             
        return SWPConverter.buildResponse(result);
    }
}
