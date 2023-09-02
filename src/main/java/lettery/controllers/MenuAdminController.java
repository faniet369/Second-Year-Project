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

public class MenuAdminController {
    @FXML private Button changePassBtn;
    @FXML private Button createAccOfficerBtn;
    @FXML private Button officerListBtn;
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

    @FXML public void handleCreateAccOfficerBtnOnAction(ActionEvent event) throws IOException {
        Button createAccOfficer = (Button) event.getSource();
        Stage stage = (Stage) createAccOfficer.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create_acc_officer.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    @FXML public void handleOfficerListBtnOnAction(ActionEvent event) throws IOException {
        Button officerList = (Button) event.getSource();
        Stage stage = (Stage) officerList.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/officer_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }
}
