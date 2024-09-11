package timecapsule;

import com.toedter.calendar.JDateChooser;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainPage extends JFrame {

    private JTextArea messageArea;
    private JDateChooser dateChooser;
    private JTextField emailField;
    private JComboBox<String> styleComboBox;
    private JComboBox<String> audienceComboBox;
    private JButton saveButton, uploadButton, sendButton;

    public MainPage(String userEmail) {
        setTitle("Time Capsule");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel messageLabel = new JLabel("Write your letter:");
        messageLabel.setBounds(50, 50, 120, 25);
        panel.add(messageLabel);

        messageArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(50, 80, 500, 200);
        panel.add(scrollPane);

        JLabel dateLabel = new JLabel("Delivery Date:");
        dateLabel.setBounds(50, 300, 100, 25);
        panel.add(dateLabel);

        String[] dateOptions = {"6 months", "1 year", "3 years", "5 years", "10 years", "Choose a date"};
        JComboBox<String> dateComboBox = new JComboBox<>(dateOptions);
        dateComboBox.setBounds(150, 300, 200, 25);
        dateComboBox.addActionListener(e -> {
            if ("Choose a date".equals(dateComboBox.getSelectedItem().toString())) {
                dateChooser.setEnabled(true);
            } else {
                dateChooser.setEnabled(false);
            }
        });
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

        JLabel styleLabel = new JLabel("Pick your style:");
        styleLabel.setBounds(50, 400, 100, 25);
        panel.add(styleLabel);

        String[] styles = {"Classic", "Modern", "Bold", "Elegant"};
        styleComboBox = new JComboBox<>(styles);
        styleComboBox.setBounds(150, 400, 150, 25);
        panel.add(styleComboBox);

        JLabel audienceLabel = new JLabel("Select your audience:");
        audienceLabel.setBounds(50, 450, 150, 25);
        panel.add(audienceLabel);

        String[] audiences = {"Private", "Public, but anonymous"};
        audienceComboBox = new JComboBox<>(audiences);
        audienceComboBox.setBounds(200, 450, 200, 25);
        panel.add(audienceComboBox);

        saveButton = new JButton("Save Letter");
        saveButton.setBounds(50, 500, 150, 25);
        panel.add(saveButton);

        uploadButton = new JButton("Upload Multimedia");
        uploadButton.setBounds(220, 500, 150, 25);
        panel.add(uploadButton);

        sendButton = new JButton("Send Letter");
        sendButton.setBounds(400, 500, 150, 25);
        panel.add(sendButton);

        add(panel);

        // Action listeners for buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMultimediaPath();
            }

            private void saveMultimediaPath() {
                // Implement the functionality here if needed
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadMultimedia();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendLetter();
            }
        });
    }

//    public void saveMultimediaPath(String filePath) {
//        String url = "jdbc:mysql://localhost:3306/timecapsule"; 
//        String user = "root"; 
//        String password = "root"; 
//
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//            String query = "INSERT INTO multimedia (file_path) VALUES (?)";
//            try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                stmt.setString(1, filePath);
//                stmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    private void uploadMultimedia() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Comment out or remove this line since we're not saving file paths to the database
            // saveMultimediaPath(filePath);
            // Optionally, you could copy the file to a designated directory
            // File destinationFile = new File("path/to/save/" + selectedFile.getName());
            // Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Multimedia uploaded successfully!");
        }
    }

    private void sendLetter() {
        String message = messageArea.getText();
        String deliveryDate = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String email = emailField.getText();
        String style = styleComboBox.getSelectedItem().toString();
        String audience = audienceComboBox.getSelectedItem().toString();

        // Email configuration
        String from = "samikshaasodekar20@gmail.com";
        String password = "aqez wzqb jpku plwx";
        String host = "smtp.gmail.com"; // SMTP server address

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587"); // Common port for SMTP
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject("Your Time Capsule Letter");
            mimeMessage.setText(message); // Set the email body

            // Send the email
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

