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

import java.util.Set;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepo extends CrudRepository<Sale, Integer>, JpaSpecificationExecutor {

    Set<Sale> findAll();

    // http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
//    Set<Sale> findByDateBetweenOrderByDateAsc(Date begin, Date end);
}
