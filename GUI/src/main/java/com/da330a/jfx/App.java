package com.da330a.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private static App instance;

    public static List<App> appInstances = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        appInstances.add(instance);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 460, 500);
        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }

    public static App getInstance() {
        return instance;
    }

}