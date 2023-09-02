package lettery.services;

import lettery.models.*;

import java.io.*;
import java.text.ParseException;

public class MailFileDatasource {
    private FileDatasource fileDatasource;
    private MailList mailList;

    public MailFileDatasource(String fileDirectoryName, String fileName) {
        fileDatasource = new FileDatasource(fileDirectoryName, fileName);
    }

    private Room getThisRoom(String buildingName, String roomName) {
        BuildingFileDatasource buildingDatasource = new BuildingFileDatasource("data", "buildings.csv");
        BuildingList buildings = buildingDatasource.getBuildingsData();
        return buildings.getThisRoom(buildingName, roomName);
    }

    private void readData() throws IOException, ParseException {
        Mail mail;
        lettery.models.Date date = new lettery.models.Date();
        File file = new File(fileDatasource.getFilePath());
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            switch (data[0].trim()) {
                case "Letter":
                    mail = new Mail(data[1].trim(), data[2].trim(), getThisRoom(data[3].trim(), data[4].trim()), data[5].trim(), date.parseDate(data[6].trim()));
                    mailList.addMail(mail);
                    break;
                case "Document":
                    mail = new Mail(data[1].trim(), data[2].trim(), getThisRoom(data[3].trim(), data[4].trim()), data[5].trim(), date.parseDate(data[6].trim()), data[7].trim());
                    mailList.addMail(mail);
                    break;
                case "Parcel":
                    mail = new Mail(data[1].trim(), data[2].trim(), getThisRoom(data[3].trim(), data[4].trim()), data[5].trim(), date.parseDate(data[6].trim()), data[7].trim(), data[8].trim());
                    mailList.addMail(mail);
                    break;
            }
        }
        reader.close();
    }

    public MailList getMailsData() {
        try {
            mailList = new MailList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(fileDatasource.getFileName() + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + fileDatasource.getFileName());
        } catch (ParseException e) {
            System.err.println("Can't parse this date from " + fileDatasource.getFileName() + " to string");
        }

        return mailList;
    }

    public void setMailsData(MailList mailList) {
        File file = new File(fileDatasource.getFilePath());
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line = "";
            for (Mail mail : mailList.getMails()) {
                switch (mail.getMailType()) {
                    case "Letter":
                        line = mail.getMailType() + ","
                                + mail.getSender().getName() + ","
                                + mail.getReceiver().getName() + ","
                                + mail.getReceiver().getRoom().getBuildingName() + ","
                                + mail.getReceiver().getRoom().getRoomName() + ","
                                + mail.getSize() + ","
                                + mail.getStringReceiveDate();
                        break;
                    case "Document":
                        line = mail.getMailType() + ","
                                + mail.getSender().getName() + ","
                                + mail.getReceiver().getName() + ","
                                + mail.getReceiver().getRoom().getBuildingName() + ","
                                + mail.getReceiver().getRoom().getRoomName() + ","
                                + mail.getSize() + ","
                                + mail.getStringReceiveDate() + ","
                                + mail.getImportance();

                        break;
                    case "Parcel":
                        line = mail.getMailType() + ","
                                + mail.getSender().getName() + ","
                                + mail.getReceiver().getName() + ","
                                + mail.getReceiver().getRoom().getBuildingName() + ","
                                + mail.getReceiver().getRoom().getRoomName() + ","
                                + mail.getSize() + ","
                                + mail.getStringReceiveDate() + ","
                                + mail.getCarrier() + ","
                                + mail.getTrackingNumber();
                        break;
                }
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + fileDatasource.getFilePath());
        }
    }
}
