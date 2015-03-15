package stat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import stat.exception.ProductNameException;
import stat.graphics.NewProductPage;
import stat.service.ProductService;
import stat.domain.Product;

import java.math.BigDecimal;

/**
 * Created by Uğur Özkan.
 *
 * ugur.ozkan@ozu.edu.tr
 */
public class ProductController {

    private NewProductPage productPage;
    private ProductService productService;

    public ProductController(NewProductPage productPage, ProductService productService) {
        this.productPage = productPage;
        this.productService = productService;
    }

    public void saveProduct() {
        String productName = productPage.getProductName();
        String productDescription = productPage.getProductDescription();
        BigDecimal productPrice = productPage.getProductPrice();

        try {
            Product product = productService.createNewProduct(productName, productDescription,productPrice);
            productPage.displaySuccess();
        } catch(ProductNameException pne){
            productPage.displayValidationError();
        }
    }

}
