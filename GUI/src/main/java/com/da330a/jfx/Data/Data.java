package com.da330a.jfx.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    public Data(){}

    public static List<String> getColors() {
        return new ArrayList<>(Arrays.asList("Blue", "Red", "Green"));
    }

    public Boolean limitToLocalNetwork = true;

    public int getPort(){ return 23232; }
    public int getEchoPort(){ return 2345; }

    public String serverIP() {
        String IP = null;
        try{
             if (limitToLocalNetwork){
//                return InetAddress.getLocalHost().getHostAddress();
                return "192.168.1.232";
            }
            URL url = URI.create("https://api.ipify.org").toURL();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            IP = reader.readLine();  // does not work with VPN
            System.out.println("Public IP Address: " + IP);
        }
        catch(UnknownHostException uhe){
            uhe.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return IP;
    }
}
