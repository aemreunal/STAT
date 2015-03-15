package stat.controllers;

import stat.graphics.ApplicationWindow;
import stat.graphics.MenuPage;
import stat.graphics.Page;

/**
 * Created by Uğur Özkan.
 *
 * ugur.ozkan@ozu.edu.tr
 */
public class ApplicationController {

    private final Page INITIAL_PAGE = new MenuPage();

    public ApplicationController() {
        startGui();
    }

    private void startGui() {
        ApplicationWindow appWindow = new ApplicationWindow(INITIAL_PAGE);
        appWindow.setVisible(true);
    }
}
