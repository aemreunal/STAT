package stat.ui.sale;

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.ProductService;
import stat.service.SaleService;
import stat.service.exception.ProductNotFoundException;
import stat.ui.PageController;
import stat.ui.sale.view.SaleAddPage;
import stat.ui.sale.view.SaleMainPage;
import stat.ui.sale.view.SaleViewPage;
import stat.ui.sale.view.helper.SaleColType;

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
    private SaleService    saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleMainPage   saleMainPage;

    private ArrayList<Integer> saleIDList = new ArrayList<>();

    // SaleAddPage methods
    public boolean validateDate(String date) {
        String dateRegex = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
        return date.matches(dateRegex);
    }

    public void saveSale(String customerName, String date, ArrayList<String> productNames, ArrayList<Integer> amounts) {
        Sale sale = saleService.createNewSale(customerName, parseDate(date));
        for (int i = 0; i < productNames.size(); i++) {
            try {
                Integer productId = productService.getIdOfProductWithName(productNames.get(i));
                sale = saleService.addProduct(sale, productId, amounts.get(i));
            } catch (ProductNotFoundException e) {
                System.out.println("Product not found exception");
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


    public void refreshSaleListTable() {
        saleIDList.clear();
        Set<Sale> allSales = saleService.getAllSales();
        Sale[] sales = allSales.toArray(new Sale[allSales.size()]);
        Object[][] saleTableObjects = new Object[sales.length][SaleColType.getColNameList().length];
        for (int i = 0; i < sales.length; i++) {
            Sale sale = sales[i];
            saleTableObjects[i] = new Object[] { sale.getCustomerName(), sale.getDate(), sale.getTotalPrice() };
            saleIDList.add(i, sale.getSaleId());
        }
        saleMainPage.setSalesList(saleTableObjects);
    }

    public void removeSale(int row) {
        int saleIdToRemove = saleIDList.remove(row);
        saleService.deleteSale(saleIdToRemove);
        refreshSaleListTable();
    }

    private LinkedHashMap<Product, Integer> getProductsAndAmounts(Integer saleId) {
        return saleService.getProductsOfSale(saleId);
    }

    public void populateWithProductNames(SaleAddPage saleAddPage) {
        saleAddPage.fillProducts(getProductNames());
    }

    private LinkedHashSet<String> getProductNames() {
        return productService.getAllProducts().stream().map(Product::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void refreshPage() {
        refreshSaleListTable();
    }


    public void showAddSaleWindow() {
        SaleAddPage saleAddPage = new SaleAddPage(this);
        populateWithProductNames(saleAddPage);
        saleMainPage.displayAddSaleWindow(saleAddPage);
    }

    public void showSaleDetails(int row) {
        SaleViewPage saleViewPage = new SaleViewPage();
        saleViewPage.setSale(saleService.getSaleWithId(saleIDList.get(row)));

        Integer saleId = saleIDList.get(row);
        Sale sale = saleService.getSaleWithId(saleId);
        String customerName = sale.getCustomerName();
        Date date = sale.getDate();
        BigDecimal totalPrice = sale.getTotalPrice();

        saleViewPage.setCustomerNameField(customerName);
        saleViewPage.setDateField(date);
        saleViewPage.setTotalPriceField(totalPrice);
        LinkedHashMap<Product, Integer> productsAndAmounts = getProductsAndAmounts(saleId);

        for (Map.Entry<Product, Integer> entry : productsAndAmounts.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(amount));
            saleViewPage.addProductDetailsToTable(product.getName(), amount, price);
        }

        saleMainPage.displaySaleDetailsWindow(saleViewPage);
    }
}

