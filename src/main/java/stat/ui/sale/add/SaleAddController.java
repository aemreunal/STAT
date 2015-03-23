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
import stat.ui.PageController;
import stat.ui.sale.add.view.SaleAddPage;
import stat.ui.sale.main.SaleController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleAddController implements PageController {

    public static final String           DATE_REGEX    = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
    private final       SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleController saleController;

    private SaleAddPage saleAddPage;

    private ArrayList<String>  availableProductsNames = new ArrayList<String>();
    private ArrayList<String>  chosenProductsNames    = new ArrayList<String>();
    private ArrayList<Integer> chosenProductsAmounts  = new ArrayList<Integer>();

    public void showSaleCreator() {
        saleAddPage = new SaleAddPage(this);
        refreshPage();
        Page.showPopup(saleAddPage);
    }

    public void refreshAvailableProductsTable() {
        availableProductsNames.clear();
        chosenProductsNames.clear();
        chosenProductsAmounts.clear();

        Set<Product> allProducts = productService.getAllProducts();
        Product[] products = allProducts.toArray(new Product[allProducts.size()]);

        Object[][] availableProductsTableObjects = new Object[products.length][1];
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            availableProductsTableObjects[i] = new Object[] { product.getName() };
            availableProductsNames.add(i, product.getName());
        }
        saleAddPage.setAvailableProducts(availableProductsTableObjects);
    }

    @Override
    public void refreshPage() {
        refreshAvailableProductsTable();
    }

    public void saveSale() {
        String dateText = saleAddPage.getDateText();
        if (!dateText.matches(DATE_REGEX)) {
            saleAddPage.showDateParseError();
            return;
        }
        String customerName = saleAddPage.getCustomerNameText();
        if (customerName == null || customerName.equals("")) {
            saleAddPage.showCustomerNameParseError();
        }
        boolean saleSaved = saveSale(customerName, parseDate(dateText), saleAddPage.getChosenProducts(), saleAddPage.getChosenAmounts());
        if (saleSaved) {
            saleAddPage.displaySuccess();
        } else {
            saleAddPage.displayValidationError();
        }
    }

    private Date parseDate(String dateText) {
        try {
            return dateFormatter.parse(dateText);
        } catch (ParseException pe) {
            saleAddPage.showDateParseError();
        }
        return null;
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
