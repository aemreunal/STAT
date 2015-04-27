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

import stat.domain.Sale;

public class SoldProductDeletionException extends Throwable {

    private final Integer productId;
    private final Sale    sale;

    public SoldProductDeletionException(Integer productId, Sale sale) {
        super("The product with ID: " + productId + " can't be deleted because it's part of the sale with ID: "
                      + sale.getSaleId() + "!", null, true, false);
        this.productId = productId;
        this.sale = sale;
    }

    public Integer getProductId() {
        return productId;
    }

    public Sale getSale() {
        return sale;
    }

    public String getErrorMessage() {
        return "The product can't be deleted because it's part of a sale to \'"
                + sale.getCustomerName() + "\' on " + sale.getDate()
                + "!";
    }
}
