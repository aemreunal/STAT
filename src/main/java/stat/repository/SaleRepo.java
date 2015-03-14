package stat.repository;

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

import java.util.Date;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepo extends CrudRepository<Sale, Integer>, JpaSpecificationExecutor {

    Set<Sale> findAll();

    Set<Sale> findByDateBetween(Date begin, Date end);

//    Sale findByCustomerName(String customerName);
}
