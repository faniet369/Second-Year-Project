package lettery.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lettery.models.*;
import lettery.services.BuildingFileDatasource;
import java.util.ArrayList;

public class ManageRoomController extends ListTableController {
    @FXML
    private TableView<Room> roomListTable;
    @FXML
    private Label newOrSelected, alertText;
    @FXML
    private TableColumn<Room, String> buildingNameColumn, roomColumn, roomTypeColumn, residentColumn;
    @FXML
    private Button backBtn, submitBtn, clearBtn;
    @FXML
    private TextField floor, room, resident1, resident2;
    @FXML
    private ComboBox buildingComboBox, roomTypeComboBox, selectBuildingComboBox;

    private BuildingList buildings;
    private ArrayList<Building> list;
    private Building roomList;
    private Room selectedRoom, newRoom;
    private BuildingFileDatasource buildingDatasource;

    @FXML
    public void initialize() {
        newOrSelected.setText("New Room");
        alertText.setText("Submit before add resident.");
        buildingDatasource = new BuildingFileDatasource("data", "buildings.csv");
        buildings = buildingDatasource.getBuildingsData();
        list = buildings.getBuildingList();
        roomList = list.get(0);
        newRoom = new Room();
        showListData();

        for (Building building : list) {
            selectBuildingComboBox.getItems().add(building.getBuildingName());
            buildingComboBox.getItems().add(building.getBuildingName());
        }
        roomTypeComboBox.getItems().addAll("1-bedroom", "2-bedroom");
        clearBtn.setDisable(true);
        resident1.setEditable(false);
        resident2.setEditable(false);
        buildingComboBox.setEditable(true);
        roomTypeComboBox.setValue("1-bedroom");
        selectBuildingComboBox.getSelectionModel().selectFirst();

        roomListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelected(newValue);
            }
        });

        buildingComboBox.valueProperty().addListener((observable, oldValue, newValue) -> buildingComboBox.setValue(newValue));

        selectBuildingComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (Building building : list) {
                if (building.getBuildingName().equals(newValue)) {
                    roomList = building;
                }
            }
            showListData();
        });

    }

    public void showSelected(Room room) {
        newOrSelected.setText("Selected Room");
        alertText.setVisible(false);
        clearBtn.setDisable(false);
        resident1.setEditable(true);
        buildingComboBox.setEditable(false);

        selectedRoom = room;
        buildingComboBox.setValue(room.getBuildingName());
        roomTypeComboBox.setValue(room.getRoomType());
        floor.setText(room.getFloor());
        this.room.setText(room.getRoomName());
        resident1.setText(room.getResident1());
        resident2.setText(room.getResident2());
        resident2.setEditable(room.getRoomType().equals("2-bedroom"));

        floor.setEditable(false);
        this.room.setEditable(false);
        buildingComboBox.setDisable(true);
        roomTypeComboBox.setDisable(true);
    }

    public void showListData() {
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList(roomList.getRoomList());
        buildingNameColumn.setCellValueFactory(new PropertyValueFactory<>("buildingName"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        residentColumn.setCellValueFactory(new PropertyValueFactory<>("resident1"));

        roomListTable.setItems(roomObservableList);
    }

    public void clearSelected() {
        newOrSelected.setText("New Room");
        alertText.setVisible(true);
        clearBtn.setDisable(true);
        resident1.setEditable(false);
        resident2.setEditable(false);
        buildingComboBox.setDisable(false);
        roomTypeComboBox.setDisable(false);
        buildingComboBox.setEditable(true);
        floor.setEditable(true);
        this.room.setEditable(true);

        selectedRoom = null;
        roomListTable.getSelectionModel().clearSelection();
        newRoom = new Room();
        roomTypeComboBox.setValue("1-bedroom");
        room.clear();
        floor.clear();
        resident1.clear();
        resident2.clear();
    }

    @FXML
    public void handleSubmitButtonOnAction(ActionEvent event) {
        if (newOrSelected.getText().equals("New Room")) {
            Building newRoomList = roomList;
            if (buildingComboBox.getValue() != null && !((String) buildingComboBox.getValue()).isEmpty()) {
                newRoom.setBuildingName(((String) buildingComboBox.getValue()).trim());
                if (!buildings.checkHasSameBuildingName(newRoom.getBuildingName())) {
                    newRoomList = new Building(newRoom.getBuildingName());
                } else {
                    newRoomList = buildings.getCurrentBuilding();
                }
            }

            if (!floor.getText().isEmpty() && !room.getText().isEmpty()) {
                newRoom.setFloor(floor.getText());
                if (newRoom.checkRoomNameRelatedToFloor(room.getText())) {
                    if (newRoomList.checkRoomNameAvailable(room.getText(), newRoom.getBuildingName())) {
                        newRoom.setRoomName(room.getText());
                    } else {
                        createAlertError("This room has already exists");
                    }
                } else {
                    createAlertError("Your room's name is not related to the floor.");
                }
            }

            if (roomTypeComboBox.getValue() != null && !((String) roomTypeComboBox.getValue()).isEmpty()) {
                if (roomTypeComboBox.getValue().equals("1-bedroom")) {
                    newRoom.setRoomType("1-bedroom");
                } else if (roomTypeComboBox.getValue().equals("2-bedroom")) {
                    newRoom.setRoomType("2-bedroom");
                }
            }

            if (newRoom.getBuildingName() != null && newRoom.getRoomName() != null && newRoom.getFloor() != null && newRoom.getRoomType() != null) {
                if (!buildings.checkHasSameBuildingName(newRoom.getBuildingName())) {
                    buildings.addBuildings(newRoomList);
                    newRoomList.addRooms(newRoom);

                    selectBuildingComboBox.getItems().add(newRoom.getBuildingName());
                    buildingComboBox.getItems().add(newRoom.getBuildingName());
                } else {
                    newRoomList.addRooms(newRoom);
                }
            } else if (newRoom.checkRoomNameRelatedToFloor(room.getText())) {
                if (newRoomList.checkRoomNameAvailable(room.getText(), newRoom.getBuildingName())) {
                    createAlertError("Please fill out all information.");
                }
            }
        }

        if (newOrSelected.getText().equals("Selected Room")) {
            selectedRoom.setResident1(resident1.getText());
            selectedRoom.setResident2(resident2.getText());
        }

        buildingDatasource.setBuildingsData(buildings);
        clearSelected();
        showListData();
        roomListTable.refresh();
        roomListTable.getSelectionModel().clearSelection();
    }

}
