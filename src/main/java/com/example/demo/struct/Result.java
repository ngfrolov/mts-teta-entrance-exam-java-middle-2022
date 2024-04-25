package com.example.demo.struct;

public class Result {
    ResultType resultType;
    String args;

    public Result(ResultType resultType, String args) {
        this.resultType = resultType;
        this.args = args;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

}
