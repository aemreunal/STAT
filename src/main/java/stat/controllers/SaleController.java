package stat.controllers;

import stat.domain.Sale;
import stat.graphics.NewSalePage;
import stat.service.SaleService;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Uğur Özkan.
 *
 * ugur.ozkan@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleController {

    @Autowired
    private NewSalePage salePage;

    @Autowired
    private SaleService saleService;

    public Set<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    public void saveSale() {
        //TODO fix
       /* Product product = productService.createNewProduct("test product", 1.0);
        Sale sale = saleService.createNewSale("test customer");
        int amount = 4;

        sale = saleService.addProduct(sale, product, amount);
        saleService.createNewSale("Customer Name");
        saleService.addProduct()
        */
    }

    //TODO Waiting for NewSalePage to be completed.
}

