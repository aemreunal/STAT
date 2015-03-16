package stat.exception;

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

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException() {
        super("Product was not found!", null, true, false);
    }

    public ProductNotFoundException(String name) {
        super("There are no products with the name: \'" + name + "\'!", null, true, false);
    }
}
