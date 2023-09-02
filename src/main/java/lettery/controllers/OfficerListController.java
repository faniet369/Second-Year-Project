package lettery.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lettery.models.Account;
import lettery.models.AccountList;
import lettery.services.AccountFileDatasource;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class OfficerListController extends BackBtnController{
    @FXML private TableView<Account> officerListTable;
    @FXML private Label username;
    @FXML private Label name;
    @FXML private Label lastLogin;
    @FXML private ImageView picOfficer;
    @FXML private TableColumn<Account, String> usernameColumn, nameColumn, lastLoginColumn;
    @FXML private Button backBtn;

    private AccountList accounts;
    private AccountFileDatasource accountDatasource;
    private ObservableList<Account> officerObservableList;

    @FXML public void initialize() {
        accountDatasource = new AccountFileDatasource("data", "accounts.csv");
        accounts = accountDatasource.getAccountsData();
        accounts.setAccounts(accounts.getOfficerAccountList());
        accounts.sortOfficerAccounts();
        showOfficerAccountData();

        officerListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedOfficer(newValue);
            }
        });
    }

    private void showSelectedOfficer(Account officerAccount) {
        name.setText(officerAccount.getName());
        username.setText(officerAccount.getUsername());
        lastLogin.setText(officerAccount.getStringLastLogin());
    }

    private void showOfficerAccountData() {
        officerObservableList = FXCollections.observableArrayList(accounts.getAccountList());

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastLoginColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStringLastLogin()));
        lastLoginColumn.setSortType(TableColumn.SortType.DESCENDING);

        officerListTable.setItems(officerObservableList);
    }

    @FXML public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button back = (Button) event.getSource();
        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu_admin.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

}
