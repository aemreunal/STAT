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
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.ProductService;
import stat.ui.product.detail.view.ProductDetailWindow;
import stat.ui.product.main.ProductController;

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
    private ProductService productService;

    private ProductDetailWindow productDetailWindow;
    private ProductController   productController;
    private Integer productId;

    public void showDetailsOfProductWithId(ProductController productController, Integer productId) {
        this.productController = productController;
        this.productId = productId;
        showProductWindow();
        setProductSaleStats();
        setProductSaleList();
    }

    public void duplicateProductButtonClicked() {
        productController.addProductButtonClicked(productId);
    }

    private void showProductWindow() {
        Product product = productService.getProductWithId(productId);
        productDetailWindow = new ProductDetailWindow(this, product);
    }

    private void setProductSaleStats() {
        int amountSold = productService.getAmountOfProductSoldTotal(productId);
        BigDecimal totalRevenue = productService.getPriceOfProductSoldTotal(productId);
        productDetailWindow.setProductSaleStats(amountSold, totalRevenue);
    }

    private void setProductSaleList() {
        LinkedHashSet<Sale> sales = productService.getSalesOfProduct(productId);
        productDetailWindow.setProductSales(sales);
    }
}
