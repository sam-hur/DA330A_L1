package com.chatapp.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ChatApplication.fxml"));
        primaryStage.setTitle("ChatApplication");
        primaryStage.setScene(new Scene(root, 915, 420));
        primaryStage.show();
    }

    public void runApp(){

    }
}
