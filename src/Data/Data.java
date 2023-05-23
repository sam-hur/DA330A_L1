package Data;

import Preperation.Client.EchoClient;

import java.util.ArrayList;

public class Data implements Runnable{
    public Data(){}

    public int getPort(){ return 1234; }
    public int getEchoPort(){ return 2345; }

    private static ArrayList<EchoClient> clients = new ArrayList<>();
    @Override
    public void run(){

    }
}
