package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController{
    @FXML private Button loginBtn;
    @FXML private Button aboutProBtn;

    @FXML public void handleLoginBtnOnAction(ActionEvent event) throws IOException {
        Button login = (Button) event.getSource();
        Stage stage = (Stage) login.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }
    @FXML public void handleAboutProBtnOnAction(ActionEvent event) throws IOException {
        Button aboutPro = (Button) event.getSource();
        Stage stage = (Stage) aboutPro.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/about_pro_page.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }
}
