package stat.ui.sale.add.view.helper;

/*
 ***************************
 * Copyright (c) 2015      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * emre@aemreunal.com      *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
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
