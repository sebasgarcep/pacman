/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Queue;
import pacman.objetos.SerializableSprite;

/**
 *
 * @author sebastian
 */
public class ClientGraphicHandler extends SimpleChannelInboundHandler<Queue<SerializableSprite>>{
    private final Client client;
    public ClientGraphicHandler(Client client) {
        this.client = client;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Queue<SerializableSprite> i) throws Exception {
        this.client.display(i);
    }
    
}
