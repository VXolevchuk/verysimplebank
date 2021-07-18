package com.example.verysimplebank.dto.result;

public class UnsuitableAccountNumberResult extends ResultDTO{
    public UnsuitableAccountNumberResult() {
        super("Account with this number already exist!");
    }
}
