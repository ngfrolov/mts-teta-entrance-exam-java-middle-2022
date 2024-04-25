package com.example.demo.struct;

public enum CommandType {
    CLOSE_TASK,
    DELETE_TASK,
    REOPEN_TASK,
    CREATE_TASK,
    LIST_TASK,
    __DELETE_ALL;

    public static CommandType findByStr(String command) {
        for (CommandType ct : CommandType.values()) {
            if (command.equals(ct.toString())) {
                return ct;
            }
        }
        return null;
    }
}
