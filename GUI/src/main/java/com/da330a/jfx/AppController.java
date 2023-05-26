package com.da330a.jfx;

import com.da330a.jfx.Client.ClientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppController extends Thread {
    @FXML
    private Label chatRoom;

    @FXML
    private static VBox vbox;

    @FXML
    private TextField tf_message;

    @FXML
    private Button sendMessage;

    @FXML
    private TextArea chatArea;


    @FXML
    protected void onSendMessageClick() {
        String message = tf_message.getText();
        System.out.println();
        if (!message.isEmpty()) {
            // Send the message to all connected clients
            for (ClientHandler connected : ClientHandler.connectedClients) {
                connected.broadcast(message);
            }

            // Display the sent message in the chat area with sender ID and timestamp
            String senderId = "Me";
            String timestamp = getCurrentTimestamp();
            appendMessage("[" + timestamp + "] " + senderId + ": " + message);

            // Clear the message field
            tf_message.clear();

        }
    }

    private String getCurrentTimestamp(){
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    private void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    public static void changeBackground(String color){
        vbox.setStyle("-fx-background-color: " + color + ";");
    }
}
