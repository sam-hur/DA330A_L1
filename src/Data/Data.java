package Data;

import Preperation.Client.EchoClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Data implements Runnable{
    public Data(){}

    public int getPort(){ return 5555; }
    public int getEchoPort(){ return 2345; }

    public String serverIP() {
        String IP = null;
        try{
            IP =  InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException uhe){
            uhe.printStackTrace();
        }
        return IP;
    }

    private static ArrayList<EchoClient> clients = new ArrayList<>();
    @Override
    public void run(){

    }
}
