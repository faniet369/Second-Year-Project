package lettery.models;

import java.util.ArrayList;

public class BuildingList {
    private ArrayList<Building> buildings;
    private Building currentBuilding;

    public BuildingList() {
        buildings = new ArrayList<>();
    }

    public void addBuildings(Building building) {
        buildings.add(building);
    }

    public boolean checkHasSameBuildingName(String buildingName) {
        for (Building building: buildings) {
            if (building.getBuildingName().equals(buildingName)) {
                currentBuilding = building;
                return true;
            }
        }
        currentBuilding = null;
        return false;
    }

    public Room getThisRoom(String buildingName, String roomName) {
        Room currentRoom = null;

        if (checkHasSameBuildingName(buildingName)) {
            if (currentBuilding.checkHasSameRoomName(roomName)) {
                currentRoom = currentBuilding.getCurrentRoom();
            }
        }
        return currentRoom;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public ArrayList<Building> getBuildingList() {
        return buildings;
    }
}
