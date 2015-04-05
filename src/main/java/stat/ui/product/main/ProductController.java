package stat.ui.product.main;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.domain.Product;
import stat.service.ProductService;
import stat.service.exception.ProductNameException;
import stat.service.exception.SoldProductDeletionException;
import stat.ui.Page;
import stat.ui.PageController;
import stat.ui.product.detail.ProductDetailController;
import stat.ui.product.main.view.ProductAddPage;
import stat.ui.product.main.view.ProductFilterPanel;
import stat.ui.product.main.view.ProductMainPage;
import stat.ui.product.main.view.helper.ProductColType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductController implements PageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMainPage productMainPage;

    @Autowired
    private ProductDetailController productDetailController;

    private ArrayList<Integer> productIDList = new ArrayList<>();

    @Override
    public void refreshPage() {
        showProducts(productService.getAllProducts());
    }

    public void saveProduct(ProductAddPage productAddPage, String productName, String productDescription, BigDecimal productPrice) {
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

    public void showProducts(Set<Product> productsToShow) {
        productIDList.clear();
        Product[] products = productsToShow.toArray(new Product[productsToShow.size()]);
        Object[][] productTableObjects = new Object[products.length][ProductColType.getColNameList().length];
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            productTableObjects[i] = new Object[] { product.getName(), product.getDescription(), product.getPrice() };
            productIDList.add(i, product.getProductId());
        }
        productMainPage.setProductsList(productTableObjects);
    }

    public void addProductButtonClicked() {
        ProductAddPage productAddPage = new ProductAddPage(this);
        Page.showPopup(productAddPage);
    }

    public void removeProductButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        //TODO: Confirm option must be added.
        int productIdToRemove = productIDList.remove(row);
        try {
            productService.deleteProduct(productIdToRemove);
            refreshPage();
        } catch (SoldProductDeletionException e) {
            productMainPage.displayProductDeletionError(e);
        }
    }

    public void viewProductButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        productDetailController.showDetailsOfProductWithId(productIDList.get(row));
    }

    public void applyFilterButtonClicked(ProductFilterPanel filterPanel) {
        String productName = filterPanel.getProductName();
        String description = filterPanel.getDescription();
        BigDecimal minPrice = filterPanel.getMinPrice();
        BigDecimal maxPrice = filterPanel.getMaxPrice();
        LinkedHashSet<Product> products = productService.searchForProducts(productName, description, minPrice, maxPrice);
        showProducts(products);
    }
}
