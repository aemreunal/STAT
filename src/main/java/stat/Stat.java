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

import stat.graphics.ApplicationWindow;
import stat.graphics.MenuPage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Stat implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("<------TEST------>");
        // The if-check is added as a temporary work-around for the HeadlessException
        // experienced when running tests.
        if (!envIsHeadless()) {
            startGui();
        }
    }

    private void startGui() {
        ApplicationWindow appWindow = new ApplicationWindow(new MenuPage());
        appWindow.setVisible(true);
    }

    private boolean envIsHeadless() {
        return System.getProperty("java.awt.headless", "false").equals("true");
    }
}
