package stat.controllers;

import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNotFoundException;
import stat.graphics.NewSalePage;
import stat.service.ProductService;
import stat.service.SaleService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
public class SaleController implements PageController{

    @Autowired
    private NewSalePage salePage;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

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

    public double calculatePrice(String productName, int amount) {
        double productPrice = 0.0;
        try {
            productPrice = productService.getProductWithName(productName).getPrice().doubleValue();
        }catch (ProductNotFoundException pnfe){
            System.out.println("SaleController pnfe");
        }
        return productPrice * amount;
    }

    public LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void refreshPage() {
        //TODO implement
    }

    public void removeSale(int saleID) {
        //TODO implement
    }

}

