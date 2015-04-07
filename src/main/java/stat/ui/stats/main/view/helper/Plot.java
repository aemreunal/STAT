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

public class Plot {
    private static final double MIN_GAP_BETWEEN_CLUSTERS = 0.2;
    private static final double DEFAULT_INTERVAL         = 0.1;

    private final JFrame                                                     plotWindow;
    private final Plot2DPanel                                                plotPanel;
    private final LinkedHashMap<Integer, LinkedHashMap<Integer, BigDecimal>> yearsToSalesBreakdowns;
    private       int                                                        numClusters;

    private double interval = DEFAULT_INTERVAL;
    private double[] xCoords;

    public Plot(LinkedHashMap<Integer, LinkedHashMap<Integer, BigDecimal>> yearsToSalesBreakdowns) {
        this.yearsToSalesBreakdowns = yearsToSalesBreakdowns;
        plotWindow = new JFrame("Stats");
        plotPanel = new Plot2DPanel();
        calculateClusters();
        createPlot();
        setPlotBounds();
        showWindow();
    }

    private void calculateClusters() {
        // Get number of clusters, which is 4 for quarterly breakdown and 12 for monthly breakdown
        numClusters = this.yearsToSalesBreakdowns.values().stream().findFirst().get().size();
        xCoords = new double[numClusters];
    }

    private void createPlot() {
        Set<Integer> years = yearsToSalesBreakdowns.keySet();
        calculateInterval(years);
        drawQuarters(years);
    }

    private void calculateInterval(Set<Integer> years) {
        if (years.size() > Math.round((1.0 - MIN_GAP_BETWEEN_CLUSTERS) * 10)) {
            // shorten interval so there is a gap between clusters of breakdowns
            interval = (1.0 - MIN_GAP_BETWEEN_CLUSTERS) / years.size();
        }
    }

    private void drawQuarters(Set<Integer> years) {
        int counter = 0;
        for (Integer year : years) {
            double[] salesOfInterval = yearsToSalesBreakdowns.get(year).values().stream()
                                                             .mapToDouble(BigDecimal::doubleValue).toArray();
            calculateXCoords(counter);
            plotPanel.addBarPlot("FY " + year, xCoords, salesOfInterval);
            counter++;
        }
    }

    private void calculateXCoords(double counter) {
        double offset = interval * counter;
        for (int i = 0; i < numClusters; i++) {
            xCoords[i] = i + offset;
        }
    }

    private void setPlotBounds() {
        // Change bounds of Axis 0 ($1) to show the interval from 0.0 ($2) up to numClusters ($3)
        plotPanel.setFixedBounds(0, 0.0, numClusters);
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
