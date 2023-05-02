package com.example.vendingmachine;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VendingMachineController implements Initializable {
    private Shared shared=new Shared();
    @FXML
    RadioButton a;
    @FXML
    RadioButton b;
    @FXML
    RadioButton c;
    @FXML
    RadioButton d;
    @FXML
    RadioButton e;
    @FXML
    RadioButton f;
    @FXML
    RadioButton g;
    @FXML
    RadioButton h;
    @FXML
    RadioButton i;
    @FXML
    RadioButton j;
    @FXML
    RadioButton k;
    @FXML
    RadioButton l;
    @FXML
    ToggleGroup items;

    @FXML
    Label balance;

    Connection connection=ConnectToDB.connectToDB();

    @FXML
    public void onBack() throws IOException {
        shared.back();
    }
    @FXML
    public void onBuy(){
        try {
            RadioButton selectedRadioButton = (RadioButton) items.getSelectedToggle();
            String itemValue = selectedRadioButton.getText().split("\n")[0];
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.items i");
            ResultSet resultSet = preparedStatement.executeQuery();
            String name="";
            Integer quantity=0;
            Integer price=0;
            while (resultSet.next()) {
                if (resultSet.getString(3).equals(itemValue)) {
                    itemValue=resultSet.getString(2);
                    name = resultSet.getString(3);
                    quantity = resultSet.getInt(5);
                    price = resultSet.getInt(4);
                }
            }
            if(quantity<=0){
                Alert quantityAlert=new Alert(Alert.AlertType.INFORMATION);
                quantityAlert.setContentText("Out of stock");
                quantityAlert.show();
            }else {
                if (InsertCoinController.amountInteger - price < 0) {
                    Alert amountAlert=new Alert(Alert.AlertType.INFORMATION);
                    amountAlert.setContentText("Insufficient balance");
                    amountAlert.show();
                }else{
                    Integer newBalance=InsertCoinController.amountInteger - price;
                    preparedStatement = connection.prepareStatement("insert into vending_machine.itemlogs(value,name,price,quantityFrom,quantityTo,balancefrom,balanceTo) values (?,?,?,?,?,?,?)");
                    preparedStatement.setString(1,itemValue);
                    preparedStatement.setString(2,name);
                    preparedStatement.setInt(3,price);
                    preparedStatement.setInt(4,quantity);
                    preparedStatement.setInt(5,quantity-1);
                    preparedStatement.setInt(6,InsertCoinController.amountInteger);
                    preparedStatement.setInt(7,newBalance);
                    preparedStatement.executeUpdate();
                    preparedStatement = connection.prepareStatement("insert into vending_machine.money_logs(type, amount,balance) values (?,?,?)");
                    preparedStatement.setString(1,"Buy");
                    preparedStatement.setInt(2,price);
                    preparedStatement.setInt(3,InsertCoinController.amountInteger - price);
                    preparedStatement.executeUpdate();
                    InsertCoinController.amountInteger=newBalance;
                    preparedStatement = connection.prepareStatement("update vending_machine.items set quantity=? where value = ?");
                    preparedStatement.setInt(1,quantity-1);
                    preparedStatement.setString(2,itemValue);
                    preparedStatement.executeUpdate();
                    balance.setText(newBalance.toString());
                    load();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(1+" "+ name +" dispensed");
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        load();
    }
    public void load(){
        try {
            balance.setText(InsertCoinController.amountInteger.toString());
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.items i");
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    a.setDisable(true);
                    a.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        a.setDisable(true);
                        a.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        a.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                a.setDisable(true);
                a.setText("empty");
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    b.setDisable(true);
                    b.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        b.setDisable(true);
                        b.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        b.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else {
                b.setDisable(true);
                b.setText("empty");
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    c.setDisable(true);
                    c.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        c.setDisable(true);
                        c.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        c.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                c.setText("empty");
                c.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    d.setDisable(true);
                    d.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        d.setDisable(true);
                        d.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        d.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                d.setText("empty");
                d.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    e.setDisable(true);
                    e.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        e.setDisable(true);
                        e.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        e.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                e.setText("empty");
                e.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    f.setDisable(true);
                    f.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        f.setDisable(true);
                        f.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        f.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                f.setText("empty");
                f.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    g.setDisable(true);
                    g.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        g.setDisable(true);
                        g.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        g.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                g.setText("empty");
                g.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    h.setDisable(true);
                    h.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        h.setDisable(true);
                        h.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        h.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                h.setText("empty");
                h.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    i.setDisable(true);
                    i.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        i.setDisable(true);
                        i.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        i.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                i.setText("empty");
                i.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    j.setDisable(true);
                    j.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        j.setDisable(true);
                        j.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        j.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                j.setText("empty");
                j.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    k.setDisable(true);
                    k.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        k.setDisable(true);
                        k.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        k.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                k.setText("empty");
                k.setDisable(true);
            }
            if(resultSet.next()){
                if(resultSet.getString(3)==null){
                    l.setDisable(true);
                    l.setText("empty");
                }else{
                    if(resultSet.getInt(5)<=0){
                        l.setDisable(true);
                        l.setText(resultSet.getString(3)+"\n(Out of Stock)");
                    }else {
                        l.setText(resultSet.getString(3)+"\nQuantity: "+resultSet.getInt(5)+"\nprice: "+resultSet.getInt(4));
                    }
                }
            }else{
                l.setText("empty");
                l.setDisable(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
