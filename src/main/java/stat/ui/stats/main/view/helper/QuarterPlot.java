package stat.ui.stats.main.view.helper;

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


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.*;
import org.math.plot.Plot2DPanel;

public class QuarterPlot {
    private static final double MIN_GAP_BETWEEN_CLUSTERS = 0.2;
    private static final double DEFAULT_INTERVAL         = 0.1;

    private final JFrame      plotWindow;
    private final Plot2DPanel plotPanel;

    private double interval = DEFAULT_INTERVAL;

    public QuarterPlot(LinkedHashMap<Integer, LinkedHashMap<Integer, BigDecimal>> quartersOfAllYears) {
        plotPanel = new Plot2DPanel();
        plotWindow = new JFrame("Stats");
        createPlot(quartersOfAllYears);
        showWindow();
    }

    private void createPlot(LinkedHashMap<Integer, LinkedHashMap<Integer, BigDecimal>> quartersOfAllYears) {
        Set<Integer> years = quartersOfAllYears.keySet();
        calculateInterval(years);
        drawQuarters(years, quartersOfAllYears);
    }

    private void calculateInterval(Set<Integer> years) {
        if (years.size() > Math.round((1.0 - MIN_GAP_BETWEEN_CLUSTERS) * 10)) {
            // shorten interval so there is a gap between clusters of quarters
            interval = (1.0 - MIN_GAP_BETWEEN_CLUSTERS) / years.size();
        }
    }

    private void drawQuarters(Set<Integer> years, LinkedHashMap<Integer, LinkedHashMap<Integer, BigDecimal>> quartersOfAllYears) {
        int counter = 0;
        for (Integer year : years) {
            double[] salesOfQuarter = quartersOfAllYears.get(year).values().stream()
                                                        .mapToDouble(BigDecimal::doubleValue).toArray();
            plotPanel.addBarPlot("FY " + year, getXCoordinates(counter), salesOfQuarter);
            counter++;
        }
    }

    private double[] getXCoordinates(double counter) {
        double offset = interval * counter;
        return new double[] { 0.0 + offset, 1.0 + offset, 2.0 + offset, 3.0 + offset };
    }

    private void showWindow() {
        plotWindow.setContentPane(plotPanel);
        plotWindow.setSize(600, 500);
        plotWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        plotWindow.setVisible(true);
    }

    public void dispose() {
        plotWindow.setVisible(false);
        plotWindow.dispose();
    }
}
