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

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.math.BigDecimal;
import java.util.Map;

public class ChartFactory {

    private static final String[] MONTHS = {"January", "February", "March",
                                            "April"  , "May"     , "June",
                                            "July"   , "August"  , "September",
                                            "October", "November", "December"};

    private static final String[] QUARTERS = {"Q1", "Q2", "Q3", "Q4"};

    public static JFreeChart createMonthChart(Map<String, Map<Integer, BigDecimal>> salesMap) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            Map<Integer, BigDecimal> sales = salesMap.get(yearName);
            for (Integer monthIndex : sales.keySet()){
                dataSet.addValue(sales.get(monthIndex), yearName, MONTHS[monthIndex]);
            }
        }
        return org.jfree.chart.ChartFactory.createBarChart("Monthly Sales", "Months", "#Sales", dataSet);
    }

    public static JFreeChart createQuarterChart(Map<String, Map<Integer, BigDecimal>> salesMap) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            Map<Integer, BigDecimal> sales = salesMap.get(yearName);
            for (Integer quarterIndex : sales.keySet()){
                dataSet.addValue(sales.get(quarterIndex), yearName, QUARTERS[quarterIndex]);
            }
        }
        return org.jfree.chart.ChartFactory.createBarChart("Quarterly Sales", "Quarters", "#Sales", dataSet);
    }

    public static JFreeChart createYearChart(Map<String, Integer> salesMap) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            dataSet.addValue(salesMap.get(yearName), yearName, yearName);
        }
        return org.jfree.chart.ChartFactory.createBarChart("Yearly Sales", "Years", "#Sales", dataSet);
    }

}
