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
import stat.service.ProductService;
import stat.service.SaleService;

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

    private LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void showSaleCreator() {
//        SaleAddPage saleAddPage = new SaleAddPage(this);
//        saleAddPage.setAvailableProducts(getProductNames());
//        Page.showPopup(saleAddPage);
        // TODO implement.
    }

    public BigDecimal calculatePrice(String productName, int i) {
        // TODO implement.
        return null;
    }

    public boolean saveSale(String trim, Date date, ArrayList<String> products, ArrayList<Integer> amounts) {
        // TODO implement.
        return false;
    }
}
