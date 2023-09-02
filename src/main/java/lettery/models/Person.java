package lettery.models;

public class Person {
    private String name;
    private Room room;

    public Person(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
