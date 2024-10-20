package timecapsule;

import com.toedter.calendar.JDateChooser;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MainPage extends JFrame {

    private JTextArea messageArea;
    private JDateChooser dateChooser;
    private JTextField emailField;
    private JButton saveButton, sendButton, exitButton;
    private JRadioButton privateRadioButton, publicRadioButton;
    private String userEmail;

    public MainPage(String userEmail) {
        this.userEmail = userEmail; // Store the user email
        setTitle("Time Capsule");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent closing without confirmation
        setLocationRelativeTo(null);
        initComponents();

        // Custom close behavior
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }

    private void initComponents() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set the background image here
                ImageIcon background = new ImageIcon("C:/Engineering Notes/New Projects/TimeCapsuleProject/src/main/java/images/img3.jpeg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JLabel messageLabel = new JLabel("Write your letter:");
        messageLabel.setBounds(50, 50, 120, 25);
        panel.add(messageLabel);

        messageArea = new JTextArea("Dear Me,"); // Set default text
        messageArea.setCaretPosition(messageArea.getText().length());
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(50, 80, 500, 200);
        panel.add(scrollPane);

        JLabel dateLabel = new JLabel("Delivery Date:");
        dateLabel.setBounds(50, 300, 100, 25);
        panel.add(dateLabel);

        String[] dateOptions = {"6 months", "1 year", "3 years", "5 years", "10 years", "Choose a date"};
        JComboBox<String> dateComboBox = new JComboBox<>(dateOptions);
        dateComboBox.setBounds(150, 300, 200, 25);
        dateComboBox.addActionListener(e -> dateChooser.setEnabled("Choose a date".equals(dateComboBox.getSelectedItem().toString())));
        panel.add(dateComboBox);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(360, 300, 200, 25);
        dateChooser.setEnabled(false);
        panel.add(dateChooser);

        JLabel emailLabel = new JLabel("Enter your email:");
        emailLabel.setBounds(50, 350, 120, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 350, 200, 25);
        panel.add(emailField);

        // Audience selection
        JLabel audienceLabel = new JLabel("Select your audience:");
        audienceLabel.setBounds(50, 400, 150, 25);
        panel.add(audienceLabel);

        privateRadioButton = new JRadioButton("Private");
        privateRadioButton.setBounds(200, 400, 80, 25);
        panel.add(privateRadioButton);

        publicRadioButton = new JRadioButton("Public, but anonymous");
        publicRadioButton.setBounds(280, 400, 200, 25);
        panel.add(publicRadioButton);

        ButtonGroup audienceGroup = new ButtonGroup();
        audienceGroup.add(privateRadioButton);
        audienceGroup.add(publicRadioButton);

        saveButton = new JButton("Save Letter");
        saveButton.setBounds(50, 500, 150, 25);
        panel.add(saveButton);

        sendButton = new JButton("Send Letter");
        sendButton.setBounds(400, 500, 150, 25);
        panel.add(sendButton);

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(600, 500, 150, 25);
        exitButton.addActionListener(e -> exitApplication());
        panel.add(exitButton);

        add(panel);

        // Action listeners for buttons
        saveButton.addActionListener(e -> saveLetter());
        sendButton.addActionListener(e -> sendLetter());
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void saveLetter() {
        JOptionPane.showMessageDialog(this, "Letter saved successfully!");
    }

    private void sendLetter() {
        String message = messageArea.getText();
        String email = emailField.getText();
        String audience = privateRadioButton.isSelected() ? "Private" : "Public, but anonymous";

        // Get the selected delivery date
        String deliveryDate = null;
        if (dateChooser.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            deliveryDate = sdf.format(dateChooser.getDate());
        }

        // Email configuration
        String from = "samikshaasodekar20@gmail.com";
        String password = " ";

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Save the letter to the database
            DatabaseHelper db = new DatabaseHelper();
            if ("Private".equals(audience)) {
                db.saveLetter(email, "Private letter (content hidden)", deliveryDate, "", audience);
            } else {
                db.saveLetter(email, message, deliveryDate, "", audience);
            }

            // Send the email
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject("Your Time Capsule Letter");
            mimeMessage.setText("Audience: " + audience + "\n\n" + message);

            Transport.send(mimeMessage);
            JOptionPane.showMessageDialog(this, "Letter sent successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send the letter.");
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
*/
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainPage().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
