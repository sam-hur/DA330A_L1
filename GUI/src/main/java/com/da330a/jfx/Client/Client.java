package com.da330a.jfx.Client;


import com.da330a.jfx.App;
import com.da330a.jfx.Data.Data;
import com.da330a.jfx.Data.EndOfWrite;
import javafx.application.Application;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public List<ClientHandler> connectedClients;
    public static void main(String[] args) throws InterruptedException {
        Thread appThread = new Thread(() -> Application.launch(App.class, args));
        appThread.start();

        Client client = new Client();
        client.listen();  // listens on sep thread for incoming messages
        client.sendMessage();  // sends newlines sent via the command prompt
    }

    /**
     * Client constructor
     */
    public Client(){
        try{
            socket = new Socket(data.serverIP(), data.getPort());
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // output as object stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connectedClients = ClientHandler.connectedClients;
        } catch (IOException ioe){
            System.err.println("\nUnable to connect; is the server running?");
            System.err.print("\nInfo:\t");
            System.out.print("IP: "+ data.serverIP() + "    |    Port: " + data.getPort());
            System.exit(-1);
        }
    }

    private final Data data = new Data();
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String
            filename = "time.ser",
            userInput;

    public void sendMessage() {
        try {
            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                String msg = sc.nextLine();
                out.write(msg);
                out.newLine();
                out.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }


    public void listen(){
        new Thread(() -> {
            System.out.println("You have joined the server chat!");
            while(socket.isConnected()){
                try {
                    String inputLine = in.readLine();
                    if (inputLine == null){
                        throw new EndOfWrite();
                    }
                    System.out.println(inputLine);
                }
                catch (EndOfWrite eow){
                    System.out.println("\u2713 Connection to server successfully terminated");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                catch (IOException e) {
                    System.err.println("Error in listening to the server (connection may have reset)");
                    System.exit(-1);
                }
            }
        }).start();
    }
}
