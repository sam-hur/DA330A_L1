package Preperation.Server;

import Data.Data;

import java.io.*;
import java.net.*;

/**
 * The EchoServer class represents a server that receives messages from a client and echoes them back.
 */
public class EchoServer {
    /**
     * Constructs a new EchoServer.
     */
    public EchoServer() {
    }

    /**
     * Establishes a server socket and waits for a client to connect.
     * Once connected, it receives messages from the client and echoes them back.
     */
    public void establish() {
        Data data = new Data();
        int PORT = data.getEchoPort();
        ServerSocket serverSocket = null;

        try {
            // Create a server socket and bind it to the specified port
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.printf("Could not listen on port: %s%n", PORT);
            System.exit(-1);
        }

        Socket clientSocket = null;

        try {
            // Wait for a client to connect and accept the connection
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 1234");
            System.exit(-1);
        }

        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Create output and input streams for the client socket
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        String inputLine;

        try {
            // Read messages from the client and echo them back
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);

                // If the client sends "Bye.", break the loop
                if (inputLine.equals("Bye."))
                    break;
            }
        } catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

        try {
            // Close the client socket and the server socket
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }
    }
}
