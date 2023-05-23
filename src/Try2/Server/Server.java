package Try2.Server;

import Data.Data;
import Try2.Client.ClientHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This echo server prints its throughput back to the terminal.
 */
public class Server extends Thread{
    public Server(){
        serverSocket = createServerSocket();
    }
    private final Data data = new Data();
    private final int PORT  = data.getPort(); // port on which the server will run

    private ServerSocket serverSocket = null;

    private BufferedReader in;
    private BufferedWriter out;  // PW for auto-flush

    @Override
    public void run() {
        // server socket details
        System.out.println(serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        System.out.printf("Halting operations and waiting for a client to connect (port %s)...%n", serverSocket.getLocalPort());

        Socket clientSocket;

        while (!serverSocket.isClosed()) {
            clientSocket = listen();
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("A new client has established a connection! (%s on port %s)%n", clientSocket.getInetAddress(), clientSocket.getPort());
            ClientHandler ch = new ClientHandler(clientSocket);
            ch.start();
        }
    }

    private ServerSocket createServerSocket(){
        ServerSocket ss = null;
        try {
            ss = data.limitToLocalNetwork ? new ServerSocket(PORT) : new ServerSocket(PORT, 20, InetAddress.getByName(data.serverIP()));
        } catch (IOException ioe){
            System.err.printf("Could not listen on port: %s%n", PORT);
            ioe.printStackTrace();
            System.exit(-1);
        }
        return ss;
    }

    private Socket listen() {
        Socket clientSocket = null;
        try{
            clientSocket = serverSocket.accept();  // Pause thread for a client to connect and accept the connection
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return clientSocket;
    }
}

