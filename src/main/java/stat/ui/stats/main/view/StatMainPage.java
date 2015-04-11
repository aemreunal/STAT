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

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import stat.ui.Page;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.*;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.ui.stats.main.StatController;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatMainPage extends Page {

    @Autowired
    private StatController statController;

    private JPanel graphHolder;
    private JPanel optionHolder;
    private LinkedHashSet<JCheckBox> checkBoxes;
    private LinkedHashSet<JRadioButton> radioButtons;

    public StatMainPage() {
        initPage();
        initGraphHolder();
        initOptionHolder();
        initRadioHolder();
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

        monthRadio.addActionListener(getRadioButtonListener());
        quarterRadio.addActionListener(getRadioButtonListener());
        yearRadio.addActionListener(getRadioButtonListener());

        radioButtons = new LinkedHashSet<JRadioButton>();
        radioButtons.add(monthRadio);
        radioButtons.add(quarterRadio);
        radioButtons.add(yearRadio);

        GridBagConstraints radioConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 1, 1);
        JPanel radioHolder = new JPanel();
        radioHolder.setLayout(new GridLayout(1, 3));
        radioHolder.add(monthRadio);
        radioHolder.add(quarterRadio);
        radioHolder.add(yearRadio);

        optionHolder.add(radioHolder, radioConstraints);
    }

    private ActionListener getRadioButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statController.yearsSelected(getSelectedYears(), getSelectedType());
                System.out.println(getSelectedType());
            }
        };
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
        JPanel yearHolder = new JPanel();
        yearHolder.setLayout(null);

        checkBoxes = new LinkedHashSet<JCheckBox>();
        int gap = 30, count = 0;
        Rectangle rect =  new Rectangle(5, 10, 50, 20);
        for (String yearName : saleYears) {
            JCheckBox yearBox = new JCheckBox(yearName);
            yearBox.setSize(rect.getSize());
            yearBox.setLocation((int) rect.getX(), (int) (rect.getY() + (count * gap)));
            yearBox.addActionListener(getYearActionListener());
            checkBoxes.add(yearBox);
            yearHolder.add(yearBox);
            count ++;
        }
        //TODO: add scroll feature to dateHolder
        GridBagConstraints holderConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 1, 1, 7);
        optionHolder.add(yearHolder, holderConstraints);
    }

    private ActionListener getYearActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = getSelectedType();
                if (selectedType != null) {
                    statController.yearsSelected(getSelectedYears(), selectedType);
                    System.out.println(selectedType);
                }
            }
        };
    }

    private String getSelectedType() {
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.isSelected())
                return radioButton.getText();
        }
        return null;
    }

    private LinkedHashSet<String> getSelectedYears() {
        LinkedHashSet<String> yearsSelected = new LinkedHashSet<String>();
        for (JCheckBox yearBox : checkBoxes) {
            if (yearBox.isSelected())
                yearsSelected.add(yearBox.getText());
        }
        return yearsSelected;
    }

    public void setChart(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        graphHolder.removeAll();
        graphHolder.add(panel);
    }

}