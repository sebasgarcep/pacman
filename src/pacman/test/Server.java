/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.online.Config;

/**
 *
 * @author sebastian
 */
public class Server extends Thread {
    private int port;
    public ChannelGroup channels;
    public ImageTest img;
    
    public Server() {
        this.port = Config.GAMEPORT;
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.img = new ImageTest("/pacman/recursos/biganimaciones/pacman/pacman1-1.png", 50, 50);
    }
    
    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        
        try {
            b
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel c) throws Exception {
                        c.pipeline().addLast(
                                new ByteArrayEncoder(),
                                new ObjectEncoder(), 
                                new ServerHandler(channels));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(this.port).sync();
            
            f.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        
    }
    
    public void send(Byte[] msg) {
        this.channels.writeAndFlush(msg);
    }

    public void sendImage() {
        ChannelGroupFuture f = this.channels.writeAndFlush(this.img);
        
    }
}
