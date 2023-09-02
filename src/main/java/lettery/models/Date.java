package lettery.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {
    private SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm");

    public String formatDate(java.util.Date date) {
        return dateAndTimeFormat.format(date);
    }

    public java.util.Date parseDate(String date) throws ParseException {
        return dateAndTimeFormat.parse(date);
    }
}
