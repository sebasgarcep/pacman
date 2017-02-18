/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author sebastian
 */
public class ServerKeyHandler extends SimpleChannelInboundHandler<KeyPacket> {
    private Server server;
    public ServerKeyHandler(Server server) {
        this.server = server;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc, KeyPacket i) throws Exception {
        this.server.feedKey(i);
    }
    
}
