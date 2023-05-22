package Preperation.MultiClientServer;

import java.io.*;
import java.net.*;

/**
 * The Server class represents a thread that handles communication with a client.
 * It receives messages from the client and echoes them back.
 */
public class Server extends Thread {
    Socket clientSocket = null;

    /**
     * Constructs a new Server thread with the specified client socket.
     * @param clientSocket The client socket to communicate with.
     */
    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Runs the Server thread and handles communication with the client.
     */
    public void run() {
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

        String inputLine, outputLine;

        try {
            // Read messages from the client, echo them back, and print them to the console
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println(inputLine);

                // If the client sends "Bye.", break the loop
                if (inputLine.equals("Bye."))
                    break;
            }
        } catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

        try {
            // Close the client socket and the streams
            clientSocket.close();
            out.close();
            in.close();
        } catch (IOException ioe) {
            System.out.println("Failed in closing down");
            System.exit(-1);
        }
    }
}
