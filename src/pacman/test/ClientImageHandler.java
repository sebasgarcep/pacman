/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * @author sebastian
 */
public class ClientImageHandler extends SimpleChannelInboundHandler<ImageTest>{
    private ImageTest parentImage;
    public ClientImageHandler(ImageTest passed) {
        this.parentImage = passed;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc, ImageTest i) throws Exception {
        this.parentImage.changeImage(i);
        ReferenceCountUtil.release(i);
    }
    
}
