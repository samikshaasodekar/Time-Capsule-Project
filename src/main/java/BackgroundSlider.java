import javax.swing.*;  
import java.awt.*;    
import java.util.Timer; 
import java.util.TimerTask; 

public class BackgroundSlider extends JPanel {
    private Image[] images;
    private int currentIndex;
    private Timer timer;

    public BackgroundSlider() {
        images = new Image[] {
            new ImageIcon("C:\\Engineering Notes\\New Projects\\TimeCapsuleProject\\src\\main\\java\\images\\img1").getImage(), // Load the first image
            new ImageIcon("C:\\Engineering Notes\\New Projects\\TimeCapsuleProject\\src\\main\\java\\images\\img3").getImage(), // Load the second image
            new ImageIcon("C:\\Engineering Notes\\New Projects\\TimeCapsuleProject\\src\\main\\java\\images\\img4").getImage()  // Load the third image
        };

        currentIndex = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % images.length;
                repaint();
            }
        }, 0, 5000); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if (images.length > 0) { 
            g.drawImage(images[currentIndex], 0, 0, getWidth(), getHeight(), this);
        }
    }
}

/*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BackgroundSlider().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
*/