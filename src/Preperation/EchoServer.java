package Preperation;

import Data.Data;

import java.io.*;
import java.net.*;

public class EchoServer {
    public EchoServer() {
    }

    public void establish() {
        Data data = new Data();
        int PORT = data.getEchoPort();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.printf("Could not listen on port: %s%n", PORT);
            System.exit(-1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 1234");
            System.exit(-1);
        }

        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equals("Bye."))
                    break;
            }
        } catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }
    }
}