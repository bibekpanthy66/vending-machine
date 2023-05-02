package com.example.vendingmachine;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UpdateBoxController {
    @FXML
    private TextField id;

    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    Button add;
    @FXML
    Button update;
    private String old_id="";
    private Stage stage=new Stage();

    Connection connection = ConnectToDB.connectToDB();
    @FXML
    private void onUpdate(){
        Integer id=0;
        try{
            if(!this.id.getText().equals("")) {
                id = Integer.parseInt(this.id.getText());
            }
            if(!this.id.getText().equals("") && (id>12 || id<1)){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Id can only be between 1-12");
                alert.show();
            }else{
                try {
                    char value = (char)(id+96);
                    if (value > 'l') {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Value can only be between a-l");
                        alert.show();
                    } else {
                        String name = this.name.getText();
                        Integer price = 0;
                        try {
                            price = Integer.parseInt(this.price.getText());
                            if (price <= 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("price should be greater than 0");
                                alert.show();
                            } else {
                                Integer quantity = 0;
                                try {
                                    quantity = Integer.parseInt(this.quantity.getText());
                                    if (quantity < 0) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setContentText("quantity should be greater than or equal to 0");
                                        alert.show();
                                    } else {
                                        try {
                                            if (this.old_id.equals("")) {
                                                PreparedStatement preparedStatement = connection.prepareStatement("insert into vending_machine.items(id,value,name,price,quantity) values (?,?,?,?,?)");
                                                preparedStatement.setInt(1, id);
                                                preparedStatement.setString(2, "" + value);
                                                preparedStatement.setString(3, name);
                                                preparedStatement.setInt(4, price);
                                                preparedStatement.setInt(5, quantity);
                                                try {
                                                    preparedStatement.executeUpdate();
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setContentText("Update success");
                                                    stage.close();
                                                }catch (SQLIntegrityConstraintViolationException e){
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setContentText("Id already exists");
                                                    alert.show();
                                                }
                                            } else {
                                                PreparedStatement preparedStatement = connection.prepareStatement("update vending_machine.items set id=?,value = ?,name = ?,price=?,quantity = ? where id=?");
                                                preparedStatement.setInt(1, id);
                                                preparedStatement.setString(2, "" + value);
                                                preparedStatement.setString(3, name);
                                                preparedStatement.setInt(4, price);
                                                preparedStatement.setInt(5, quantity);
                                                preparedStatement.setInt(6, Integer.parseInt(old_id));
                                                preparedStatement.executeUpdate();
                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                alert.setContentText("Update success");
                                                stage.close();
                                            }
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("quantity can only be in numbers");
                                    alert.show();
                                }
                            }
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("price can only be in numbers");
                            alert.show();
                        }
                    }
                }catch (StringIndexOutOfBoundsException e){
                    throw new RuntimeException(e);
                }
            }
        }catch (NumberFormatException e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Id can only be in numbers");
            alert.show();
        }
    }

    void setTextField(String id, String value,String name, String price, String quantity,String old_id) {
        this.id.setText(id);
        this.name.setText(name);
        this.price.setText(price);
        this.quantity.setText(quantity);
        this.old_id=old_id;
    }
    void setStage(Stage stage){
        this.stage=stage;
    }
    void setIdDisable(){
        add.setVisible(true);
        update.setVisible(false);
    }
    void setIdEnabled(){
        add.setVisible(false);
        update.setVisible(true);
    }
}
