package stat.controllers;

import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNotFoundException;
import stat.graphics.SaleAddPage;
import stat.service.ProductService;
import stat.service.SaleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;

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
    private SaleAddPage saleAddPage;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public Set<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    public boolean validateDate(String date) {
        String dateRegex = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
        return date.matches(dateRegex) ? true : false;
    }

    public void saveSale(String customerName, String date, ArrayList<String> products, ArrayList<Integer> amounts) {
        Sale sale = saleService.createNewSale(customerName, parseDate(date));
        for (int i = 0; i < products.size(); i++) {
            try {
                Product product = productService.getProductWithName(products.get(i));
                sale = saleService.addProduct(sale,product,amounts.get(i));
            } catch (ProductNotFoundException pnfe) {
                System.out.println("SaleController pnfe");
            }
        }
    }

    private Date parseDate(String date) {
        try {
            return dateFormatter.parse(date);
        } catch (ParseException pe) {
            System.out.println("SaleController pe");
        }

        return null;
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

    public void populateWithProductNames() {
        saleAddPage.fillProducts(getProductNames());
    }

    @Override
    public void refreshPage() {
        //TODO implement
    }

    public void removeSale(int saleID) {
        //TODO implement
    }

    public Sale getSale(int saleID) {
        //TODO implement
        return null;
    }

}

