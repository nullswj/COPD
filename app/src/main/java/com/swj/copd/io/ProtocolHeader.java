package com.swj.copd.io;

import androidx.annotation.NonNull;

public class ProtocolHeader {

    private byte magic;         // 帧头
    private byte dataType;      // 数据类型
    private int terminalID;     // 终端id
    private int timestamp;      // 数据包发送的时间
    private int len;            // 长度

    public ProtocolHeader() {
    }

    public ProtocolHeader(byte magic, byte dataType, int terminalID, int timestamp, int len) {
        this.magic = magic;
        this.dataType = dataType;
        this.terminalID = terminalID;
        this.timestamp = timestamp;
        this.len = timestamp;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "帧头:"+magic+"\n"
                +"数据类型:"+dataType+"\n"
                +"终端id:"+terminalID+"\n"
                +"数据包发送的时间:"+timestamp+"\n"
                +"长度:"+dataType+"\n";
        return result;
    }
}