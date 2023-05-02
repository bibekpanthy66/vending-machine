package com.example.vendingmachine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Shared {
    public void back() throws IOException {
        Stage stage=VendingMachineApplication.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("home-page.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Vending Machine");
        stage.setScene(scene);
        stage.show();
    }
}
