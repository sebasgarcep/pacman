/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.interfaz.paneles.GamePanel;
import pacman.objetos.Jugador;

/**
 *
 * @author sebastian
 */
public class Server extends Thread implements Agent {
    private int port;
    public volatile boolean wait_incoming;
    public volatile boolean connected;
    private ChannelGroup channels;
    private GamePanel owner;
    private Jugador commanded;
    
    public Server(GamePanel owner) {
        this.owner = owner;
        init();
    }
    
    private void init() {
        this.port = Config.GAMEPORT;
        this.setName("Game Server Runnable");
        this.connected = false;
        this.wait_incoming = false;
        this.commanded = null;
    }
    
    public synchronized String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void startListening() {
        this.wait_incoming = true;
        Thread listener = new Thread(() -> listenConnection());
        listener.setName("Server Listener");
        listener.start();
    }
    
    private void listenConnection() {
        try {
            //SETUP MULTICAST GROUP
            MulticastSocket mc = new MulticastSocket(Config.DISCOVERYPORT);
            InetAddress group = InetAddress.getByName(Config.GROUP_ADDRESS);
            mc.joinGroup(group);
            mc.setSoTimeout(Config.TIMEOUT);

            while(this.wait_incoming) {
                //RECEIVE REQUEST PACKET
                byte[] data = new byte[Config.DISCOVERY_REQUEST.length];
                DatagramPacket dgpacket = new DatagramPacket(data, data.length);
                try {
                    mc.receive(dgpacket);
                } catch(SocketTimeoutException stex) {
                    continue;
                }

                //SEND RESPONSE PACKET
                if(Arrays.equals(data, Config.DISCOVERY_REQUEST)) {
                    DatagramSocket dgs = new DatagramSocket();
                    DatagramPacket packet = new DatagramPacket(Config.DISCOVERY_RESPONSE, Config.DISCOVERY_RESPONSE.length, InetAddress.getByName(Config.GROUP_ADDRESS), Config.DISCOVERYPORT);
                    dgs.send(packet);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void establishConnection() {
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer(this))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            ChannelFuture f = b.bind(getHost(), this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
    
    @Override
    public void addChannel(Channel c) {
        this.channels.add(c);
    }
    
    @Override
    public void run(){
        init();
        startListening();
        establishConnection();
    }
    
    @Override
    public void end() {
        this.wait_incoming = false;
        this.channels.close();
    }
    
    @Override
    public synchronized boolean isConnected() {
        return this.connected;
    }
    
    @Override
    public synchronized boolean isListening() {
        return this.wait_incoming;
    }
    
    @Override
    public void send(Object o) {
        this.channels.writeAndFlush(o);
    }

    void feedKey(KeyPacket i) {
        if(this.commanded != null) {
            if(i.getOrientacion() != null) {
                this.commanded.pressKey(i.getOrientacion());
            } else {
                this.commanded.releaseKey();
            }
        }
    }

    public void commandPlayer(Jugador j) {
        this.commanded = j;
    }
}
