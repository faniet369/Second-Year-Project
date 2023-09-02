package lettery.models;

import java.util.Date;

public class Mail implements Comparable{
    private Person sender;
    private Person receiver;
    private String size;
    private Date receiveDate;
    private String importance;
    private String carrier;
    private String trackingNumber;
    private String mailType;

    public Mail(String senderName, String receiverName, Room room, String size, Date receiveDate) {
        sender = new Person(senderName);
        receiver = new Person(receiverName, room);
        this.size = size;
        this.receiveDate = receiveDate;
        mailType = "Letter";
    }

    public Mail(String senderName, String receiverName, Room room, String size, Date receiveDate, String importance) {
        sender = new Person(senderName);
        receiver = new Person(receiverName, room);
        this.size = size;
        this.receiveDate = receiveDate;
        this.importance = importance;
        mailType = "Document";
    }

    public Mail(String senderName, String receiverName, Room room, String size, Date receiveDate, String carrier, String trackingNumber) {
        sender = new Person(senderName);
        receiver = new Person(receiverName, room);
        this.size = size;
        this.receiveDate = receiveDate;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        mailType = "Parcel";
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStringReceiveDate() {
        lettery.models.Date date = new lettery.models.Date();
        return date.formatDate(receiveDate);
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    @Override
    public int compareTo(Object o) {
        Mail other = (Mail) o;
        return Integer.compare(receiveDate.compareTo(other.getReceiveDate()), 0);
    }
}
