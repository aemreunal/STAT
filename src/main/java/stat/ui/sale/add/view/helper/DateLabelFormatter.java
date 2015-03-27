package stat.ui.sale.add.view.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    // Date format via: http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    private String           datePattern   = "EEE, dd MMMMM yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
