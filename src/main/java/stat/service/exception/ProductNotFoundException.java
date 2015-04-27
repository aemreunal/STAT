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

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException() {
        super("Product was not found!", null, true, false);
    }

    public ProductNotFoundException(String name) {
        super("There are no products (or more than one product) with the name: \'" + name + "\'!", null, true, false);
    }
}
