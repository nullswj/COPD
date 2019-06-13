package com.swj.copd.io;

import java.nio.charset.StandardCharsets;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtocolDecoderDeprecation extends ByteToMessageDecoder {

    private static final int HEADER_SIZE = 14;

    private byte magic;         // 帧头
    private byte dataType;      // 数据类型
    private int terminalID;     // 终端id
    private int timestamp;      // 数据包发送的时间
    private int len;            // 长度

    public ProtocolDecoderDeprecation() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.
     * ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
        if (in.readableBytes() < HEADER_SIZE) {
            return;// response header is 10 bytes
        }

        magic = in.readByte();
        dataType = in.readByte();
        terminalID = in.readInt();
        timestamp = in.readInt();
        len = in.readInt();

        if (in.readableBytes() < len) {
            return; // until we have the entire payload return
        }

        ByteBuf buf = in.readBytes(len);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        ProtocolMsg msg = new ProtocolMsg();

//		ProtocolBody body2 = new ProtocolBody();
//		body2.setBody(body);
        ProtocolHeader protocolHeader = new ProtocolHeader(magic, dataType,
                terminalID, timestamp, len);
        //msg.setProtocolBody(body2);
        msg.setBody(body);
        msg.setProtocolHeader(protocolHeader);
        out.add(msg);

    }

}