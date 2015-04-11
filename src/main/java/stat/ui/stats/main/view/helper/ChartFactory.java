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

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class ChartFactory {

    private static final String[] MONTHS = {"January", "February", "March",
                                            "April"  , "May"     , "June",
                                            "July"   , "August"  , "September",
                                            "October", "November", "December"};

    private static final String[] QUARTERS = {"Q1", "Q2", "Q3", "Q4"};

    public static JFreeChart createMonthChart(Map<String, int[]> salesMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            int[] sales = salesMap.get(yearName);
            for (int monthIndex = 0; monthIndex < MONTHS.length; monthIndex++) {
                dataset.addValue(sales[monthIndex], yearName, MONTHS[monthIndex]);
            }
        }
        return org.jfree.chart.ChartFactory.createBarChart("Monthly Sales", "Months", "#Sales", dataset);
    }

    public static JFreeChart createQuarterChart(Map<String, int[]> salesMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            int[] sales = salesMap.get(yearName);
            for (int quarterIndex = 0; quarterIndex < QUARTERS.length; quarterIndex++) {
                dataset.addValue(sales[quarterIndex], yearName, QUARTERS[quarterIndex]);
            }
        }
        return org.jfree.chart.ChartFactory.createBarChart("Quarterly Sales", "Quarters", "#Sales", dataset);
    }

    public static JFreeChart createYearChart(Map<String, Integer> salesMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            dataset.addValue(salesMap.get(yearName), yearName, yearName);
        }
        return org.jfree.chart.ChartFactory.createBarChart("Yearly Sales", "Years", "#Sales", dataset);
    }

}
