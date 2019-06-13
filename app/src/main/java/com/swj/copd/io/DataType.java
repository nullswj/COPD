package com.swj.copd.io;

public enum DataType {
    EMGW_LOGIN_REQ((byte) 0x00),
    EMGW_LOGIN_RES((byte) 0x01);

    private byte value;

    DataType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
