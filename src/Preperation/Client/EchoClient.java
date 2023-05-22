package Preperation.Client;

import Data.Data;

import java.io.*;
import java.net.*;

/**
 * The EchoClient class represents a client that sends messages to a server and receives echo responses.
 */
public class EchoClient {
    Data data = new Data();
    int PORT = data.getEchoPort();

    /**
     * Constructs a new EchoClient.
     */
    public EchoClient() {
    }

    /**
     * Establishes a connection with the server and interacts with it.
     */
    public void establish() {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Print the local host address
            System.out.println(InetAddress.getLocalHost());

            System.out.printf("Attempting to create new echo socket for a connection to %s on port %d%n", data.serverIPAddress(), PORT);

            // Create a socket and connect to the server
            echoSocket = new Socket(data.serverIPAddress(), PORT);

            System.out.println("socket built successfully!");

            // Get the output stream of the socket
            out = new PrintWriter(echoSocket.getOutputStream(), true);

            // Get the input stream of the socket
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.printf("Couldn't get I/O on port %s%n", PORT);
            e.printStackTrace();
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try {
            // Read user input and send it to the server
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);

                // If the user enters "Bye.", break the loop
                if (userInput.equals("Bye."))
                    break;

                // Receive and print the server's response
                System.out.println("echo: " + in.readLine());
            }

            // Close the streams and the socket
            out.close();
            in.close();
            stdIn.close();
            echoSocket.close();
        } catch (IOException ioe) {
            System.out.println("Failed");
            System.exit(-1);
        }
    }
}
