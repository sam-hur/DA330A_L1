package Preperation.MultiClientServer;

import java.io.*;
import java.net.*;
import Data.Data;

/**
 * The MultiClientServer class represents a server that can handle multiple client connections concurrently.
 * It waits for clients to connect and creates a new Server thread to handle each client connection.
 */
class MultiClientServer extends Thread {
    /**
     * Constructs a new MultiClientServer.
     */
    public MultiClientServer() {
    }

    /**
     * Runs the server and waits for client connections.
     * For each client connection, it creates a new Server thread to handle communication with the client.
     */
    public void run() {
        Data data = new Data();
        ServerSocket serverSocket = null;
        int PORT = data.getPort();
        InetAddress iAddr = null;

        try {
            // Create a server socket and bind it to the specified port
            serverSocket = new ServerSocket(PORT, 20, InetAddress.getLocalHost());
            iAddr =  serverSocket.getInetAddress();
        } catch (IOException e) {
            System.out.printf("Could not listen on port: %s%n", PORT);
            System.exit(-1);
        }

        Socket clientSocket;
        System.out.println(serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        System.out.printf("Host Address: %s%s %n", iAddr, iAddr.toString().equals("0.0.0.0/0.0.0.0") ? " (wildcard) -- Listening on all interfaces " : "");
        System.out.printf("Halting operations and waiting for a client to connect (port %s)...%n", serverSocket.getLocalPort());

        while (!serverSocket.isClosed()) {
            try {
                // Wait for a client to connect and accept the connection
                clientSocket = serverSocket.accept();
                System.out.printf("A new client has established a connection! (%s on port %s!)%n", clientSocket.getInetAddress(), clientSocket.getPort());

                // Create a new Server Thread object to handle communication with the client
                Thread tServer = new Server(clientSocket);  // client handler -- Server class extends Thread

                // Start the Server thread
                tServer.start();

//                try{
//                    serverSocket.close();
//                } catch(IOException ioe){
//                    ioe.printStackTrace();
//                }
            } catch (IOException e) {
                System.out.printf("Accept failed on port %s%n", PORT);
                System.exit(-1);
            }
        }
    }



}

