package com.nio.test;



import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // �ͻ��˽��յ�����httpResponse��Ӧ������Ҫʹ��HttpResponseDecoder���н���
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // �ͻ��˷��͵���httprequest������Ҫʹ��HttpRequestEncoder���б���
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler()); 
                }
            });

            	 ChannelFuture f = b.connect(host, port).sync();

               URI uri = new URI("http://gc.ditu.aliyun.com:80/geocoding?a=������");
                 String msg = "Are you ok?";
                 DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                         uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));                             
                 // ����http����
                 request.headers().set(HttpHeaders.Names.HOST, host);
                 request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                 request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
                 // ����http����
                 f.channel().write(request);
                 f.channel().flush();
                 f.channel().closeFuture().sync();
                
//            }
           
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
    
    public void connect_post(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // �ͻ��˽��յ�����httpResponse��Ӧ������Ҫʹ��HttpResponseDecoder���н���
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // �ͻ��˷��͵���httprequest������Ҫʹ��HttpRequestEncoder���б���
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler()); 
                }
            });

            	 ChannelFuture f = b.connect(host, port).sync();

               URI uri = new URI("http://gc.ditu.aliyun.com:80/geocoding?a=������");
               FullHttpRequest request = new DefaultFullHttpRequest(
                       HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath());                        
                 // ����http����
               request.headers().set(HttpHeaders.Names.HOST, host);
               request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE); // or HttpHeaders.Values.CLOSE
               request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
               request.headers().add(HttpHeaders.Names.CONTENT_TYPE, "application/json");
               ByteBuf bbuf = Unpooled.copiedBuffer("{\"jsonrpc\":\"2.0\",\"method\":\"calc.add\",\"params\":[1,2],\"id\":1}", StandardCharsets.UTF_8);
               request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, bbuf.readableBytes());
                 // ����http����
                 f.channel().write(request);
                 f.channel().flush();
                 f.channel().closeFuture().sync();
                
//            }
           
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
            //�������޸ĳɷ���˵�IP
        for(int k=0; k<1;k++){
          client.connect("http://gc.ditu.aliyun.com/", 80);

            System.out.println(k);
            }
    }
}