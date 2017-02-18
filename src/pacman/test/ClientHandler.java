/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import java.util.Arrays;

/**
 *
 * @author sebastian
 */
public class ClientHandler extends SimpleChannelInboundHandler<Byte[]> {

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Byte[] i) throws Exception {
        System.out.println(Arrays.toString(i));
    }
}
