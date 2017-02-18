/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;

/**
 *
 * @author sebastian
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ChannelGroup channelGroup;
    public ServerHandler(ChannelGroup chg) {
        super();
        this.channelGroup = chg;
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channelGroup.add(ctx.channel());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
