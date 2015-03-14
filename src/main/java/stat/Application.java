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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebEnvironment(false); // To not instantiate a web environment
        app.setHeadless(false); // To instantiate AWT
        app.setShowBanner(false); // To not show startup banner
        app.run(args);
    }
}
