package stat.graphics;

import javax.swing.*;

/**
 * Created by
 * Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */

public abstract class Page extends JPanel {

    private ApplicationWindow appWindow;

    public void setApplicationWindow(ApplicationWindow appWindow) {
        this.appWindow = appWindow;
    }

    public ApplicationWindow getApplicationWindow() {
        return appWindow;
    }

}
