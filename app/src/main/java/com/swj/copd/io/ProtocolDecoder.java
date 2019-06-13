package com.swj.copd.io;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {
    private static final int HEADER_SIZE = 14;

    private byte magic;         // 帧头
    private byte dataType;      // 数据类型
    private int terminalID;     // 终端id
    private int timestamp;      // 数据包发送的时间
    private int len;            // 长度

    /**
     * @param maxFrameLength
     * @param lengthFieldOffset
     * @param lengthFieldLength
     * @param lengthAdjustment
     * @param initialBytesToStrip
     */
    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset,
                           int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip);
    }

    /**
     * @param maxFrameLength
     * @param lengthFieldOffset
     * @param lengthFieldLength
     * @param lengthAdjustment
     * @param initialBytesToStrip
     * @param failFast
     */
    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset,
                           int lengthFieldLength, int lengthAdjustment,
                           int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
        // TODO Auto-generated constructor stub
    }


    @Override
    protected ProtocolMsg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < HEADER_SIZE) {
            return null;// response header is 14 bytes
        }

        magic = in.readByte();
        dataType = in.readByte();
        terminalID = in.readInt();
        timestamp = in.readInt();
        len = in.readInt();

        if (in.readableBytes() < len) {
            return null; // until we have the entire payload return
        }

        ByteBuf buf = in.readBytes(len);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        ProtocolMsg msg = new ProtocolMsg();
        ProtocolHeader protocolHeader = new ProtocolHeader(magic, dataType, terminalID, timestamp, len);
        msg.setBody(body);
        msg.setProtocolHeader(protocolHeader);
        return msg;
    }
}