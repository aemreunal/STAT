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

import stat.domain.Sale;

public class SoldProductDeletionException extends Throwable {

    private final Integer productId;
    private final Sale sale;

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
