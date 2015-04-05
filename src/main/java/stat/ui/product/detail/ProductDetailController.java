package stat.ui.product.detail;

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

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.ProductService;
import stat.ui.product.detail.view.ProductDetailWindow;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductDetailController {

    @Autowired
    private ProductService      productService;
    private ProductDetailWindow productDetailWindow;

    public void showDetailsOfProductWithId(Integer productId) {
        showProductWindow(productId);
        setProductSaleStats(productId);
        setProductSaleList(productId);
    }

    private void showProductWindow(Integer productId) {
        Product product = productService.getProductWithId(productId);
        productDetailWindow = new ProductDetailWindow(this, product);
    }

    private void setProductSaleStats(Integer productId) {
        int amountSold = productService.getAmountOfProductSoldTotal(productId);
        BigDecimal totalRevenue = productService.getPriceOfProductSoldTotal(productId);
        productDetailWindow.setProductSaleStats(amountSold, totalRevenue);
    }

    private void setProductSaleList(Integer productId) {
        LinkedHashSet<Sale> sales = productService.getSalesOfProduct(productId);
        productDetailWindow.setProductSales(sales);
    }
}
