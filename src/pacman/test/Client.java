/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.online.Config;

/**
 *
 * @author sebastian
 */
public class Client extends Thread {
    private String host;
    private int port;
    public ImageTest img;
    
    public Client() {
        this.host = "localhost";
        this.port = Config.GAMEPORT;
        this.img = new ImageTest("/pacman/recursos/biganimaciones/pacman/pacman1-1.png", 50, 50);
    }
    
    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel c) throws Exception {
                        c.pipeline().addLast(
                                new ByteArrayDecoder(),
                                new ObjectDecoder(ClassResolvers.weakCachingResolver(ClassLoader.getSystemClassLoader())), 
                                new ClientImageHandler(img),
                                new ClientHandler());
                    }
                });
            
            ChannelFuture f = b.connect(this.host, this.port).sync();
            
            f.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
