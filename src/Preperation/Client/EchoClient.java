package Preperation.Client;

import Data.Data;
import Preperation.Shared.PersistentTime;

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
        ObjectOutputStream out = null;  // changed to OOS
        ObjectInputStream in = null; // changed to OIS
        String filename = "time.ser";

        try {
            // Print the local host address
            System.out.println(InetAddress.getLocalHost());
            InetAddress iAddr = InetAddress.getLocalHost();
            System.out.printf("Attempting to create new echo socket for a connection to %s on port %d%n", iAddr, PORT);

            // Create a socket and connect to the server
            echoSocket = new Socket(InetAddress.getLocalHost(), PORT);  // run on localhost ip at specified port
            System.out.println("socket built successfully!");
            // Get the output stream of the socket
            out = new ObjectOutputStream(echoSocket.getOutputStream()); // output as object stream
            System.out.println("OOS constructed.");

            // Get the input stream of the socket
            in = new ObjectInputStream(new FileInputStream(filename));
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
                // write a new PersistentTime object with curr time
                out.writeObject(new PersistentTime());
                System.out.println("\u2713 Object sent");
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
