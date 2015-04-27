package stat.ui.sale.add.view.helper;

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

public class SaleSaveException extends Throwable {
    public static final int DATE = 0;
    public static final int NAME = 1;
    public static final int SAVE = 2;

    private final int causingData;

    public SaleSaveException(int causingData) {
        super("Invalid data!", null, true, false);
        this.causingData = causingData;
    }

    public int getCausingData() {
        return this.causingData;
    }
}
