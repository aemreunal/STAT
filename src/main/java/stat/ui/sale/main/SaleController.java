package stat.ui.sale.main;

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.ProductService;
import stat.service.SaleService;
import stat.service.exception.ProductNotFoundException;
import stat.ui.Page;
import stat.ui.PageController;
import stat.ui.sale.add.SaleAddController;
import stat.ui.sale.main.view.SaleMainPage;
import stat.ui.sale.main.view.SaleViewPage;
import stat.ui.sale.main.view.helper.SaleColType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
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
    private SaleMainPage saleMainPage;

    @Autowired
    private SaleAddController saleAddController;

    private ArrayList<Integer> saleIDList = new ArrayList<>();

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
        refreshSaleListTable();
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

    @Override
    public void refreshPage() {
        refreshSaleListTable();
    }

    public void addSaleButtonClicked() {
        saleAddController.showSaleCreator();
    }


    public void removeSaleButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        int saleIdToRemove = saleIDList.remove(row);
        saleService.deleteSale(saleIdToRemove);
        refreshSaleListTable();
    }

    public void showSaleDetailsButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        Integer saleId = saleIDList.get(row);
        Sale sale = saleService.getSaleWithId(saleId);
        LinkedHashMap<Product, Integer> productsAndAmounts = saleService.getProductsOfSale(saleId);
        SaleViewPage saleViewPage = new SaleViewPage(sale, productsAndAmounts);
        Page.showPopup(saleViewPage);
    }
}

