package com.example.vendingmachine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CheckItemController implements Initializable {
    Connection connection = ConnectToDB.connectToDB();
    @FXML
    TableView<ItemTableDto> itemTable;
    @FXML
    TableColumn<ItemTableDto, String> itemId;
    @FXML
    TableColumn<ItemTableDto, String> itemValue;
    @FXML
    TableColumn<ItemTableDto, String> itemName;
    @FXML
    TableColumn<ItemTableDto, String> itemPrice;
    @FXML
    TableColumn<ItemTableDto, String> itemQuantity;
    @FXML
    TableColumn<ItemTableDto, String> editCol;
    ObservableList<ItemTableDto> list = FXCollections.observableArrayList();
    private Shared shared = new Shared();
    ItemTableDto itemTableDto = null;

    public void initialize(URL location, ResourceBundle resources) {
        itemId.setCellValueFactory(new PropertyValueFactory<ItemTableDto, String>("itemId"));
        itemValue.setCellValueFactory(new PropertyValueFactory<ItemTableDto, String>("value"));
        itemName.setCellValueFactory(new PropertyValueFactory<ItemTableDto, String>("name"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<ItemTableDto, String>("price"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<ItemTableDto, String>("quantity"));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.items i");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new ItemTableDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
            itemTable.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //add cell of button edit
        Callback<TableColumn<ItemTableDto, String>, TableCell<ItemTableDto, String>> cellFactory = (TableColumn<ItemTableDto, String> param) -> {
            // make cell containing buttons
            final TableCell<ItemTableDto, String> cell = new TableCell<ItemTableDto, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Button deleteIcon = new Button("delete");
                        Button editIcon = new Button("edit");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                itemTableDto = itemTable.getSelectionModel().getSelectedItem();
                                PreparedStatement preparedStatement = connection.prepareStatement("delete from vending_machine.items where id=?");
                                preparedStatement.setInt(1, Integer.parseInt(itemTableDto.getItemId()));
                                preparedStatement.executeUpdate();
                                refreshTable();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) ->{
                            try {
                                itemTableDto = itemTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader=new FXMLLoader();
                                loader.setLocation(getClass().getResource("updateBox.fxml"));
                                loader.load();
                                UpdateBoxController updateBoxController=loader.getController();
                                updateBoxController.setTextField(itemTableDto.getItemId(),itemTableDto.getValue(),itemTableDto.getName(),itemTableDto.getPrice(),itemTableDto.getQuantity(),itemTableDto.getItemId());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                updateBoxController.setStage(stage);
                                updateBoxController.setIdEnabled();
                                stage.showAndWait();
                                refreshTable();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);
                    }
                }
            };
            return cell;
        };
        editCol.setCellFactory(cellFactory);
        itemTable.setItems(list);
    }

    @FXML
    public void onAdd() throws IOException {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("updateBox.fxml"));
            loader.load();
            UpdateBoxController updateBoxController=loader.getController();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            updateBoxController.setStage(stage);
            updateBoxController.setIdDisable();
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onBack() throws IOException {
        shared.back();
    }

    @FXML
    public void refreshTable() {
        try {
            list.clear();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vending_machine.items i");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                list.add(new ItemTableDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
                itemTable.setItems(list);
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}