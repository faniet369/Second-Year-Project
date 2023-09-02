package lettery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lettery.models.Account;
import lettery.models.AccountList;
import lettery.services.AccountFileDatasource;

import java.io.IOException;
import java.util.Date;

public class MenuOfficerController {
    @FXML private Button changePassBtn;
    @FXML private Button manageRoomBtn;
    @FXML private Button manageMailBtn;
    @FXML private Button logoutBtn;
    private AccountList accounts;
    private AccountFileDatasource accountDatasource;
    private Account currentAccount;

    @FXML public void initialize() {
        accountDatasource = new AccountFileDatasource("data", "accounts.csv");
        accounts = accountDatasource.getAccountsData();
        currentAccount = accountDatasource.getCurrentAccountData();
    }

    @FXML public void handleLogoutBtnOnAction(ActionEvent event) throws IOException {
        Date lastLogin = new Date();
        accounts.checkAccount(currentAccount.getUsername(), currentAccount.getPassword());
        accounts.getCurrentAccount().setLastLogin(lastLogin);
        currentAccount = new Account(null, null, null, null, new Date(0));
        accountDatasource.setAccountsData(accounts, currentAccount);

        Button logout = (Button) event.getSource();
        Stage stage = (Stage) logout.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML public void handleChangePassBtnOnAction(ActionEvent event) throws IOException {
        Button changePass = (Button) event.getSource();
        Stage stage = (Stage) changePass.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/change_pass.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML public void handleManageRoomBtnOnAction(ActionEvent event) throws IOException {
        Button manageRoom = (Button) event.getSource();
        Stage stage = (Stage) manageRoom.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/manage_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML public void handleManageMailBtnOnAction(ActionEvent event) throws IOException {
        Button manageMail = (Button) event.getSource();
        Stage stage = (Stage) manageMail.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/manage_mail.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }
}
