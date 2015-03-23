package stat.ui.mainApp;

import stat.ui.mainApp.view.ApplicationWindow;
import stat.ui.product.ProductController;
import stat.ui.sale.main.SaleController;

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
public class ApplicationController {

    @Autowired
    private ApplicationWindow applicationWindow;

    @Autowired
    private SaleController saleController;

    @Autowired
    private ProductController productController;

    public void startGui() {
        applicationWindow.display();
        saleController.refreshSaleListTable();
        productController.refreshProductListTable();
    }

    public void changedTab(String tabName) {
        // TODO: implement
        // Tab names can be found in the class ApplicationWindow as fields.
    }

}
