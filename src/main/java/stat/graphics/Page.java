package stat.graphics;

import javax.swing.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public abstract class Page extends JPanel {

    private ApplicationWindow appWindow;

    public void setApplicationWindow(ApplicationWindow appWindow) {
        this.appWindow = appWindow;
    }

    public ApplicationWindow getApplicationWindow() {
        return appWindow;
    }

}
