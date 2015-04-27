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

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
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

    public void yearSelectionChanged(String chartType) {
        switch (chartType) {
            case "Month":
                createMonthlySalesChart();
                break;
            case "Quarter":
                createQuarterlySalesChart();
                break;
            case "Year":
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
        return saleService.getAllSales().stream()
                          .map(sale -> sale.getDate().getYear() + 1900)
                          .sorted()
                          .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
