package stat.ui.stats.main;

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

import stat.domain.Sale;
import stat.service.SaleService;
import stat.ui.stats.main.view.StatMainPage;
import stat.ui.stats.main.view.helper.BreakdownType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Fiscal year definitions are based on those of <a href="http://en.wikipedia.org/wiki/Fiscal_year#United_States">USA</a>.
 */
@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatController {

    public static final int QUARTERS_PER_YEAR = 4;
    public static final int MONTHS_PER_YEAR   = 12;

    @Autowired
    private SaleService saleService;

    @Autowired
    private StatMainPage statMainPage;

    private Set<Sale> allSales;
    private Integer   firstYear;

    public void refreshSalesList() {
        allSales = saleService.getAllSales();
        Optional<Integer> firstYear = allSales.stream()
                                              .map(sale -> (sale.getDate().getYear() + 1900))
                                              .sorted()
                                              .findFirst();
        if (firstYear != null) {
            this.firstYear = firstYear.get();
        }
    }

    // FY 2015: 1 October 2014 - 30 September 2015
    private BigDecimal summarizeFiscalYear(int year) {
        return summarizeInterval(year - 1, 10, 1, year, 9, 30);
    }

    private BigDecimal summarizeFiscalQuarter(int year, int quarter) {
        // 4 quarters in a year
        switch (quarter % QUARTERS_PER_YEAR) {
            case 0:
                return summarizeFirstFiscalQuarter(year);
            case 1:
                return summarizeSecondFiscalQuarter(year);
            case 2:
                return summarizeThirdFiscalQuarter(year);
            case 3:
                return summarizeFourthFiscalQuarter(year);
            default:
                return new BigDecimal(0.0);
        }
    }

    // 1st quarter for FY 2015: 1 October 2014 – 31 December 2014
    private BigDecimal summarizeFirstFiscalQuarter(int year) {
        return summarizeInterval(year - 1, 10, 1, year - 1, 12, 31);
    }

    // 2nd quarter for FY 2015: 1 January 2015 – 31 March 2015
    private BigDecimal summarizeSecondFiscalQuarter(int year) {
        return summarizeInterval(year, 1, 1, year, 3, 31);
    }

    // 3rd quarter for FY 2015: 1 April 2015 – 30 June 2015
    private BigDecimal summarizeThirdFiscalQuarter(int year) {
        return summarizeInterval(year, 4, 1, year, 6, 30);
    }

    // 4th quarter for FY 2015: 1 July 2015 – 30 September 2015
    private BigDecimal summarizeFourthFiscalQuarter(int year) {
        return summarizeInterval(year, 7, 1, year, 9, 30);
    }

    private BigDecimal summarizeMonth(int year, int month) {
        return summarizeInterval(year, month, 1, year, month, getNumDaysInMonth(month));
    }

    // Months are returned as 0-11 from Date class.
    private int getNumDaysInMonth(int month) {
        // Before (and including) July (month = 6 at Date class), odd months are 31 days.
        // After (and including) August (month = 7 at Date class), even months are 31 days.
        int oddOrEvenSideOfYear = (month <= 6) ? 1 : 0;
        if (month % 2 == oddOrEvenSideOfYear) {
            return 31;
        } else {
            return 30;
        }
    }

    private BigDecimal summarizeInterval(int beginYear, int beginMonth, int beginDay, int endYear, int endMonth, int endDay) {
        // Dates must be created by subtracting 1900 from the year, as per Date constructor docs
        final Date from = new Date(beginYear - 1900, beginMonth, beginDay);
        final Date until = new Date(endYear - 1900, endMonth, endDay);
        return calculateTotalSalesForInterval(from, until);
    }

    private BigDecimal calculateTotalSalesForInterval(Date from, Date until) {
        LinkedHashSet<Sale> sales = saleService.searchForSales(null, from, until, null, null);
        BigDecimal totalSales = new BigDecimal(0.0);
        for (Sale sale : sales) {
            totalSales = totalSales.add(sale.getTotalPrice());
        }
        return totalSales;
    }

    public void yearSelectionChanged(BreakdownType breakdownType) {
        switch (breakdownType) {
            case MONTHLY:
                createMonthlySalesChart();
                break;
            case QUARTERLY:
                createQuarterlySalesChart();
                break;
            case YEARLY:
                createYearlySalesChart();
                break;
            default:
                break;
        }
    }

    private void createMonthlySalesChart() {
        Map<Integer, Map<Integer, BigDecimal>> yearsVsMonthlySales = new LinkedHashMap<>();
        for (Integer year : statMainPage.getSelectedYears()) {
            getYearAsMonths(yearsVsMonthlySales, year);
        }
        statMainPage.showMonthlySalesChart(yearsVsMonthlySales);
    }

    private void getYearAsMonths(Map<Integer, Map<Integer, BigDecimal>> map, Integer year) {
        LinkedHashMap<Integer, BigDecimal> breakdown = new LinkedHashMap<>();
        for (int i = 0; i < MONTHS_PER_YEAR; i++) {
            breakdown.put(i, summarizeMonth(year, i));
        }
        map.put(year, breakdown);
    }

    private void createQuarterlySalesChart() {
        Map<Integer, Map<Integer, BigDecimal>> yearsVsQuarterlySales = new LinkedHashMap<>();
        for (Integer year : statMainPage.getSelectedYears()) {
            getYearAsQuarters(yearsVsQuarterlySales, year);
        }
        statMainPage.showQuarterlySalesChart(yearsVsQuarterlySales);
    }

    private void getYearAsQuarters(Map<Integer, Map<Integer, BigDecimal>> map, Integer year) {
        LinkedHashMap<Integer, BigDecimal> breakdown = new LinkedHashMap<>();
        for (int i = 0; i < QUARTERS_PER_YEAR; i++) {
            breakdown.put(i, summarizeFiscalQuarter(year, i));
        }
        map.put(year, breakdown);
    }

    private void createYearlySalesChart() {
        LinkedHashMap<Integer, BigDecimal> yearsVsSales = new LinkedHashMap<>();
        for (Integer year : statMainPage.getSelectedYears()) {
            yearsVsSales.put(year, summarizeFiscalYear(year));
        }
        statMainPage.showYearlySalesChart(yearsVsSales);
    }

    public LinkedHashSet<Integer> getListOfSalesYears() {
        return allSales.stream()
                       .map(sale -> sale.getDate().getYear() + 1900)
                       .sorted()
                       .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    private int getQuarter(Date date) {
        if (date.getMonth() >= 9 && date.getMonth() <= 11) { // 1st quarter for FY 2015: 1 October 2014 – 31 December 2014
            return 0;
        } else if (date.getMonth() >= 0 && date.getMonth() <= 2) { // 2nd quarter for FY 2015: 1 January 2015 – 31 March 2015
            return 1;
        } else if (date.getMonth() >= 3 && date.getMonth() <= 5) { // 3rd quarter for FY 2015: 1 April 2015 – 30 June 2015
            return 2;
        } else if (date.getMonth() >= 6 && date.getMonth() <= 8) { // 4th quarter for FY 2015: 1 July 2015 – 30 September 2015
            return 3;
        } else { // N/A
            return -1;
        }
    }
    
    public void forecastButtonClicked(BreakdownType breakdownType) {
        if (firstYear == null) {
            statMainPage.showForecastResult(0, breakdownType.toTitleCasedString());
        }
        double[][] salesOfAllPeriods;
        switch (breakdownType) {
            case MONTHLY:
                salesOfAllPeriods = getSalesOfAllMonths();
                break;
            case QUARTERLY:
                salesOfAllPeriods = getSalesOfAllQuarters();
                break;
            case YEARLY:
                salesOfAllPeriods = getSalesOfAllYears();
                break;
            default:
                salesOfAllPeriods = getSalesOfAllYears();
                break;
        }
        statMainPage.showForecastResult(getPeriodForecast(salesOfAllPeriods), breakdownType.toTitleCasedString());
    }

    private double[][] getSalesOfAllMonths() {
        LinkedHashMap<Integer, BigDecimal> salesOfMonths = new LinkedHashMap<>();
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        allSales.stream()
                .filter(sale -> sale.getDate().getMonth() == thisMonth)
                .forEach(sale -> {
                    Integer period = sale.getDate().getYear() + 1900;
                    addSaleToMap(salesOfMonths, sale, period);
                });
        return getSalesAsForecastMatrix(salesOfMonths);
    }

    private double[][] getSalesOfAllQuarters() {
//        LinkedHashMap<Integer, BigDecimal> salesOfQuarters = new LinkedHashMap<>();
//        allSales.stream()
//                .forEach(sale -> {
//                    Integer period = 0; // getQuarterIndex(sale.getDate().getMonth());
//                    addSaleToMap(salesOfQuarters, sale, period);
//                });
//        return getSalesAsForecastMatrix(salesOfQuarters);
        return new double[0][0];
    }

    private double[][] getSalesOfAllYears() {
        LinkedHashMap<Integer, BigDecimal> salesOfYears = new LinkedHashMap<>();
        allSales.stream()
                .forEach(sale -> {
                    Integer period = sale.getDate().getYear() + 1900;
                    addSaleToMap(salesOfYears, sale, period);
                });
        return getSalesAsForecastMatrix(salesOfYears);
    }

    private double getPeriodForecast(double[][] salesOfAllPeriods) {
        SimpleRegression simpleRegression = new SimpleRegression(true);
        simpleRegression.clear();
        simpleRegression.addData(salesOfAllPeriods);
        return simpleRegression.predict(salesOfAllPeriods.length);
    }

    private double[][] getSalesAsForecastMatrix(LinkedHashMap<Integer, BigDecimal> salesOfAllPeriods) {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int numYears = thisYear - firstYear + 1;
        double[][] sales = new double[numYears][2];
        for (int yearIndex = 0; yearIndex < numYears; yearIndex++) {
            int year = firstYear + yearIndex;
            putSaleToMatrix(salesOfAllPeriods, sales, yearIndex, year);
        }
        return sales;
    }

    private void putSaleToMatrix(LinkedHashMap<Integer, BigDecimal> salesOfAllPeriods, double[][] sales, int yearIndex, int period) {
        BigDecimal salesOfThisPeriod = new BigDecimal(0);
        if (salesOfAllPeriods.containsKey(period)) {
            salesOfThisPeriod = salesOfAllPeriods.get(period);
        }
        sales[yearIndex][0] = yearIndex;
        sales[yearIndex][1] = salesOfThisPeriod.doubleValue();
    }

    private void addSaleToMap(LinkedHashMap<Integer, BigDecimal> salesMap, Sale sale, Integer key) {
        BigDecimal amount = sale.getTotalPrice();
        if (salesMap.containsKey(key)) {
            BigDecimal newAmount = salesMap.get(key).add(amount);
            salesMap.put(key, newAmount);
        } else {
            salesMap.put(key, amount);
        }
    }
}
