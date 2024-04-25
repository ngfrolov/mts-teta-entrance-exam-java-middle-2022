package com.example.demo.dispatcher;

import com.example.demo.struct.Command;
import com.example.demo.struct.CommandType;
import com.example.demo.struct.Result;
import com.example.demo.struct.ResultType;

public class SWPConverter {

    public static Command parseRequest(String textCommand) {
        if (textCommand == null || textCommand.trim().equals("")) {
            return null;
        }
        String[] textParts = textCommand.trim().split(" ");
        if (textParts == null || (textParts.length != 1 && textParts.length != 3)) {
            return null;
        }
        if (textParts.length == 1) {
            CommandType commandType = CommandType.findByStr(textParts[0]);
            if (commandType == null || commandType != CommandType.__DELETE_ALL) {
                return null;
            }
            return new Command(null, commandType, null);
        }
        if (textParts.length == 3) {
            String user = textParts[0];
            CommandType commandType = CommandType.findByStr(textParts[1]);
            if (commandType == null) {
                return null;
            }
            String[] args;
            if (textParts[2].startsWith("[") && textParts[2].endsWith("]")) {
                args = textParts[2]
                                .substring(1, textParts[2].length() - 1)
                                .split(", "); 
            } else {
                args = new String[] { textParts[2] };
            }

            return new Command(user, commandType, args);
        }
        return null;
    }

    public static String buildResponse(Result result) {
        return buildResponse(result.getResultType(), result.getArgs());
    }

    public static String buildResponse(ResultType resultType, String args) {
        if (args != null) {
            return resultType.toString() + " [" + args + "]";
        } else {
            return resultType.toString();
        }        
    }
}
