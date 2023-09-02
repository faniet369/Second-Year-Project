package lettery.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lettery.models.*;
import lettery.services.BuildingFileDatasource;
import lettery.services.MailFileDatasource;
import java.util.ArrayList;
import java.util.Date;

public class ManageMailController extends ListTableController{
    @FXML private TableView<Mail> mailListTable;
    @FXML private Label newOrSelected, alertText;
    @FXML private TableColumn<Mail, String> buildingNameColumn, roomColumn, mailTypeColumn, receiverNameColumn, receiveDateColumn;
    @FXML private Button backBtn, submitBtn, receiveBtn, clearBtn;
    @FXML private TextField receiverName, room, size, senderName, trackNum, receiveDate;
    @FXML private ComboBox buildingComboBox, mailTypeComboBox, importanceComboBox, carrierComboBox;

    private BuildingList buildings;
    private MailFileDatasource mailFileDatasource;
    private MailList mailList;
    private Mail selectedMail, mail;

    @FXML public void initialize() {
        BuildingFileDatasource buildingDatasource = new BuildingFileDatasource("data", "buildings.csv");
        mailFileDatasource = new MailFileDatasource("data", "mails.csv");
        buildings = buildingDatasource.getBuildingsData();
        ArrayList<Building> listBuilding = buildings.getBuildingList();
        mailList = mailFileDatasource.getMailsData();
        mailList.sortMails();
        showListData();

        for (Building building : listBuilding) {
            buildingComboBox.getItems().add(building.getBuildingName());
        }
        mailTypeComboBox.getItems().addAll("Letter", "Document", "Parcel");
        importanceComboBox.getItems().addAll("Secret", "Urgent");
        carrierComboBox.getItems().addAll("Kerry", "Thailand Post");

        mailTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Letter")) {
                    receiverName.setDisable(false);
                    buildingComboBox.setDisable(false);
                    room.setDisable(false);
                    size.setDisable(false);
                    importanceComboBox.setDisable(true);
                    senderName.setDisable(false);
                    carrierComboBox.setDisable(true);
                    trackNum.setDisable(true);
                } else if (newValue.equals("Document")) {
                    receiverName.setDisable(false);
                    buildingComboBox.setDisable(false);
                    room.setDisable(false);
                    size.setDisable(false);
                    importanceComboBox.setDisable(false);
                    senderName.setDisable(false);
                    carrierComboBox.setDisable(true);
                    trackNum.setDisable(true);
                } else if (newValue.equals("Parcel")) {
                    receiverName.setDisable(false);
                    buildingComboBox.setDisable(false);
                    room.setDisable(false);
                    size.setDisable(false);
                    importanceComboBox.setDisable(true);
                    senderName.setDisable(false);
                    carrierComboBox.setDisable(false);
                    trackNum.setDisable(false);
                }
            }
        });

        mailListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMail(newValue);
            }
        });

        importanceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> importanceComboBox.setValue(newValue));
        carrierComboBox.valueProperty().addListener((observable, oldValue, newValue) -> carrierComboBox.setValue(newValue));
        mailTypeComboBox.getSelectionModel().selectFirst();

    }

    private void showSelectedMail(Mail mail) {
        newOrSelected.setText("Selected Mail");
        alertText.setVisible(false);
        receiveBtn.setDisable(false);
        submitBtn.setDisable(true);
//        receiverName.setDisable(false);
//        buildingComboBox.setDisable(false);
//        room.setDisable(false);
//        size.setDisable(false);
//        importanceComboBox.setDisable(false);
//        senderName.setDisable(false);
//        carrierComboBox.setDisable(false);
//        trackNum.setDisable(false);
//        receiveDate.setDisable(false);

        selectedMail = mail;
        mailTypeComboBox.setValue(mail.getMailType());
        receiverName.setText(mail.getReceiver().getName());
        buildingComboBox.setValue(mail.getReceiver().getRoom().getBuildingName());
        room.setText(mail.getReceiver().getRoom().getRoomName());
        size.setText(mail.getSize());
        importanceComboBox.setValue(mail.getImportance());
        senderName.setText(mail.getSender().getName());
        carrierComboBox.setValue(mail.getCarrier());
        trackNum.setText(mail.getTrackingNumber());
        receiveDate.setText(mail.getStringReceiveDate());

        mailTypeComboBox.setDisable(true);
    }

    public void showListData() {
        ObservableList<Mail> mailObservableList = FXCollections.observableArrayList(mailList.getMails());
        mailTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMailType()));
        buildingNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReceiver().getRoom().getBuildingName()));
        roomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReceiver().getRoom().getRoomName()));
        receiverNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReceiver().getName()));
        receiveDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStringReceiveDate()));

        mailListTable.setItems(mailObservableList);
    }

    public void clearSelected() {
        newOrSelected.setText("New Mail");
        alertText.setVisible(true);
        mailTypeComboBox.setDisable(false);
        receiveBtn.setDisable(true);
        submitBtn.setDisable(false);
//        receiveDate.setDisable(true);

        selectedMail = null;
        mail = null;
        mailListTable.getSelectionModel().clearSelection();
        receiverName.clear();
        mailTypeComboBox.setValue(null);
        mailTypeComboBox.getSelectionModel().selectFirst();
        buildingComboBox.setValue(null);
        room.clear();
        size.clear();
        importanceComboBox.setValue(null);
        senderName.clear();
        carrierComboBox.setValue(null);
        trackNum.clear();
        receiveDate.clear();
    }

    private boolean checkHasSameValue(ObservableList<Object> listValue, String newValue) {
        for (Object value : listValue) {
            if (value.equals(newValue)) {
                return true;
            }
        }
        return false;
    }

    @FXML public void handleSubmitButtonOnAction(ActionEvent event) {
        if (!receiverName.getText().isEmpty() && !((String) buildingComboBox.getValue()).isEmpty() && !room.getText().isEmpty() && !size.getText().isEmpty() && !senderName.getText().isEmpty()) {
            Room currentRoom = buildings.getThisRoom((String) buildingComboBox.getValue(), room.getText());
            if (currentRoom != null) {
                if (currentRoom.checkHasSameResident(receiverName.getText())) {
                    Date receiveDateAndTime = new Date();
                    mail = new Mail(senderName.getText(), receiverName.getText(), currentRoom, size.getText(), receiveDateAndTime);
                    if (mailTypeComboBox.getValue().equals("Document")) {
                        if (importanceComboBox.getValue() != null && !((String) importanceComboBox.getValue()).isEmpty()) {
                            mail = new Mail(senderName.getText(), receiverName.getText(), currentRoom, size.getText(), receiveDateAndTime, ((String) importanceComboBox.getValue()).trim());
                            if (!checkHasSameValue(importanceComboBox.getItems(), ((String) importanceComboBox.getValue()).trim())) {
                                importanceComboBox.getItems().add(((String) importanceComboBox.getValue()).trim());
                            }
                        } else {
                            mail = null;
                            createAlertError("Please fill out all information.");
                        }
                    }
                    else if (mailTypeComboBox.getValue().equals("Parcel")) {
                        if (carrierComboBox.getValue() != null && !((String) carrierComboBox.getValue()).isEmpty() && !trackNum.getText().isEmpty()) {
                            mail = new Mail(senderName.getText(), receiverName.getText(), currentRoom, size.getText(), receiveDateAndTime, ((String) carrierComboBox.getValue()).trim(), trackNum.getText());
                            if (!checkHasSameValue(carrierComboBox.getItems(), ((String) carrierComboBox.getValue()).trim())) {
                                carrierComboBox.getItems().add(((String) carrierComboBox.getValue()).trim());
                            }
                        }
                        else {
                            mail = null;
                            createAlertError("Please fill out all information.");
                        }
                    }
                } else {
                    createAlertError("Can't find " + receiverName.getText() + " in room " + currentRoom.getRoomName() + ".");
                }
            } else {
                createAlertError("This room does not exist.");
            }
        } else {
            createAlertError("Please fill out all information.");
        }

        mailList.addMail(mail);
        mailFileDatasource.setMailsData(mailList);
        if (mail != null) {
            clearSelected();
        }
        mailList.sortMails();
        showListData();
        mailListTable.refresh();
    }

    @FXML public void handleReceiveBtnOnAction(ActionEvent event) {
        mailList.removeMail(selectedMail);
        mailFileDatasource.setMailsData(mailList);
        clearSelected();
        mailList.sortMails();
        showListData();
        mailListTable.refresh();
    }
}
