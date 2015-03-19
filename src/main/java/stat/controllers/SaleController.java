package stat.controllers;

import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNotFoundException;
import stat.graphics.SaleAddPage;
import stat.graphics.SaleMainPage;
import stat.service.ProductService;
import stat.service.SaleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Uğur Özkan.
 * <p>
 * ugur.ozkan@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleController implements PageController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleAddPage saleAddPage;

    @Autowired
    private SaleMainPage saleMainPage;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public boolean validateDate(String date) {
        String dateRegex = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
        return date.matches(dateRegex);
    }

    public void saveSale(String customerName, String date, ArrayList<String> products, ArrayList<Integer> amounts) {
        Sale sale = saleService.createNewSale(customerName, parseDate(date));
        for (int i = 0; i < products.size(); i++) {
            try {
                Integer productId = productService.getIdOfProductWithName(products.get(i));
                sale = saleService.addProduct(sale, productId, amounts.get(i));
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
        } catch (ProductNotFoundException pnfe) {
            System.out.println("SaleController pnfe");
        }
        return productPrice * amount;
    }

    public void populateWithProductNames() {
        saleAddPage.fillProducts(getProductNames());
    }

    private LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void populateWithSales() {
        saleMainPage.addSales(getAllSales());
    }

    private Set<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    public void removeSale(int saleID) {
        saleService.deleteSale(saleID);
    }

    public Sale getSale(int saleID) {
        return saleService.getSaleWithId(saleID);
    }

    public HashSet<Sale> getSortedSales() {
        //TODO improve
        return getAllSales().stream().sorted().collect(Collectors.toCollection(HashSet<Sale>::new));
    }

    @Override
    public void refreshPage() {
        //TODO implement
    }
}

