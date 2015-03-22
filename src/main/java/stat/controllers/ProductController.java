package stat.controllers;

import stat.domain.Product;
import stat.exception.ProductNameException;
import stat.exception.SoldProductDeletionException;
import stat.graphics.ProductAddPage;
import stat.graphics.ProductColType;
import stat.graphics.ProductMainPage;
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
        try {
            productService.createNewProduct(productName, productDescription, productPrice);
        } catch (ProductNameException pne) {
            System.out.println("productController pne ");
        }
        productAddPage.clearInputFields();
    }

    public void populateProductListTable() {
        productMainPage.clearProductsList();
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
        } catch (SoldProductDeletionException e) {
            productMainPage.showProductDeletionError(e);
        }
        populateProductListTable();
    }

    public void showProductDetails(int row) {
        Product product = productService.getProductWithId(productIDList.get(row));
        productMainPage.displayProductDetailWindow(product.getName(), product.getDescription(), product.getPrice().toPlainString());
    }

    public Product getProduct(int productId) {
        return productService.getProductWithId(productId);
    }

    @Override
    public void refreshPage() {
        productMainPage.clearProductsList();
    }
}
