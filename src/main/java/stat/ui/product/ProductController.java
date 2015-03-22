package stat.ui.product;

import stat.ui.PageController;
import stat.domain.Product;
import stat.service.exception.ProductNameException;
import stat.service.exception.SoldProductDeletionException;
import stat.ui.product.view.ProductAddPage;
import stat.ui.product.view.helper.ProductColType;
import stat.ui.product.view.ProductMainPage;
import stat.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ProductController implements PageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAddPage productAddPage;

    @Autowired
    private ProductMainPage productMainPage;

    private ArrayList<Integer> productIDList = new ArrayList<>();

    public void saveProduct(String productName, String productDescription, BigDecimal productPrice) {
        if (productPrice.signum() == -1) {
            productAddPage.displayValidationError();
            return;
        }
        try {
            productService.createNewProduct(productName, productDescription, productPrice);
            refreshPage();
            productAddPage.displaySuccess();
            productAddPage.clearInputFields();
        } catch (ProductNameException pne) {
            productAddPage.displayNameError();
        }
    }

    public void refreshProductListTable() {
        Set<Product> allProducts = productService.getAllProducts();
        Product[] products = allProducts.toArray(new Product[allProducts.size()]);
        Object[][] productTableObjects = new Object[products.length][ProductColType.getColNameList().length];
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            productTableObjects[i] = new Object[] { product.getName(), product.getDescription(), product.getPrice() };
            productIDList.add(i, product.getProductId());
        }
        productMainPage.setProductsList(productTableObjects);
    }

    public void removeProduct(int row) {
        int productIdToRemove = productIDList.get(row);
        try {
            productService.deleteProduct(productIdToRemove);
            refreshProductListTable();
        } catch (SoldProductDeletionException e) {
            productMainPage.displayProductDeletionError(e);
        }
    }

    public void showProductDetails(int row) {
        Product product = productService.getProductWithId(productIDList.get(row));
        productMainPage.displayProductDetailWindow(product.getName(), product.getDescription(), product.getPrice().toPlainString());
    }

    @Override
    public void refreshPage() {
        refreshProductListTable();
    }
}
