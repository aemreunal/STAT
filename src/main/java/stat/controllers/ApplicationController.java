package stat.controllers;

import stat.graphics.ApplicationWindow;
import stat.graphics.MenuPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Uğur Özkan.
 *
 * ugur.ozkan@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ApplicationController {

    @Autowired
    private ApplicationWindow applicationWindow;

    @Autowired
    private MenuPage menuPage;

    public void startGui() {
        // TODO: remove
        //applicationWindow.setCurrentPage(menuPage);
        applicationWindow.setVisible(true);
    }
}
