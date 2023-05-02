package com.example.vendingmachine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckLogController implements Initializable {
    private Shared shared = new Shared();
    Connection connection = ConnectToDB.connectToDB();
    @FXML
    TableView<ItemLogsDto> itemLogsTable;
    @FXML
    TableColumn<ItemLogsDto, String> itemId;
    @FXML
    TableColumn<ItemLogsDto, String> itemValue;
    @FXML
    TableColumn<ItemLogsDto, String> itemName;
    @FXML
    TableColumn<ItemLogsDto, String> itemPrice;
    @FXML
    TableColumn<ItemLogsDto, String> itemQuantityFrom;
    @FXML
    TableColumn<ItemLogsDto, String> itemQuantityTo;
    @FXML
    TableColumn<ItemLogsDto, String> itemBalanceFrom;
    @FXML
    TableColumn<ItemLogsDto, String> itemBalanceTo;
    ObservableList<ItemLogsDto> itemList = FXCollections.observableArrayList();
    @FXML
    TableView<MoneyLogsDto> transactionLogsTable;
    @FXML
    TableColumn<MoneyLogsDto, String> transactionId;
    @FXML
    TableColumn<MoneyLogsDto, String> transactionType;
    @FXML
    TableColumn<MoneyLogsDto, String> transactionAmount;
    @FXML
    TableColumn<MoneyLogsDto, String> transactionBalance;
    ObservableList<MoneyLogsDto> transactionList = FXCollections.observableArrayList();
    @FXML
    public void onBack() throws IOException {
        shared.back();
    }

    public void initialize(URL location, ResourceBundle resources) {
        itemId.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("id"));
        itemValue.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("value"));
        itemName.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("name"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("price"));
        itemQuantityFrom.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("quantityFrom"));
        itemQuantityTo.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("quantityTo"));
        itemBalanceFrom.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("balanceFrom"));
        itemBalanceTo.setCellValueFactory(new PropertyValueFactory<ItemLogsDto, String>("balanceTo"));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.itemlogs i");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemList.add(new ItemLogsDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
            }
            itemLogsTable.setItems(itemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        transactionId.setCellValueFactory(new PropertyValueFactory<MoneyLogsDto, String>("id"));
        transactionType.setCellValueFactory(new PropertyValueFactory<MoneyLogsDto, String>("type"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<MoneyLogsDto, String>("amount"));
        transactionBalance.setCellValueFactory(new PropertyValueFactory<MoneyLogsDto, String>("balance"));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.money_logs ml");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactionList.add(new MoneyLogsDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            transactionLogsTable.setItems(transactionList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
