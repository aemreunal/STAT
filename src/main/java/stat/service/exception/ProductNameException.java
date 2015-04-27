package stat.service.exception;

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

public class ProductNameException extends Throwable {

    public ProductNameException(String name) {
        super("A product with name \'" + name + "\' already exists!", null, true, false);
    }
}
