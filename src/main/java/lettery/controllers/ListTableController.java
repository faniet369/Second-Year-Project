package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ListTableController extends BackBtnController{
    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button back = (Button) event.getSource();
        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_officer.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    public void createAlertError(String alertText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(alertText);
        alert.show();
    }

    abstract void showListData();
    abstract void clearSelected();
    @FXML public abstract void handleSubmitButtonOnAction(ActionEvent event);

    @FXML public void handleClearBtnOnAction(ActionEvent event) {
        clearSelected();
    }

}