/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * @author sebastian
 */
public class ServerInitializer extends ChannelInitializer<Channel> {
    private Server server;
    public ServerInitializer(Server server) {
        this.server = server;
    }
    
    @Override
    protected void initChannel(Channel c) throws Exception {
        this.server.connected = true;
        this.server.wait_incoming = false;
        this.server.addChannel(c);
        c.pipeline().addLast(
                new ObjectDecoder(ClassResolvers.weakCachingResolver(ClassLoader.getSystemClassLoader())),
                new ObjectEncoder(),
                new ServerKeyHandler(server));
    }
    
}
