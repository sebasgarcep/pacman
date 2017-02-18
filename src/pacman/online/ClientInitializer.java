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
public class ClientInitializer extends ChannelInitializer<Channel> {
    private final Client client;
    public ClientInitializer(Client client) {
        this.client = client;
    }
    
    @Override
    protected void initChannel(Channel c) throws Exception {
        this.client.connected = true;
        this.client.addChannel(c);
        c.pipeline().addLast(
                new ObjectDecoder(ClassResolvers.weakCachingResolver(ClassLoader.getSystemClassLoader())),
                new ObjectEncoder(),
                new ClientMessageHandler(this.client),
                new ClientGraphicHandler(this.client));
    }
    
}
