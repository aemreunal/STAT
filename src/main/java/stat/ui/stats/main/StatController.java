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
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.domain.Sale;
import stat.service.SaleService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.ui.stats.main.view.StatMainPage;

/**
 * Fiscal year definitions are based on those of
 * <a href="http://en.wikipedia.org/wiki/Fiscal_year#United_States">USA</a>.
 */
@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class StatController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private StatMainPage statMainPage;

    public void summarizeYearButtonClicked(int year) {
        String title = "Summary of " + getDateFormat(year, 1, 1, "YYYY");
        String message = "Total Revenue: " + summarizeFiscalYear(year).toPlainString();
        statMainPage.showResult(message, title);
    }

    public void summarizeQuarterButtonClicked(int year) {
        String title = "Summary of " + getDateFormat(year, 1, 1, "YYYY");
        String message = "Total revenue in first quarter: " + summarizeFirstFiscalQuarter(year).toPlainString() +
                         "\nTotal revenue in second quarter: " + summarizeSecondFiscalQuarter(year).toPlainString() +
                         "\nTotal revenue in third quarter: " + summarizeThirdFiscalQuarter(year).toPlainString() +
                         "\nTotal revenue in fourth quarter: " + summarizeFourthFiscalQuarter(year).toPlainString();
        statMainPage.showResult(message, title);
    }

    public void summarizeMonthButtonClicked(int year, int month) {
        String title = "Summary of " + getDateFormat(year, month, 1, "MMMM YYYY");
        String message = "Total revenue: " + summarizeMonth(year, month).toPlainString();
        statMainPage.showResult(message, title);
    }

    public void compareYearButtonClicked(int firstYear, int secondYear) {
        BigDecimal firstRevenue = summarizeFiscalYear(firstYear);
        BigDecimal secondRevenue =  summarizeFiscalYear(secondYear);

        String title = getDateFormat(firstYear, 1, 1, "YYYY") + " vs " + getDateFormat(secondYear, 1, 1, "YYYY");
        String message = getString("Total revenue of ", firstYear, secondYear, firstRevenue, secondRevenue);
        statMainPage.showResult(message, title);
    }

    public void compareQuarterButtonClicked(int firstYear, int secondYear) {
        BigDecimal first1QuarterRevenue = summarizeFirstFiscalQuarter(firstYear);
        BigDecimal first2QuarterRevenue = summarizeSecondFiscalQuarter(firstYear);
        BigDecimal first3QuarterRevenue = summarizeThirdFiscalQuarter(firstYear);
        BigDecimal first4QuarterRevenue = summarizeFourthFiscalQuarter(firstYear);

        BigDecimal second1QuarterRevenue = summarizeFirstFiscalQuarter(secondYear);
        BigDecimal second2QuarterRevenue = summarizeSecondFiscalQuarter(secondYear);
        BigDecimal second3QuarterRevenue = summarizeThirdFiscalQuarter(secondYear);
        BigDecimal second4QuarterRevenue = summarizeFourthFiscalQuarter(secondYear);

        String title = getDateFormat(firstYear, 1, 1, "YYYY") + " vs " + getDateFormat(secondYear, 1, 1, "YYYY");
        String message = getString("1. Quarter of ", firstYear, secondYear, first1QuarterRevenue, second1QuarterRevenue) +
                         "\n\n" +
                         getString("2. Quarter of ", firstYear, secondYear, first2QuarterRevenue, second2QuarterRevenue) +
                         "\n\n" +
                         getString("3. Quarter of ", firstYear, secondYear, first3QuarterRevenue, second3QuarterRevenue) +
                         "\n\n" +
                         getString("4. Quarter of ", firstYear, secondYear, first4QuarterRevenue, second4QuarterRevenue);
        statMainPage.showResult(message, title);
    }

    public void compareMonthButtonClicked(int firstYear, int firstMonth, int secondYear, int secondMonth) {
        BigDecimal firstRevenue = summarizeMonth(firstYear, firstMonth);
        BigDecimal secondRevenue =  summarizeMonth(secondYear, secondMonth);

        String title = getDateFormat(firstYear, firstMonth, 1, "MMMM YYYY") + " vs " + getDateFormat(secondYear, secondMonth, 1, "MMMM YYYY");
        String message = "Total revenue of " + getDateFormat(firstYear, firstMonth, 1, "MMMM YYYY : ") + firstRevenue.toPlainString() +
                         "\nTotal revenue of " + getDateFormat(secondYear, secondMonth, 1, "MMMM YYYY : ") + secondRevenue.toPlainString() +
                         "\n" + getDifference(firstRevenue, secondRevenue);
        statMainPage.showResult(message, title);
    }

    private String getDateFormat(int year, int month, int day, String format) {
        // Dates must be created by subtracting 1900 from the year, as per Date constructor docs
        return new SimpleDateFormat(format).format(new Date(year - 1900, month, day));
    }

    private String getString(String message, int firstYear, int secondYear, BigDecimal firstRevenue, BigDecimal secondRevenue) {
        return message+ getDateFormat(firstYear, 1, 1, "YYYY : ") + firstRevenue.toPlainString() +
                "\n" + message + getDateFormat(secondYear, 1, 1, "YYYY : ") + secondRevenue.toPlainString() +
                "\n" + getDifference(firstRevenue, secondRevenue);
    }

    private String getDifference(BigDecimal firstRevenue, BigDecimal secondRevenue) {
        switch (firstRevenue.compareTo(secondRevenue)) {
            case -1: return "Revenue increased " + secondRevenue.subtract(firstRevenue).toPlainString();
            case 1 : return "Revenue decreased " + firstRevenue.subtract(secondRevenue).toPlainString();
            default: return "Revenue stayed the same";
        }
    }

    // FY 2015: 1 October 2014 - 30 September 2015
    private BigDecimal summarizeFiscalYear(int year) {
        return summarizeInterval(year - 1, 10, 1, year, 9, 30);
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
}
