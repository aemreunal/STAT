package stat.graphics;

import javax.swing.*;

/**
 * Created by
 * Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */


public class ApplicationWindow extends JFrame {

    private Page currentPage;

    public ApplicationWindow(Page initialPage) {
        setWindowConfigurations();
        setCurrentPage(initialPage);
    }

    private void setWindowConfigurations() {
        setBounds(0, 0, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setCurrentPage(Page nextPage) {
        if (currentPage != null)
            getContentPane().remove(currentPage);

        currentPage = nextPage;
        currentPage.setApplicationWindow(this);


        getContentPane().add(nextPage);
        setSize(currentPage.getSize());
        invalidate();
        validate();
    }

}
