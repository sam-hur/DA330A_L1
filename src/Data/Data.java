package Data;

import Preperation.Client.EchoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class Data {
    public Data(){}

    public Boolean limitToLocalNetwork = true;

    public int getPort(){ return 3342; }
    public int getEchoPort(){ return 2345; }

    public String serverIP() {
        String IP = null;
        try{
             if (limitToLocalNetwork){
//                return InetAddress.getLocalHost().getHostAddress();
                return "192.168.1.99";
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
