package stat.controllers;

import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNotFoundException;
import stat.graphics.SaleAddPage;
import stat.graphics.SaleMainPage;
import stat.graphics.SaleViewPage;
import stat.service.ProductService;
import stat.service.SaleService;

import java.math.BigDecimal;
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

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private SaleService saleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleAddPage saleAddPage;
    @Autowired
    private SaleMainPage saleMainPage;
    @Autowired
    private SaleViewPage saleViewPage;

    // SaleAddPage methods
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

    public BigDecimal calculatePrice(String productName, int amount) {
        BigDecimal productPrice = BigDecimal.ZERO;
        try {
            productPrice = productService.getProductWithName(productName).getPrice();
        } catch (ProductNotFoundException pnfe) {
            System.out.println("SaleController pnfe");
        }
        return productPrice.multiply(BigDecimal.valueOf(amount));
    }


    // SaleMainPage methods
    public void populateWithSales() {
        getAllSales().forEach(this::addSale);
    }

    private void addSale(Sale sale) {
        Integer id = sale.getSaleId();
        String customerName = sale.getCustomerName();
        Date date = sale.getDate();
        BigDecimal totalPrice = calculateTotalPrice(id);
        saleMainPage.addSale(id, customerName, date, totalPrice);
    }

    private BigDecimal calculateTotalPrice(Integer saleId) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> productsAndAmounts : getProductsAndAmounts(saleId).entrySet()) {
            Product product = productsAndAmounts.getKey();
            Integer amount = productsAndAmounts.getValue();
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(amount)));
        }
        return totalPrice;
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

    public void fillSaleDetails(Integer saleId) {
        LinkedHashMap<Product, Integer> productsAndAmounts = getProductsAndAmounts(saleId);

        populateProductTable(productsAndAmounts);
        saleViewPage.setCustomerNameField(saleService.getSaleWithId(saleId).getCustomerName());
        saleViewPage.setDateField(saleService.getSaleWithId(saleId).getDate());
        saleViewPage.setTotalPriceField(productsAndAmounts.values().stream().mapToInt(Integer::intValue).sum());
    }

    private LinkedHashMap<Product, Integer> getProductsAndAmounts(Integer saleId) {
        return saleService.getProductsOfSale(saleId);
    }

    private void populateProductTable(LinkedHashMap<Product, Integer> productsAndAmounts) {
        for (Product product : productsAndAmounts.keySet()) {
            String productName = product.getName();
            int amount = productsAndAmounts.get(product);
            double price = product.getPrice().doubleValue() * amount;

            saleViewPage.addProductDetailsToTable(productName, amount, price);
        }
    }

    public void populateWithProductNames() {
        saleAddPage.fillProducts(getProductNames());
    }

    private LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public HashSet<Sale> getSortedSales() {
        //TODO improve
        return getAllSales().stream().sorted().collect(Collectors.toCollection(HashSet<Sale>::new));
    }

    @Override
    public void refreshPage() {
        saleMainPage.refreshTable();
    }
}

