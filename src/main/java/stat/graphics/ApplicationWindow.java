package stat.graphics;

import javax.swing.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ApplicationWindow extends JFrame {

    private Page currentPage;

    public ApplicationWindow() {
        setBounds(0, 0, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setCurrentPage(Page nextPage) {
        if (currentPage != null) {
            getContentPane().remove(currentPage);
        }

        currentPage = nextPage;
        currentPage.setApplicationWindow(this);

        getContentPane().add(nextPage);
        setSize(currentPage.getSize());
        validate();
    }

}
