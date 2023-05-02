package com.example.vendingmachine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class InsertCoinController implements Initializable {

    @FXML
    TextField amount;
    @FXML
    Label errorWithdraw;
    @FXML
    Label errorAdd;
    @FXML
    Label currentBalance;
    static public Integer amountInteger;
    private Shared shared=new Shared();

    Connection connection=ConnectToDB.connectToDB();
    @FXML
    protected void onAdd() throws IOException {
        try {
            if(Integer.parseInt(amount.getText())>0) {
                amountInteger += Integer.parseInt(amount.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(amount.getText() + " added");
                alert.show();
                currentBalance.setText(amountInteger.toString());
                PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO vending_machine.money_logs(type,amount,balance) VALUES (?,?,?)");
                preparedStatement.setString(1,"Add");
                preparedStatement.setInt(2,Integer.parseInt(amount.getText()));
                preparedStatement.setInt(3,amountInteger);
                preparedStatement.executeUpdate();
                amount.clear();
                errorWithdraw.setText("");
                errorAdd.setText("");
            }else{
                amount.clear();
                errorAdd.setText("Number should be greater than 0");
                errorWithdraw.setText("");
            }
        }catch (NumberFormatException e){
            amount.clear();
            errorAdd.setText("Write in numbers");
            errorWithdraw.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onWithdraw() throws IOException {
        try {
            if(amountInteger>0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(amountInteger + " returned");
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vending_machine.money_logs(type,amount,balance) VALUES (?,?,?)");
                preparedStatement.setString(1, "Withdraw");
                preparedStatement.setInt(2, amountInteger);
                preparedStatement.setInt(3, 0);
                preparedStatement.executeUpdate();
                amountInteger = 0;
                currentBalance.setText(amountInteger.toString());
                alert.show();
                errorAdd.setText("");
                errorWithdraw.setText("");
            }else{
                errorWithdraw.setText("No money to withdraw");
                errorAdd.setText("");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onBack() throws IOException {
        shared.back();
    }

    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (amountInteger == null) {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.money_logs ml");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    while (!resultSet.isLast()) {
                        resultSet.next();
                    }
                    amountInteger = resultSet.getInt(4);
                }else {
                    amountInteger=0;
                }
            }
            currentBalance.setText(amountInteger.toString());
            errorWithdraw.setText("");
            errorAdd.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
