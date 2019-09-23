// Made by Nicolavickh Yohanes - 1772016
package com.nico.controller;

import com.nico.entity.category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class categoryController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave;
    @FXML
    private TableColumn<category, String> colID;
    @FXML
    private TableColumn<category, String> colCategoryName;
    @FXML
    private TableView<category> tableCategory;
    @FXML
    private mainController mainController;
    private ObservableList<category> categories;

    @FXML
    public void setMainController(mainController mainController) {
        this.mainController = mainController;
        tableCategory.setItems(mainController.getCategories());
    }

    @FXML
    private void saveAction(ActionEvent actionEvent) {
        category c = new category();
        try {
            if (txtName.getText().trim().isEmpty() && txtID.getText().trim().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please fill Category Name");
                a.show();
            } else {
                boolean cek = false;
                for (category cat : mainController.getCategories()) {
                    if (cat.getName().trim().contains(txtName.getText().trim())) {
                        cek = true;
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Duplicate Category Name!");
                        a.show();
                    }
                    if (String.valueOf(cat.getId()).trim().contains(txtID.getText().trim())){
                        cek = true;
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Duplicate Category ID!");
                        a.show();
                    }
                }
                if (!cek) {
                    c.setName(txtName.getText().trim());
                    c.setId(Integer.valueOf(txtID.getText().trim()));
                    mainController.getCategories().add(c);
                }
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Field ID insert number please..");
            a.show();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(data -> {
            category c = data.getValue();
            return new SimpleStringProperty(String.valueOf(c.getId()));
        });
        colCategoryName.setCellValueFactory(data -> {
            category c = data.getValue();
            return new SimpleStringProperty(c.getName());
        });
    }

}
