package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lettery.models.Account;
import lettery.models.AccountList;
import lettery.services.AccountFileDatasource;

import java.io.IOException;

public class ChangePassController extends BackBtnController{
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
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

        if (currentAccount.getRole().equals("admin")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        }
        else if (currentAccount.getRole().equals("officer")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_officer.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        }
    }

    @FXML public void handleOkBtnOnAction(ActionEvent event) throws IOException {
        if (currentAccount.getPassword().equals(oldPassword.getText())) {
            if(newPassword.getText().equals(confirmPassword.getText())) {
                if (!newPassword.getText().isEmpty()) {
                    accounts.checkAccount(currentAccount.getUsername(), currentAccount.getPassword());
                    accounts.getCurrentAccount().setPassword(newPassword.getText());
                    currentAccount.setPassword(newPassword.getText());
                    accountDatasource.setAccountsData(accounts, currentAccount);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Your password has been changed.");
                    alert.show();

                    Button ok = (Button) event.getSource();
                    Stage stage = (Stage) ok.getScene().getWindow();

                    if (currentAccount.getRole().equals("admin")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
                        stage.setScene(new Scene(loader.load(), 800, 600));
                        stage.show();
                    }
                    else if (currentAccount.getRole().equals("officer")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_officer.fxml"));
                        stage.setScene(new Scene(loader.load(), 800, 600));
                        stage.show();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter your new password.");
                    alert.show();
                }
            }
            else if (!newPassword.equals(confirmPassword)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your new password and confirm new password are not match.");
                alert.show();
            }
        }
        else if (!currentAccount.getPassword().equals(oldPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your old password is not correct.");
            alert.show();
        }
    }
}
