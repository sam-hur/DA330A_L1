package com.chatapp.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
            stage.setTitle("Application");
            stage.setScene(new Scene(root, 915, 420));
            stage.show();
            Client client = new Client();
            client.listen();  // listens on sep thread for incoming messages
            System.out.println(ClientHandler.connectedClients); // prints []
            client.sendMessage();  // sends newlines sent via the command prompt
        }
    }
