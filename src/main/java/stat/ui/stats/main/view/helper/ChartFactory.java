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
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

import java.awt.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.jfree.chart.ChartFactory.createBarChart;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ChartFactory {
    private static final String[] MONTHS   = { "January", "February", "March",
                                               "April",   "May",      "June",
                                               "July",    "August",   "September",
                                               "October", "November", "December" };

    private static final String[] QUARTERS = { "1 (01/10-31/12)", "2 (01/01-31/03)",
                                               "3 (01/04-30/06)", "4 (01/07-30/09)" };

    public JFreeChart createMonthlyChart(Map<Integer, Map<Integer, BigDecimal>> salesMap) {
        JFreeChart barChart = createBarChart("Monthly Revenues", "Months", "Revenues", getDataSet(salesMap, MONTHS));
        flattenChart(barChart, false);
        return barChart;
    }

    public JFreeChart createQuarterlyChart(Map<Integer, Map<Integer, BigDecimal>> salesMap) {
        JFreeChart barChart = createBarChart("Quarterly Revenues", "Quarters", "Revenues", getDataSet(salesMap, QUARTERS));
        flattenChart(barChart, false);
        return barChart;
    }

    private DefaultCategoryDataset getDataSet(Map<Integer, Map<Integer, BigDecimal>> salesMap, String[] columnNames) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (Integer year : salesMap.keySet()) {
            Map<Integer, BigDecimal> sales = salesMap.get(year);
            for (Integer breakdownIndex : sales.keySet()) {
                dataSet.addValue(sales.get(breakdownIndex), year, columnNames[breakdownIndex]);
            }
        }
        return dataSet;
    }

    public JFreeChart createYearlyChart(LinkedHashMap<Integer, BigDecimal> salesMap) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        Integer rowKey = 0;
        for (Integer year : salesMap.keySet()) {
            dataSet.addValue(salesMap.get(year), rowKey, year);
        }
        JFreeChart barChart = createBarChart("Yearly Revenues", "Years", "Revenues", dataSet);
        flattenChart(barChart, true);
        return barChart;
    }

    private void flattenChart(JFreeChart barChart, boolean removeLegend) {
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setBackgroundPaint(new Color(221, 223, 238));
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setMaximumBarWidth(0.25);
        renderer.setGradientPaintTransformer(null);
        renderer.setBarPainter(new StandardBarPainter());
        if (removeLegend) {
            barChart.removeLegend();
        }
    }
}
