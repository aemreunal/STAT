package stat.ui.stats.main.view;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.ui.Page;
import stat.ui.stats.main.StatController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatMainPage extends Page {

    @Autowired
    private StatController statController;

    private UtilDateModel dateOneModel;
    private UtilDateModel dateTwoModel;

    private JButton summarizeMonthButton;
    private JButton compareMonthButton;
    private JButton summarizeQuarterButton;
    private JButton compareQuarterButton;
    private JButton summarizeYearButton;
    private JButton compareYearButton;

    private StatPageButtonListener buttonListener;

    public StatMainPage() {
        buttonListener = new StatPageButtonListener();
        initPage();
        initDateFields();
        initLabels();
        initButtons();
    }

    @Override
    protected void initPage() {
        setLayout(null);
    }

    private void initDateFields() {
        initDate1Field();
        initDate2Field();
    }

    private void initDate1Field() {
        JLabel dateLabel1 = new JLabel("Date 1 :");
        dateLabel1.setBounds(98, 106, 46, 14);
        add(dateLabel1);

        JDatePickerImpl datePicker = createDatePicker();
        dateOneModel = (UtilDateModel) datePicker.getModel();
        datePicker.setLocation(98, 131);
        datePicker.setSize(datePicker.getPreferredSize());
        add(datePicker);
    }

    private void initDate2Field() {
        JLabel dateLabel2 = new JLabel("Date 2 :");
        dateLabel2.setBounds(496, 106, 46, 14);
        add(dateLabel2);

        JDatePickerImpl datePicker = createDatePicker();
        dateTwoModel = (UtilDateModel) datePicker.getModel();
        datePicker.setLocation(496, 131);
        datePicker.setSize(datePicker.getPreferredSize());
        add(datePicker);
    }

    private void initLabels() {
        JLabel summariseLabel = new JLabel("Summarise :");
        summariseLabel.setBounds(138, 219, 75, 14);
        add(summariseLabel);

        JLabel compareLabel = new JLabel("Compare :");
        compareLabel.setBounds(536, 219, 75, 14);
        add(compareLabel);
    }

    private void initButtons() {
        initMonthButtons();
        initQuarterButtons();
        initYearButtons();
    }

    private void initMonthButtons() {
        summarizeMonthButton = new JButton("Month");
        summarizeMonthButton.setBounds(138, 255, 89, 23);
        summarizeMonthButton.addActionListener(buttonListener);
        add(summarizeMonthButton);

        compareMonthButton = new JButton("Month");
        compareMonthButton.setBounds(536, 255, 89, 23);
        compareMonthButton.addActionListener(buttonListener);
        add(compareMonthButton);
    }

    private void initQuarterButtons() {
        summarizeQuarterButton = new JButton("Quarter");
        summarizeQuarterButton.setBounds(138, 301, 89, 23);
        summarizeQuarterButton.addActionListener(buttonListener);
        add(summarizeQuarterButton);

        compareQuarterButton = new JButton("Quarter");
        compareQuarterButton.setBounds(536, 301, 89, 23);
        compareQuarterButton.addActionListener(buttonListener);
        add(compareQuarterButton);
    }

    private void initYearButtons() {
        summarizeYearButton = new JButton("Year");
        summarizeYearButton.setBounds(138, 350, 89, 23);
        summarizeYearButton.addActionListener(buttonListener);
        add(summarizeYearButton);

        compareYearButton = new JButton("Year");
        compareYearButton.setBounds(536, 350, 89, 23);
        compareYearButton.addActionListener(buttonListener);
        add(compareYearButton);
    }

    private class StatPageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(summarizeMonthButton)) {
                //TODO : implement
            } else if (sourceOfAction.equals(summarizeQuarterButton)) {
                //TODO : implement
            } else if (sourceOfAction.equals(summarizeYearButton)) {
                //TODO : implement
            } else if (sourceOfAction.equals(compareMonthButton)) {
                //TODO : implement
            } else if (sourceOfAction.equals(compareQuarterButton)) {
                //TODO : implement
            } else if (sourceOfAction.equals(compareYearButton)) {
                //TODO : implement
            }
        }
    }

}
