package stat.ui;

import stat.ui.mainApp.view.ApplicationWindow;
import stat.ui.sale.add.view.helper.DateLabelFormatter;
import stat.ui.sale.helper.FilterFieldListener;

import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public abstract class Page extends JPanel {

    protected TableRowSorter    tableSorter;
    private   ApplicationWindow appWindow;

    public ApplicationWindow getApplicationWindow() {
        return appWindow;
    }

    public void setApplicationWindow(ApplicationWindow appWindow) {
        this.appWindow = appWindow;
    }

    protected abstract void initPage();

    protected void initFilters(int numCols) {
        JPanel filterHolder = new JPanel();
        filterHolder.setLayout(new GridLayout(1, numCols));

        for (int i = 0; i < numCols; i++) {
            createFilterFieldForCol(filterHolder, i);
        }

        add(filterHolder, BorderLayout.NORTH);
    }

    protected void createFilterFieldForCol(JPanel parentPanel, int columnIndex) {
        JTextField filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new FilterFieldListener(tableSorter, columnIndex));
        parentPanel.add(filterField);
    }

    public static JDatePickerImpl createDatePicker(UtilDateModel dateModel) {
        // JDatePicker creation via: http://stackoverflow.com/a/26794863/2246876
        dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        return datePicker;
    }

    public static void showPopup(Page page) {
        JFrame popupWindow = new JFrame();
        popupWindow.setContentPane(page);
        popupWindow.setSize(page.getMinimumSize());
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
    }
}
