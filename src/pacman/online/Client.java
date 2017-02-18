/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pacman.interfaz.paneles.ContenedorClient;
import pacman.interfaz.paneles.GamePanel;
import pacman.interfaz.paneles.Menu;
import pacman.objetos.SerializableSprite;

/**
 *
 * @author sebastian
 */
public class Client extends Thread implements Agent {
    private String host;
    private int port;
    public volatile boolean searching;
    public volatile boolean connected;
    private ChannelGroup channels;
    private GamePanel owner;
    
    public Client(GamePanel owner) {
        this.owner = owner;
        init();
    }
    
    private void init() {
        this.port = Config.GAMEPORT;
        this.setName("Game Client Runnable");
        this.host = null;
        this.searching = false;
        this.connected = false;
    }
    
    private void hostLookup() {
        try {
            //SETUP MULTICAST GROUP
            MulticastSocket mc = new MulticastSocket(Config.DISCOVERYPORT);
            InetAddress group = InetAddress.getByName(Config.GROUP_ADDRESS);
            mc.joinGroup(group);
            mc.setSoTimeout(Config.TIMEOUT);
            this.searching = true;
            
            //SEND REQUEST PACKET
            DatagramSocket dgs = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(Config.DISCOVERY_REQUEST, Config.DISCOVERY_REQUEST.length, InetAddress.getByName(Config.GROUP_ADDRESS), Config.DISCOVERYPORT);
            byte[] data = new byte[Config.DISCOVERY_RESPONSE.length];
            DatagramPacket dgpacket = new DatagramPacket(data, data.length);
            do {
                dgs.send(packet);
                try {
                    //RECEIVE RESPONSE PACKET
                    data = new byte[Config.DISCOVERY_RESPONSE.length];
                    dgpacket = new DatagramPacket(data, data.length);
                    mc.receive(dgpacket);
                } catch(SocketTimeoutException stex) {}
            } while(this.searching && !Arrays.equals(data, Config.DISCOVERY_RESPONSE));
            
            this.host = dgpacket.getAddress().getHostAddress();
            this.searching = false;
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void establishConnection() {
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        if(this.host != null) {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b
                        .group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ClientInitializer(this));
                
                ChannelFuture f = b.connect(this.host, this.port).sync();
                f.channel().closeFuture().sync();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                workerGroup.shutdownGracefully();
            }
        }
    }
    
    @Override
    public void addChannel(Channel c) {
        this.channels.add(c);
    }
    
    public void gameBegin() {
        ContenedorClient gamepanel = ContenedorClient.getPanel(this);
        owner.setContexto(gamepanel);
        this.owner = gamepanel;
    }
    
    @Override
    public void run() {
        init();
        hostLookup();
        establishConnection();
    }
    
    @Override
    public boolean isConnected() {
        return this.connected;
    }
    
    @Override
    public boolean isListening() {
        return this.searching;
    }
    
    @Override
    public void end() {
        this.searching = false;
        this.channels.close();
    }
    
    @Override
    public void send(Object o) {
        this.channels.writeAndFlush(o);
    }

    public void display(Queue<SerializableSprite> i) {
        if(this.owner instanceof ContenedorClient) {
            ((ContenedorClient)this.owner).display(i);
        }
    }

    void gameOver() {
        int score1 = ((ContenedorClient)this.owner).getScore(1);
        int score2 = ((ContenedorClient)this.owner).getScore(2);
        if(score1 > score2) {
            JOptionPane.showMessageDialog(this.owner, "Fin del juego" + "\n" + "Ganador: Jugador 1" + "\n" + "Puntajes:" + "\n" + "J1:" + score1 + "\n" + "J2:" + score2);
        } else if(score1 < score2) {
            JOptionPane.showMessageDialog(this.owner, "Fin del juego" + "\n" + "Ganador: Jugador 2" + "\n" + "Puntajes:" + "\n" + "J1:" + score1 + "\n" + "J2:" + score2);
        } else {
            JOptionPane.showMessageDialog(this.owner, "Fin del juego\nTermino en empate");
        }
        this.owner.setContexto(new Menu());
    }
    
    public void setScore(String s) {
        String[] input = s.split(" ");
        int j = Integer.parseInt(input[0]);
        int score = Integer.parseInt(input[1]);
        if(this.owner instanceof ContenedorClient) ((ContenedorClient)this.owner).setScore(j, score);
    }
}
