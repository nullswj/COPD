package com.swj.copd.io;

import android.util.Log;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.swj.copd.util.StringUtil.repeat;

public class CopdClient {

    private static final String TAG = "CopdClient";

    private static final int MAX_FRAME_LENGTH = 100*1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_OFFSET = 10;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;
    private String host;
    private CopdClientHandler handler;
    private int port;
    private int type;

    public CopdClient(String host, int port,int type) {
        this.host = host;
        this.port = port;
        this.type = type;
        this.handler = new CopdClientHandler();
    }

    public CopdClientHandler getHandler()
    {
        return this.handler;
    }

//    /**
//     * @param args
//     * @throws InterruptedException
//     */
//    public static void main(String[] args) throws InterruptedException {
//        new CopdClient("localhost", 8082).run();
//    }

    public void run() throws InterruptedException {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder",
                            new ProtocolDecoder(MAX_FRAME_LENGTH,
                                    LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                                    LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
                    ch.pipeline().addLast("encoder", new ProtocolEncoder());
                    ch.pipeline().addLast(handler);
                }
            });

            // 启动客户端
            ChannelFuture f = b.connect(host, port).sync();
            //int count = 0;


            //while (true) {

            byte tb;
            // 发送消息给服务器
            ProtocolMsg msg = new ProtocolMsg();
            ProtocolHeader protocolHeader = new ProtocolHeader();

            protocolHeader.setMagic((byte) 0x01);
            if(type == 0)   tb = (byte) 0x01;
            else if(type == 1)  tb = (byte)0x02;
            else if (type == 2) tb = (byte)0x03;
            else
            {
                tb = (byte)0x04;
                protocolHeader.setMagic((byte) 0x02);
            }
            protocolHeader.setDataType(tb);
            protocolHeader.setTerminalID(11);
            protocolHeader.setTimestamp(Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            StringBuilder body = new StringBuilder(com.swj.copd.fragment.SRFragment.message);
            StringBuilder sb = new StringBuilder();
            sb.append(body);

            byte[] bodyBytes = sb.toString().getBytes(Charset.forName("utf-8"));
            int bodySize = bodyBytes.length;
            protocolHeader.setLen(bodySize);

            msg.setProtocolHeader(protocolHeader);
            msg.setBody(body.toString());

            f.channel().writeAndFlush(msg);
            Log.e(TAG, body.toString());
            //Thread.sleep(2000);
            //}
            // 等待连接关闭
            // f.channel().closeFuture().sync();
    }

}