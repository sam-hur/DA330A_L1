package com.chatapp.gui;

import com.chatapp.Client.Client;
import com.chatapp.Client.ClientHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaFX Chat Application for exchanging messages between clients connected to a server.
 */
public class ChatApplicationOld extends Application {

    private ListView<ClientHandler> clientsListView;

    private TextArea chatArea;
    private TextField messageField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        clientsListView = new ListView<>();
        clientsListView.setPrefWidth(200);

        VBox clientsBox = new VBox(10);
        clientsBox.getChildren().addAll(clientsListView);
        clientsBox.setPadding(new Insets(10));
        clientsBox.setPrefWidth(220);


        primaryStage.setTitle("Chat Application");
        chatArea = new TextArea();
        chatArea.setEditable(false);

        VBox chatBox = new VBox(10);
        chatBox.getChildren().addAll(chatArea);
        chatBox.setPadding(new Insets(10));
        chatBox.setPrefWidth(380);

        messageField = new TextField();
        messageField.setOnAction(event -> sendMessage());

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage());

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(messageField, sendButton);
        inputBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(chatBox, inputBox);
        centerBox.setAlignment(Pos.CENTER);

        layout.setLeft(clientsBox);
        layout.setCenter(centerBox);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the connected clients list
        ObservableList<ClientHandler> connectedClients = FXCollections.observableArrayList(ClientHandler.connectedClients);
        System.out.println(ClientHandler.connectedClients);

        // Bind the client list to the ListView
        clientsListView.setItems(connectedClients);

        Client client = new Client();
        client.listen();  // listens on sep thread for incoming messages
        // replaced with send button in GUI.
//        client.sendMessage();  // sends newlines sent via the command prompt
//        client.sendMessage();
        System.out.println(connectedClients);

        Platform.runLater(() -> {
            // Bind the client list to the ListView
            clientsListView.setItems(FXCollections.observableArrayList(ClientHandler.connectedClients));
            System.out.println(ClientHandler.connectedClients + " on main thread");
        });
        System.out.println(connectedClients);

        System.out.println(ClientHandler.connectedClients);
        sendMessage();
    }

    /**
     * Sends a message to all connected clients.
     */
    private void sendMessage() {
        String message = messageField.getText();
        System.out.println();
        if (!message.isEmpty()) {
            // Send the message to all connected clients
            for(ClientHandler connected: ClientHandler.connectedClients){
                try {
                    connected.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Display the sent message in the chat area with sender ID and timestamp
            String senderId = "Me";
            String timestamp = getCurrentTimestamp();
            appendMessage("[" + timestamp + "] " + senderId + ": " + message);

            // Clear the message field
            messageField.clear();

        }
    }

    /**
     * Appends a message to the chat area.
     *
     * @param message The message to append.
     */
    private void appendMessage(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }

    /**
     * Gets the current timestamp in the format "hh:mm:ss".
     *
     * @return The current timestamp.
     */
    private String getCurrentTimestamp() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
/**
 * Gets the user color associated with this client.
 *
 * @return The user color.
 */
/*public Color getUserColor() {
    return userColor;
}

    /**
     * Generates a random user color from the available color array.
     *
     * @return The random user color.
     */
/*
    private Color getRandomUserColor() {
        Random random = new Random();
        int index = random.nextInt(userColors.length);
        return userColors[index];
    }
}*/