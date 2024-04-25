package com.example.demo.struct;

public class Command {
    String user;
    CommandType commandType;
    String[] args;

    public Command(String user, CommandType command, String[] args) {
        this.user = user;
        this.commandType = command;
        this.args = args;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public CommandType getCommandType() {
        return commandType;
    }
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
    public String[] getArgs() {
        return args;
    }
    public void setArgs(String[] args) {
        this.args = args;
    }    
}
