package stat.service;

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
import stat.repository.SaleRepo;

import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SaleService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private SaleRepo saleRepo;

    public Sale createNewSale() {
        Sale sale = new Sale();
        sale = this.save(sale);
        return sale;
    }

    private Sale save(Sale sale) {
        return saleRepo.save(sale);
    }

    @Transactional(readOnly = true)
    public Set<Sale> getAllSales() {
        return saleRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Sale getSaleWithId(Integer saleId) {
        return saleRepo.findOne(saleId);
    }

    public Sale addProduct(Integer saleId, Product product, int amount) {
        Sale sale = this.getSaleWithId(saleId);
        return this.addProduct(sale, product, amount);
    }

    public Sale addProduct(Sale sale, Product product, int amount) {
        sale.addProductToSale(product, amount);
        return this.save(sale);
    }

    public void deleteSaleWithId(Integer saleId) {
        saleRepo.delete(saleId);
    }
}
