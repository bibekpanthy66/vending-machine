package com.example.vendingmachine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VendingMachineApplication extends Application {
    private static Stage primaryStage;

    static public Stage getPrimaryStage() {
        return VendingMachineApplication.primaryStage;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home-page.fxml"));
        Scene scene = new Scene(root);
        primaryStage=stage;
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}