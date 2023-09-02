package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lettery.models.AccountList;
import lettery.services.AccountFileDatasource;

import java.io.IOException;
import java.util.Date;

public class LoginPageController extends BackBtnController{
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button okBtn;
    @FXML private Button backBtn;
    private AccountList accounts;
    private AccountFileDatasource accountDatasource;

    @FXML public void initialize() {
        accountDatasource = new AccountFileDatasource("data", "accounts.csv");
        accounts = accountDatasource.getAccountsData();
    }

    @FXML public void handleOkBtnOnAction(ActionEvent event) throws IOException {
        if (accounts.checkAccount(username.getText(), password.getText())) {
            if (accounts.getCurrentAccount().getRole().equals("admin")) {
                accountDatasource.setAccountsData(accounts, accounts.getCurrentAccount());

                Button ok = (Button) event.getSource();
                Stage stage = (Stage) ok.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                stage.show();
            }
            else if (accounts.getCurrentAccount().getRole().equals("officer")) {
                Date lastLogin = new Date();
                accounts.getCurrentAccount().setLastLogin(lastLogin);

                accountDatasource.setAccountsData(accounts, accounts.getCurrentAccount());

                Button ok = (Button) event.getSource();
                Stage stage = (Stage) ok.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_officer.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                stage.show();
            }
        }
    }
    @FXML public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button back = (Button) event.getSource();
        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_page.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }
}
