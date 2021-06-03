package com.example.verysimplebank.dto.result;

public class BadRequestResult extends ResultDTO{
    public BadRequestResult() {
        super("JSON deserialization failed");
    }
}
