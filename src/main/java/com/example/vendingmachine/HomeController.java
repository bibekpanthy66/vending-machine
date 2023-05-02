package com.example.vendingmachine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HomeController {

    @FXML
    protected void onInsertCoin() throws IOException {
        Stage stage=VendingMachineApplication.getPrimaryStage();
        Parent root =FXMLLoader.load(getClass().getResource("insert-coin.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Vending Machine");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onVendingMachine() throws IOException {
        if(InsertCoinController.amountInteger==null || InsertCoinController.amountInteger<=0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please insert money first");
            alert.show();
        }else{
            Stage stage=VendingMachineApplication.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("vending-machine.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Vending Machine");
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    protected void onCheckItems() throws IOException {
        Stage stage=VendingMachineApplication.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("CheckItems.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Vending Machine");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onCheckLog() throws IOException {
        Stage stage=VendingMachineApplication.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("check-log.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Vending Machine");
        stage.setScene(scene);
        stage.show();
    }
}