package stat.ui.mainApp;

import stat.ui.mainApp.view.ApplicationWindow;
import stat.ui.product.main.ProductController;
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
        saleController.refreshPage();
        productController.refreshPage();
    }
}
