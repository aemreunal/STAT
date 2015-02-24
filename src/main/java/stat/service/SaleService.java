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

import stat.domain.Sale;
import stat.repository.SaleRepo;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SaleService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private SaleRepo saleRepo;

    public Sale save(Sale sale) {
        return saleRepo.save(sale);
    }

}
