package lettery.services;

import lettery.models.Building;
import lettery.models.BuildingList;
import lettery.models.Room;
import java.io.*;

public class BuildingFileDatasource {
    private FileDatasource fileDatasource;
    private BuildingList buildings;

    public BuildingFileDatasource(String fileDirectoryName, String fileName) {
        fileDatasource = new FileDatasource(fileDirectoryName, fileName);
    }

    private void readData() throws IOException {
        Building roomList;
        File file = new File(fileDatasource.getFilePath());
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (!buildings.checkHasSameBuildingName(data[0].trim())) {
                roomList = new Building(data[0].trim());
                buildings.addBuildings(roomList);
            }
            else {
                roomList = buildings.getCurrentBuilding();
            }
            Room room = new Room(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim());
            room.setResident1(data[4].trim());
            room.setResident2(data[5].trim());
            roomList.addRooms(room);
        }
        reader.close();
    }

    public BuildingList getBuildingsData() {
        try {
            buildings = new BuildingList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(fileDatasource.getFileName() + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + fileDatasource.getFileName());
        }
        return buildings;
    }

    public void setBuildingsData(BuildingList buildings) {
        File file = new File(fileDatasource.getFilePath());
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line;
            for (Building building: buildings.getBuildingList()) {
                for (Room room: building.getRoomList()) {
                    line = building.getBuildingName() + ","
                            + room.getRoomName() + ","
                            + room.getRoomType() + ","
                            + room.getFloor() + ","
                            + room.getResident1() + ","
                            + room.getResident2() ;
                    writer.append(line);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + fileDatasource.getFilePath());
        }
    }
}
