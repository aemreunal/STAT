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

    public static JFreeChart createMonthlyChart(Map<String, int[]> salesMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (String yearName : salesMap.keySet()) {
            int[] sales = salesMap.get(yearName);
            for (int monthIndex = 0; monthIndex < MONTHS.length; monthIndex++) {
                dataset.addValue(sales[monthIndex], yearName, MONTHS[monthIndex]);
            }
        }
        return org.jfree.chart.ChartFactory.createBarChart("Monthly Sales", "Months", "#Sales", dataset);
    }

}
