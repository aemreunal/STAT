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

import org.jdatepicker.impl.JDatePickerImpl;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import stat.ui.Page;

import java.awt.*;
import java.util.LinkedHashSet;
import javax.swing.*;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatMainPage extends Page {

    private JPanel graphHolder;
    private JPanel optionHolder;

    public StatMainPage() {
        initPage();
        initGraphHolder();
        initOptionHolder();
        initRadioHolder();
       // initDateHolder();
    }

    @Override
    protected void initPage() {
        setLayout(new GridBagLayout());
    }

    private void initGraphHolder() {
        graphHolder = new JPanel();
        graphHolder.setBackground(Color.YELLOW);
        graphHolder.setLayout(new BorderLayout());

        GridBagConstraints graphConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0, 5, 1);
        add(graphHolder, graphConstraints);
    }

    private void initOptionHolder() {
        optionHolder = new JPanel();
        optionHolder.setBackground(Color.RED);
        optionHolder.setLayout(new GridBagLayout());

        GridBagConstraints optionConstraints = createConstraints(GridBagConstraints.WEST, GridBagConstraints.BOTH, 1, 0, 0, 0);
        add(optionHolder, optionConstraints);
    }

    private void initRadioHolder() {
        JRadioButton monthRadio   = new JRadioButton("Month");
        JRadioButton quarterRadio = new JRadioButton("Quarter");
        JRadioButton yearRadio    = new JRadioButton("Year");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(monthRadio);
        radioGroup.add(quarterRadio);
        radioGroup.add(yearRadio);

        GridBagConstraints radioConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 1, 1);
        JPanel radioHolder = new JPanel();
        radioHolder.setLayout(new GridLayout(1, 3));
        radioHolder.add(monthRadio);
        radioHolder.add(quarterRadio);
        radioHolder.add(yearRadio);

        optionHolder.add(radioHolder, radioConstraints);
    }

    private void initDateHolder() {
        JPanel dateHolder = new JPanel();
        dateHolder.setLayout(new FlowLayout());

        JDatePickerImpl datePicker1 = Page.createDatePicker();
        datePicker1.setSize(datePicker1.getPreferredSize());

        JDatePickerImpl datePicker2 = Page.createDatePicker();
        datePicker2.setSize(datePicker2.getPreferredSize());

        JButton addButton = new JButton("ADD");
        addButton.setMinimumSize(new Dimension(250, 50));

        dateHolder.add(addButton);
        dateHolder.add(datePicker1);
        dateHolder.add(datePicker2);


        GridBagConstraints holderConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 1, 1, 7);
        optionHolder.add(dateHolder, holderConstraints);
    }

    private GridBagConstraints createConstraints(int anchor, int fill, int gridX, int gridY, int weightX, int weightY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor  = anchor;
        constraints.fill    = fill;
        constraints.gridx   = gridX;
        constraints.gridy   = gridY;
        constraints.weightx = weightX;
        constraints.weighty = weightY;

        return constraints;
    }

    public void initializeYears(LinkedHashSet<String> saleYears) {
        // TODO: implement
    }

    public void setChart(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        graphHolder.removeAll();
        graphHolder.add(panel);
    }

}