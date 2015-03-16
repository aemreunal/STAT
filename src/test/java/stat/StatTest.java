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

import stat.config.GlobalSettings;
import stat.repository.SaleRepo;
import stat.service.ProductService;
import stat.service.SaleService;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(GlobalSettings.TEST_DB_PROPERTY_SOURCE)
@Transactional
public abstract class StatTest {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected SaleService saleService;


    @Before
    public void setUpTest() {
        System.out.println("<------TEST------>");
    }
}
