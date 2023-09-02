package lettery.services;

import lettery.models.*;

import java.io.*;
import java.text.ParseException;

public class AccountFileDatasource{
    private FileDatasource fileDatasource;
    private AccountList accounts;
    private Account currentAccount;

    public AccountFileDatasource(String fileDirectoryName, String fileName) {
        fileDatasource = new FileDatasource(fileDirectoryName, fileName);
    }

    public void readData() throws IOException, ParseException {
        lettery.models.Date date = new lettery.models.Date();
        File file = new File(fileDatasource.getFilePath());
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        String[] data = line.split(",");
        currentAccount = new Account(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), date.parseDate(data[4].trim()));
        while ((line = reader.readLine()) != null) {
            data = line.split(",");
            switch (data[2].trim()) {
                case "admin":
                    Account admin = new Account(data[0].trim(), data[1].trim());
                    accounts.addAccounts(admin);
                    break;
                case "officer":
                    Account officer = new Account(data[0].trim(), data[1].trim(), data[3].trim());
                    officer.setLastLogin(date.parseDate(data[4].trim()));
                    accounts.addAccounts(officer);
                    break;
            }
        }
        reader.close();
    }

    public AccountList getAccountsData() {
        try {
            accounts = new AccountList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(fileDatasource.getFileName() + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + fileDatasource.getFileName());
        }  catch (ParseException e) {
            System.err.println("Can't parse this date from " + fileDatasource.getFileName() + " to string");
        }

        return accounts;
    }

    public Account getCurrentAccountData() {
        return currentAccount;
    }

    public void setAccountsData(AccountList accounts, Account currentAccount) {
        File file = new File(fileDatasource.getFilePath());
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line;
            line = currentAccount.getUsername() + ","
                    + currentAccount.getPassword() + ","
                    + currentAccount.getRole() + ","
                    + currentAccount.getName() + ","
                    + currentAccount.getStringLastLogin();
            writer.append(line);
            for (Account acc: accounts.getAccountList()) {
                writer.newLine();
                switch (acc.getRole()) {
                    case "admin":
                        line = acc.getUsername() + ","
                                + acc.getPassword() + ","
                                + acc.getRole();
                        break;
                    case "officer":
                        line = acc.getUsername() + ","
                                + acc.getPassword() + ","
                                + acc.getRole() + ","
                                + acc.getName() + ","
                                + acc.getStringLastLogin();
                        break;
                }
                writer.append(line);
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + fileDatasource.getFilePath());
        }
    }

}
