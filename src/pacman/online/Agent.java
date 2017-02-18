/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.channel.Channel;

/**
 *
 * @author sebastian
 */
public interface Agent {
    public boolean isConnected();
    public boolean isListening();
    public void send(Object o);
    public void start();
    public void run();
    public void end();
    public void addChannel(Channel c);
}
