package stat.ui.sale.add;

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

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.ProductService;
import stat.service.SaleService;
import stat.service.exception.ProductNotFoundException;
import stat.ui.Page;
import stat.ui.sale.add.view.SaleAddPage;
import stat.ui.sale.main.SaleController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleAddController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleController saleController;

    public void showSaleCreator() {
        SaleAddPage saleAddPage = new SaleAddPage(this);
        saleAddPage.setAvailableProducts(getProductNames());
        Page.showPopup(saleAddPage);
    }

    private LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public boolean saveSale(String customerName, Date date, ArrayList<String> productNames, ArrayList<Integer> amounts) {
        Sale sale = saleService.createNewSale(customerName, date);
        for (int i = 0; i < productNames.size(); i++) {
            try {
                Integer productId = productService.getIdOfProductWithName(productNames.get(i));
                sale = saleService.addProduct(sale, productId, amounts.get(i));
            } catch (ProductNotFoundException e) {
                System.out.println("Product not found exception");
                return false;
            }
        }
        saleController.refreshSaleListTable();
        return true;
    }

    public BigDecimal calculatePrice(String productName, int amount) {
        BigDecimal productPrice = BigDecimal.ZERO;
        try {
            productPrice = productService.getProductWithName(productName).getPrice();
        } catch (ProductNotFoundException pnfe) {
            System.out.println("SaleController pnfe");
        }
        return productPrice.multiply(BigDecimal.valueOf(amount));
    }
}
