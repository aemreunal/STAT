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


public enum BreakdownType {
    MONTHLY, QUARTERLY, YEARLY;

    public String toTitleCasedString () {
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
    }

    public String toLowerCaseString() {
        return this.name().toLowerCase();
    }

    public String toUppperCaseString() {
        return this.name().toUpperCase();
    }

}
