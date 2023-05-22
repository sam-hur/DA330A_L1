package Preperation;

import Data.Data;

import java.io.*;
import java.net.*;

public class EchoClient {
    Data data = new Data();
    int PORT = data.getEchoPort();
    public EchoClient() {
    }
    public void establish() {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            System.out.println(InetAddress.getLocalHost());
            echoSocket = new Socket("10.0.2.15", PORT);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
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
        BufferedReader stdIn = new BufferedReader( new
                InputStreamReader(System.in));
        String userInput;
        try{
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if (userInput.equals("Bye."))
                    break;
                System.out.println("echo: " + in.readLine());
            }
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