package com.da330a.jfx.Client;


import com.da330a.jfx.Data.EndOfWrite;
import com.da330a.jfx.Shared.PersistentTime;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    public static List<ClientHandler> connectedClients = new ArrayList<>();
    private BufferedWriter out;
    private BufferedReader in;
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ClientHandler.connectedClients.add(this);
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                broadcast(clientSocket.getInetAddress().getHostAddress()+" ("+clientSocket.getPort()+ "):\t" + inputLine);

                if (inputLine.equalsIgnoreCase("Bye")) {
                    throw new EndOfWrite();
                }

                if (inputLine.equals("Bye."))
                    break;
            }
        } catch (EndOfWrite eow) {
            disconnect();
            System.out.printf("\u2713 connection to %s (Port %s) terminated successfully%n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
        } catch (IOException ioe) {
            System.out.printf("Connection to %s (port %s) interrupted.%n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
            disconnect();
        } finally {
            try {
                clientSocket.close();
                out.close();
                in.close();
            } catch (IOException e) {
                System.out.println("Failed in closing");
            }
        }
    }

    public void broadcast(String message) {
        for (ClientHandler handler : ClientHandler.connectedClients) {
            if (handler != this) { // Exclude the current client
                try {
                    handler.sendMessage(message);
                } catch (IOException e) {
                    handler.disconnect();
                }
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        String time = new PersistentTime().getTime().toString().split("\s")[3];
        String msg = "("+time+") " + message;
        try {
            out.write(msg);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        ClientHandler.connectedClients.remove(this);
        broadcast("Server: " + clientSocket.getInetAddress().getHostAddress() + " (" + clientSocket.getPort() + ") has disconnected.");
    }
}
