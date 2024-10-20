package timecapsule;

import javax.swing.SwingUtilities;

public class TimeCapsuleApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage("user@example.com"); 
            mainPage.setVisible(true);
        });
    }
}

