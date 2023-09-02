package lettery.models;

import java.util.ArrayList;
import java.util.Collections;

public class MailList {
    private ArrayList<Mail> mails;

    public MailList() {
        mails = new ArrayList<>();
    }

    public void addMail(Mail mail) {
        if (mail != null) {
            mails.add(mail);
        }
    }

    public void removeMail(Mail mail) {
        mails.remove(mail);
    }

    public void sortMails() {
        mails.sort(Collections.reverseOrder());
    }

    public ArrayList<Mail> getMails() {
        return mails;
    }

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }
}
