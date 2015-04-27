package stat;

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

import stat.config.GlobalSettings;
import stat.service.ProductService;
import stat.service.SaleService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    protected final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUpTest() {
        System.out.println("<------TEST------>");
    }

    protected int getRandomAmount() {
        return (int) (Math.random() * 10);
    }

    protected BigDecimal getRandomPrice() {
        return BigDecimal.valueOf(Math.random() * 100).setScale(GlobalSettings.PRICE_DECIMAL_PRECISION, BigDecimal.ROUND_HALF_UP);
    }
}
