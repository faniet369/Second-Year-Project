package lettery.models;

public class Room {
    private String buildingName;
    private String roomName;
    private String roomType;
    private String floor;
    private String resident1 = "-";
    private String resident2 = "-";

    public Room(String buildingName, String roomName, String roomType, String floor) {
        this.buildingName = buildingName;
        this.roomName = roomName;
        this.roomType = roomType;
        this.floor = floor;
    }

    public Room() {}

    public boolean checkRoomNameRelatedToFloor(String roomName) {
        if (floor != null && roomName != null) {
            return floor.equals(roomName.substring(0, floor.length()));
        }
        return false;
    }

    public boolean checkHasSameResident(String residentName) {
        return resident1.equals(residentName) || resident2.equals(residentName);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getResident1() {
        return resident1;
    }

    public void setResident1(String resident1) {
        if (!resident1.isEmpty()) {
            this.resident1 = resident1;
        }
        else {
            this.resident1 = "-";
        }
    }

    public String getResident2() {
        return resident2;
    }

    public void setResident2(String resident2) {
        if (!resident2.isEmpty()) {
            this.resident2 = resident2;
        }
        else {
            this.resident2 = "-";
        }
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public String toString() {
        return "Room{" +
                "buildingName='" + buildingName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", floor='" + floor + '\'' +
                ", resident1='" + resident1 + '\'' +
                ", resident2='" + resident2 + '\'' +
                '}';
    }
}
