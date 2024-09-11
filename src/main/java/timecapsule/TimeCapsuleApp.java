package timecapsule;

import javax.swing.SwingUtilities;

public class TimeCapsuleApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPage mainPage = new MainPage(null);  // User email can be passed if required
                mainPage.setVisible(true);
            }
        });
    }
}
