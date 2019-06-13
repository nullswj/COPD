package com.swj.copd.io;


import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CopdClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final String TAG = "CopdClientHandler";
    private ProtocolMsg msg = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Server->Client:"+incoming.remoteAddress()+obj.toString());

        Log.e(TAG, "进入handle");
        if(obj instanceof ProtocolMsg) {
            msg = (ProtocolMsg)obj;
            //System.out.println("Server->Client:"+incoming.remoteAddress()+msg.getBody());
            //Intent broadcast = new Intent();
            Log.e(TAG, msg.toString());
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public ProtocolMsg getMessage()
    {
        if(msg != null)
        {
            ProtocolMsg message = msg;
            msg = null;
            return message;
        }
        else
            return null;
    }

}

