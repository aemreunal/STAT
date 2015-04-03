package stat.ui.sale.add;

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
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleAddController implements PageController {

    public static final int DEFAULT_PRODUCT_AMOUNT = 1;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleController saleController;

    private SaleAddPage saleAddPage;

    private ArrayList<Product> availableProducts     = new ArrayList<>();
    private ArrayList<Product> chosenProducts        = new ArrayList<>();
    private ArrayList<Integer> chosenProductsAmounts = new ArrayList<>();
    private LinkedHashSet<String> customerNames      = new LinkedHashSet<>();

    public void showSaleCreator() {
        saleAddPage = new SaleAddPage(this);
        customerNames = saleService.getCustomerNames();
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
        if (!haveProducts()) {
            saleAddPage.showMissingProductError();
        } else {
            recordSale();
        }

    }

    private boolean haveProducts() {
        return chosenProducts.size() > 0;
    }

    private void recordSale() {
        try {
            Date date = saleAddPage.getDate();
            String customerName = getCustomerName();
            saveSale(customerName, date);
            saleController.refreshPage();
            saleAddPage.displaySuccess();
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

    public String getNameSuggestion(String text) {
        if (text.equals(""))
            return "";

        LinkedHashSet<String> possibleNameSuggestions = customerNames.stream()
                .filter(name -> name.startsWith(text))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        try {
            return possibleNameSuggestions.iterator().next();
        } catch (NoSuchElementException e) {
            return "";
        }

    }
}
