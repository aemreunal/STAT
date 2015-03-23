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
import stat.ui.Page;
import stat.ui.PageController;
import stat.ui.sale.add.view.SaleAddPage;
import stat.ui.sale.add.view.helper.SaleSaveException;
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

    public static final String           DATE_REGEX             = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
    public static final int              DEFAULT_PRODUCT_AMOUNT = 1;
    private final       SimpleDateFormat dateFormatter          = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleController saleController;

    private SaleAddPage saleAddPage;

    private ArrayList<Product> availableProducts     = new ArrayList<Product>();
    private ArrayList<Product> chosenProducts        = new ArrayList<Product>();
    private ArrayList<Integer> chosenProductsAmounts = new ArrayList<Integer>();

    public void showSaleCreator() {
        saleAddPage = new SaleAddPage(this);
        refreshPage();
        Page.showPopup(saleAddPage);
    }

    @Override
    public void refreshPage() {
        refreshProductData();
        reflectAvailableProducts();
    }

    public void refreshProductData() {
        availableProducts.clear();
        chosenProducts.clear();
        chosenProductsAmounts.clear();

        Set<Product> allProducts = productService.getAllProducts();
        availableProducts.addAll(allProducts);
    }

    private void reflectProductData() {
        reflectAvailableProducts();
        reflectChosenProducts();
    }

    private void reflectAvailableProducts() {
        Object[][] availableProductsTableObjects = new Object[availableProducts.size()][1];
        for (int i = 0; i < availableProducts.size(); i++) {
            availableProductsTableObjects[i] = new Object[] { availableProducts.get(i).getName() };
        }
        saleAddPage.setAvailableProducts(availableProductsTableObjects);
    }

    private void reflectChosenProducts() {
        Object[][] chosenProductsTableObjects = new Object[chosenProducts.size()][3];
        for (int i = 0; i < chosenProducts.size(); i++) {
            Product product = chosenProducts.get(i);
            chosenProductsTableObjects[i] = new Object[] { product.getName(),
                    chosenProductsAmounts.get(i),
                    calculatePrice(product, chosenProductsAmounts.get(i)) };
        }
        saleAddPage.setChosenProducts(chosenProductsTableObjects);
    }

    public void updateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (int i = 0; i < chosenProducts.size(); i++) {
            BigDecimal productPrice = calculatePrice(chosenProducts.get(i), chosenProductsAmounts.get(i));
            totalPrice = totalPrice.add(productPrice);
        }
        saleAddPage.setTotalPrice(totalPrice.toPlainString());
    }

    public void addProductButtonClicked(int row) {
        Product product = availableProducts.remove(row);
        chosenProducts.add(product);
        chosenProductsAmounts.add(DEFAULT_PRODUCT_AMOUNT);
        reflectProductData();
        updateTotalPrice();
    }

    public void removeProductButtonClicked(int row) {
        Product product = chosenProducts.remove(row);
        chosenProductsAmounts.remove(row);
        availableProducts.add(product);
        reflectProductData();
        updateTotalPrice();
    }

    public void confirmButtonClicked() {
        try {
            Date date = getDate();
            String customerName = getCustomerName();
            saveSale(customerName, date);
            saleController.refreshPage();
            saleAddPage.displaySuccess();
        } catch (ParseException e) {
            saleAddPage.showDateParseError();
        } catch (SaleSaveException e) {
            switch (e.getCausingData()) {
                case SaleSaveException.DATE:
                    saleAddPage.showDateParseError();
                    break;
                case SaleSaveException.NAME:
                    saleAddPage.showCustomerNameParseError();
                    break;
                case SaleSaveException.SAVE:
                    saleAddPage.showSaveError();
                    break;
            }
        }
    }

    private Date getDate() throws ParseException, SaleSaveException {
        String dateText = saleAddPage.getDateText();
        if (!dateText.matches(DATE_REGEX)) {
            throw new SaleSaveException(SaleSaveException.DATE);
        }
        return dateFormatter.parse(dateText);
    }

    private String getCustomerName() throws SaleSaveException {
        String customerName = saleAddPage.getCustomerNameText();
        if (customerName == null || customerName.equals("")) {
            throw new SaleSaveException(SaleSaveException.NAME);
        }
        return customerName;
    }

    public void saveSale(String customerName, Date date) throws SaleSaveException {
        Sale sale = saleService.createNewSale(customerName, date);
        for (int i = 0; i < chosenProducts.size(); i++) {
            Integer productId = chosenProducts.get(i).getProductId();
            sale = saleService.addProduct(sale, productId, chosenProductsAmounts.get(i));
        }
    }

    public void productAmountChanged(int row, int newAmount) {
        chosenProductsAmounts.set(row, newAmount);
        Product product = chosenProducts.get(row);
        BigDecimal newPrice = calculatePrice(product, newAmount);
        saleAddPage.setChosenProductPrice(row, newPrice);
        updateTotalPrice();
    }

    public BigDecimal calculatePrice(Product product, int amount) {
        return product.getPrice().multiply(BigDecimal.valueOf(amount));
    }
}