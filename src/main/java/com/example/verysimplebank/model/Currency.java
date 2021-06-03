package com.example.verysimplebank.model;

public enum Currency {
    UAH, USD, EUR;

    @Override
    public String toString() {
        return name();
    }
}
