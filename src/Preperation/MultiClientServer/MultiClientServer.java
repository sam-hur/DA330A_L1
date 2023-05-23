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
        int PORT = data.getEchoPort();
        InetAddress iAddr = null;

        try {
            // Create a server socket and bind it to the specified port
            serverSocket = new ServerSocket(PORT);
            iAddr =  serverSocket.getInetAddress();
        } catch (IOException e) {
            System.out.printf("Could not listen on port: %s%n", PORT);
            System.exit(-1);
        }

        Socket clientSocket;
        System.out.println(serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        System.out.printf("Host Address: %s%s %n", iAddr, iAddr.toString().equals("0.0.0.0/0.0.0.0") ? " (wildcard) -- Listening on all interfaces " : "");
        System.out.println("Halting operations and waiting for a client to connect...");

        while (true) {
            try {
                // Wait for a client to connect and accept the connection
                clientSocket = serverSocket.accept();

                // Create a new Server thread to handle communication with the client
                Server server = new Server(clientSocket);

                System.out.printf("Connection established with %s on port %s!%n", clientSocket.getInetAddress(), serverSocket.getLocalPort());

                // Start the Server thread
                server.start();
            } catch (IOException e) {
                System.out.printf("Accept failed on port %s%n", PORT);
                System.exit(-1);
            }
        }
    }
}
