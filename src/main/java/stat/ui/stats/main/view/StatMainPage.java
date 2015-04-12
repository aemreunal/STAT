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
import stat.ui.stats.main.view.helper.ChartFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatMainPage extends Page {

    @Autowired
    private StatController statController;

    @Autowired
    private ChartFactory chartFactory;

    private JPanel                      graphHolder  = new JPanel();
    private JPanel                      optionHolder = new JPanel();
    private LinkedHashSet<JRadioButton> radioButtons = new LinkedHashSet<>();
    private JList<Integer>              yearList     = new JList<>();

    private ActionListener radioButtonListener;
    private LinkedHashSet<Integer> listOfSalesYears;

    public StatMainPage() {
        radioButtonListener = new BreakdownSelectionListener();
        initPage();
        initGraphHolder();
        initOptionHolder();
        initRadioHolder();
        initYearList();
    }

    @Override
    protected void initPage() {
        setLayout(new GridBagLayout());
    }

    private void initGraphHolder() {
        graphHolder.setBackground(Color.WHITE);
        graphHolder.setLayout(new BorderLayout());

        GridBagConstraints graphConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0, 5, 1);
        add(graphHolder, graphConstraints);
    }

    private void initOptionHolder() {
        optionHolder.setLayout(new GridBagLayout());

        GridBagConstraints optionConstraints = createConstraints(GridBagConstraints.WEST, GridBagConstraints.BOTH, 1, 0, 0, 0);
        add(optionHolder, optionConstraints);
    }

    private void initRadioHolder() {
        JPanel radioHolder = new JPanel();
        radioHolder.setLayout(new GridLayout(3, 1));
        ButtonGroup radioGroup = new ButtonGroup();
        radioButtons = new LinkedHashSet<>();

        createRadioButton(radioGroup, radioHolder, "Month");
        createRadioButton(radioGroup, radioHolder, "Quarter");
        createRadioButton(radioGroup, radioHolder, "Year");

        GridBagConstraints radioConstraints = createConstraints(GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0, 1, 1);
        optionHolder.add(radioHolder, radioConstraints);
    }

    private void createRadioButton(ButtonGroup group, JPanel holderPanel, String label) {
        JRadioButton radioButton = new JRadioButton(label);
        radioButton.addActionListener(radioButtonListener);
        group.add(radioButton);
        radioButtons.add(radioButton);
        holderPanel.add(radioButton);
    }

    private void initYearList() {
        // Make list items individually selectable
        yearList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        yearList.addListSelectionListener(new YearListListener());
        yearList.setFont(new Font("Arial", Font.PLAIN, 18));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) yearList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints holderConstraints = createConstraints(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 1, 1, 7);
        optionHolder.add(new JScrollPane(yearList), holderConstraints);
    }

    @Override
    public void refresh() {
        refreshYearList();
        validate();
    }

    public void refreshYearList() {
        LinkedHashSet<Integer> salesYears = statController.getListOfSalesYears();
        if (!salesYears.equals(listOfSalesYears)) {
            this.listOfSalesYears = salesYears;
            yearList.setListData(this.listOfSalesYears.toArray(new Integer[this.listOfSalesYears.size()]));
        }
    }

    public void showMonthlySalesChart(Map<Integer, Map<Integer, BigDecimal>> yearsVsMonthlySales) {
        setChart(chartFactory.createMonthlyChart(yearsVsMonthlySales));
    }

    public void showQuarterlySalesChart(Map<Integer, Map<Integer, BigDecimal>> yearsVsQuarterlySales) {
        setChart(chartFactory.createQuarterlyChart(yearsVsQuarterlySales));
    }

    public void showYearlySalesChart(LinkedHashMap<Integer, BigDecimal> yearsVsSales) {
        setChart(chartFactory.createYearlyChart(yearsVsSales));
    }

    public void setChart(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        graphHolder.removeAll();
        graphHolder.add(panel);
        validate();
    }

    private class BreakdownSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            statController.yearSelectionChanged(getSelectedType());
        }
    }

    private class YearListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String selectedType = getSelectedType();
            if (selectedType != null) {
                statController.yearSelectionChanged(selectedType);
            }
        }
    }

    private String getSelectedType() {
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return null;
    }

    public List<Integer> getSelectedYears() {
        return yearList.getSelectedValuesList();
    }

    private GridBagConstraints createConstraints(int anchor, int fill, int gridX, int gridY, int weightX, int weightY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = anchor;
        constraints.fill = fill;
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        return constraints;
    }
}
