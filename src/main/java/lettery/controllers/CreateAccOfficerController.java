package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lettery.models.Account;
import lettery.models.AccountList;
import lettery.services.AccountFileDatasource;

import java.io.IOException;

public class CreateAccOfficerController extends BackBtnController{
    @FXML private TextField officerName;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField confirmPassword;
    @FXML private Button okBtn;
    @FXML private Button backBtn;
    private AccountList accounts;
    private AccountFileDatasource accountDatasource;
    private Account currentAccount;

    @FXML public void initialize() {
        accountDatasource = new AccountFileDatasource("data", "accounts.csv");
        accounts = accountDatasource.getAccountsData();
        currentAccount = accountDatasource.getCurrentAccountData();
    }

    @FXML public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button back = (Button) event.getSource();
        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML public void handleOkBtnOnAction(ActionEvent event) throws IOException {
        if (accounts.checkUsernameAvailable(username.getText())) {
            if (password.getText().equals(confirmPassword.getText())) {
                if (!password.getText().isEmpty()) {
                    Account officerAccount = new Account(username.getText(), password.getText(), officerName.getText());
                    accounts.addAccounts(officerAccount);
                    accountDatasource.setAccountsData(accounts, currentAccount);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Your account has been created.");
                    alert.show();

                    Button ok = (Button) event.getSource();
                    Stage stage = (Stage) ok.getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
                    stage.setScene(new Scene(loader.load(), 800, 600));
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter your password.");
                    alert.show();
                }
            }
            else if (!password.getText().equals(confirmPassword.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your password and confirm password are not match.");
                alert.show();
            }
        }
        else if (!accounts.checkUsernameAvailable(username.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This username is already taken.");
            alert.show();
        }
    }
}
