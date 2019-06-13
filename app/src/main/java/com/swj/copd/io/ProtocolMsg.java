package com.swj.copd.io;

import androidx.annotation.NonNull;

public class ProtocolMsg {
    private ProtocolHeader protocolHeader = new ProtocolHeader();
    private String body;

    public ProtocolMsg() {
        // TODO Auto-generated constructor stub
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ProtocolHeader getProtocolHeader() {
        return protocolHeader;
    }

    public void setProtocolHeader(ProtocolHeader protocolHeader) {
        this.protocolHeader = protocolHeader;
    }

    @NonNull
    @Override
    public String toString() {
        String result = protocolHeader.toString()
                + "数据："+body;
        return result;
    }
}
