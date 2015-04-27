package stat.service.repository;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
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
