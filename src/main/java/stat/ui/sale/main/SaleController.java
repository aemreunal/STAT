package stat.ui.sale.main;

import stat.domain.Product;
import stat.domain.Sale;
import stat.service.SaleService;
import stat.ui.Page;
import stat.ui.PageController;
import stat.ui.sale.add.SaleAddController;
import stat.ui.sale.main.view.SaleMainPage;
import stat.ui.sale.main.view.SaleViewPage;
import stat.ui.sale.main.view.helper.SaleColType;

import java.util.ArrayList;
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
    private SaleMainPage saleMainPage;

    @Autowired
    private SaleAddController saleAddController;

    private ArrayList<Integer> saleIDList = new ArrayList<>();

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

