package lettery.models;

import java.util.ArrayList;

public class Building {
    private String buildingName;
    private ArrayList<Room> rooms;
    private Room currentRoom;

    public Building(String buildingName) {
        this.buildingName = buildingName;
        rooms = new ArrayList<>();
    }

    public boolean checkRoomNameAvailable(String roomName, String buildingName) {
        if (buildingName.equals(this.buildingName)) {
            for (Room room : rooms) {
                if (room.getRoomName().equals(roomName)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkHasSameRoomName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                currentRoom = room;
                return true;
            }
        }
        currentRoom = null;
        return false;
    }

    public void addRooms(Room room) {
        rooms.add(room);
    }

    public ArrayList<Room> getRoomList() {
        return rooms;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
