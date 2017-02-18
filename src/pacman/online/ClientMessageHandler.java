/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pacman.interfaz.paneles.AbstractContenedor;
import pacman.interfaz.paneles.ContenedorClient;

/**
 *
 * @author sebastian
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler<String> {
    private Client client;
    public ClientMessageHandler(Client client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String i) throws Exception {
        if(i.equals(Config.GAME_BEGIN)) {
            this.client.gameBegin();
        } else if(i.equals(Config.GAME_OVER)) {
            this.client.gameOver();
        } else {
            this.client.setScore(i);
        }
    }
    
}
