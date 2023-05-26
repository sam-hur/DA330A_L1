package com.chatapp.Client;

import com.chatapp.Data.EndOfWrite;
import com.chatapp.Shared.PersistentTime;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        out.write("("+time+") " + message);
        out.newLine();

        try {
                ObjectOutputStream outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
                Rectangle rectangle = new Rectangle(100, 100, 200, 150);
                rectangle.setFill(Color.BLUE);
                Pane pane = new Pane();
                pane.getChildren().add(rectangle);
                Scene scene = new Scene(pane, 400, 300);
                outputStream.writeObject(scene.getRoot());
                outputStream.flush();
                out.write("Graphic description sent!");
                out.newLine();
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//            try {
//
//
//
//                // Write the serialized object to a file
//                Serializable sceneGraphSerializable = new Serializable(){};
//                try (FileOutputStream fileOutputStream = new FileOutputStream("sceneGraph.ser");
//                    ObjectOutputStream outputStream2 = new ObjectOutputStream(fileOutputStream)) {
//                        outputStream.writeObject(sceneGraphSerializable);
//                        outputStream.flush();
//                    }
//                outputStream.writeObject(scene.getRoot());
//                outputStream.flush();
//                System.out.println("Graphic description sent!");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });


    public void disconnect() {
        ClientHandler.connectedClients.remove(this);
        broadcast("Server: " + clientSocket.getInetAddress().getHostAddress() + " (" + clientSocket.getPort() + ") has disconnected.");
    }
}
