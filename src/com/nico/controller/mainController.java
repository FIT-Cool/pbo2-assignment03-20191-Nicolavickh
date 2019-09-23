// Made by Nicolavickh Yohanes - 1772016
package com.nico.controller;

import com.nico.entity.category;
import com.nico.entity.item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<category> cmbxCategory;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnReset;
    @FXML
    private TableColumn<item, String> colID;
    @FXML
    private TableColumn<item, String> colName;
    @FXML
    private TableColumn<item, String> colCategory;
    @FXML
    private TableColumn<item, String> colDate;
    @FXML
    private ObservableList<item> items;
    @FXML
    private ObservableList<category> categories;
    @FXML
    private mainController mainController;
    @FXML
    private TableView<item> tableItem;
    @FXML
    private BorderPane mainStage;
    private int index=0;


    @FXML
    private void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        item i = tableItem.getSelectionModel().getSelectedItem();
        txtID.setId(String.valueOf(i.getId()));
        txtName.setText(i.getName());
        cmbxCategory.setValue(i.getCategory());
        dpDate.setValue(LocalDate.parse(i.getDate()));
        index=tableItem.getSelectionModel().getSelectedIndex();
    }

    private void setMainController(mainController mainController) {
        this.mainController = mainController;
        tableItem.setItems(mainController.getItems());
    }

    public ObservableList<category> getCategories() {
        if (categories == null) {
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }

    public ObservableList<item> getItems() {
        if (items == null) {
            items = FXCollections.observableArrayList();
        }
        return items;
    }

    @FXML
    private void saveAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (txtName.getText() == null || txtID.getText() == null || cmbxCategory.getValue() == null) {
            alert.setContentText("Please fill ID / Name / Category");
            alert.showAndWait();
        } else {
            int count = (int) items.stream().filter(p -> p.getId() == Integer.parseInt(txtID.getText())).count();
            if (count >= 1) {
                alert.setContentText("Dupicate ID !");
                alert.showAndWait();
            } else {
                item i = new item();
                i.setId(Integer.parseInt(txtID.getText()));
                i.setName(txtName.getText());
                i.setDate(String.valueOf(dpDate.getValue()));
                i.setCategory(cmbxCategory.getSelectionModel().getSelectedItem());
                items.add(i);
            }
        }
    }

    @FXML
    private void resetAction(ActionEvent actionEvent) {
        txtName.setText(null);
        txtID.setText(null);
        cmbxCategory.getSelectionModel().select(-1);
        dpDate.getEditor().clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void updateAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        item i = tableItem.getSelectionModel().getSelectedItem();
        i.setId(Integer.parseInt(txtID.getText()));
        i.setName(txtName.getText());
        i.setDate(String.valueOf(dpDate.getValue()));
        i.setCategory(cmbxCategory.getValue());
        tableItem.getItems().set(index,i);
        tableItem.refresh();

    }

    @FXML
    private void showCategory(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/nico/view/category.fxml"));
            BorderPane root = loader.load();
            categoryController controller = loader.getController();
            controller.setMainController(this);

            Stage mainStage = new Stage();
            mainStage.setTitle("Category Management");
            mainStage.setScene(new Scene(root));
            mainStage.show();


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        cmbxCategory.setItems(categories);
        tableItem.setItems(items);
        colID.setCellValueFactory(data -> {
            item p = data.getValue();
            return new SimpleStringProperty(String.valueOf(p.getId()));
        });
        colName.setCellValueFactory(data -> {
            item p = data.getValue();
            return new SimpleStringProperty(p.getName());
        });
        colDate.setCellValueFactory(data -> {
            item p = data.getValue();
            return new SimpleStringProperty(String.valueOf(p.getDate()));
        });
        colCategory.setCellValueFactory(data -> {
            item p = data.getValue();
            return new SimpleStringProperty(p.getCategory().getName());
        });
    }

    @FXML
    private void menuClose(ActionEvent actionEvent) {
        Stage stage = (Stage) mainStage.getScene().getWindow();
        stage.close();
    }
}


