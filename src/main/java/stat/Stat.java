package stat;

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

import stat.domain.Sale;
import stat.service.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Stat implements CommandLineRunner {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private SaleService saleService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------TEST-----");
        Sale sale = new Sale();
        saleService.save(sale);
    }
}
