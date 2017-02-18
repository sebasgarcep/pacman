/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

/**
 *
 * @author sebastian
 */
public interface Config {
    public static final String GROUP_ADDRESS = "224.0.0.10";
    public static final int GAMEPORT = 5382;
    public static final int DISCOVERYPORT = 8182;
    public static final int TIMEOUT = 1000;
    public static final byte[] DISCOVERY_REQUEST = "DISCOVERY_REQUEST".getBytes();
    public static final byte[] DISCOVERY_RESPONSE = "DISCOVERY_RESPONSE".getBytes();
    public static final String GAME_BEGIN = "GAME_BEGIN";
    public static final String GAME_OVER = "GAME_OVER";
}
