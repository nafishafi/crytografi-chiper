import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class CipherAppSwing {
    public static void main(String[] args) {
        // Membuat JFrame
        JFrame frame = new JFrame("Cipher Encryptor/Decryptor");
        frame.setSize(500, 500); // Ukuran yang lebih besar
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Mengatur layout untuk frame
        frame.setLayout(new BorderLayout());

        // Menambahkan panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Warna background yang lebih modern
        frame.add(mainPanel, BorderLayout.CENTER);

        // Panel untuk form input
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255)); // Background putih
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Menambahkan komponen ke panel
        placeComponents(formPanel);

        // Tambahkan ke panel utama
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Menampilkan frame
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10); // Padding

        // Judul
        JLabel titleLabel = new JLabel("Cipher Encryptor/Decryptor", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 122, 204)); // Warna biru modern
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        // Label untuk input pesan
        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(messageLabel, constraints);

        // Text field untuk input pesan
        JTextField messageText = new JTextField(15);
        messageText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(messageText, constraints);

        // Tombol untuk mengunggah file .txt dengan ikon
        JButton uploadButton = new JButton("Upload .txt File", new ImageIcon("icons/upload.png"));
        uploadButton.setBackground(new Color(0, 122, 204));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(uploadButton, constraints);

        // Label untuk input kunci
        JLabel keyLabel = new JLabel("Key (min. 12 chars):");
        keyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(keyLabel, constraints);

        // Text field untuk input kunci
        JTextField keyText = new JTextField(15);
        keyText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(keyText, constraints);

        // ComboBox untuk memilih cipher
        JLabel cipherLabel = new JLabel("Select Cipher:");
        cipherLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(cipherLabel, constraints);

        String[] ciphers = {"Vigenere", "Playfair", "Hill"};
        JComboBox<String> cipherComboBox = new JComboBox<>(ciphers);
        cipherComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(cipherComboBox, constraints);

        // Tombol untuk enkripsi dengan ikon
        JButton encryptButton = new JButton("Encrypt", new ImageIcon("icons/encrypt.png"));
        encryptButton.setBackground(new Color(0, 153, 51));
        encryptButton.setForeground(Color.WHITE);
        encryptButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(encryptButton, constraints);

        // Tombol untuk dekripsi dengan ikon
        JButton decryptButton = new JButton("Decrypt", new ImageIcon("icons/decrypt.png"));
        decryptButton.setBackground(new Color(204, 51, 0));
        decryptButton.setForeground(Color.WHITE);
        decryptButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(decryptButton, constraints);

        // Label untuk hasil
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(resultLabel, constraints);

        // Text area untuk menampilkan hasil
        JTextArea resultArea = new JTextArea(5, 15);
        resultArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(scrollPane, constraints);

        // Action untuk tombol Upload
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        messageText.setText(stringBuilder.toString().trim());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Action untuk tombol Encrypt
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageText.getText();
                String key = keyText.getText();
                String selectedCipher = (String) cipherComboBox.getSelectedItem();
                if (key.length() >= 12) {
                    String encryptedMessage = "";
                    switch (selectedCipher) {
                        case "Vigenere":
                            encryptedMessage = CipherUtils.vigenereEncrypt(message, key);
                            break;
                        case "Playfair":
                            encryptedMessage = CipherUtils.playfairEncrypt(message, key);
                            break;
                        case "Hill":
                            encryptedMessage = CipherUtils.hillEncrypt(message, key);
                            break;
                    }
                    resultArea.setText(encryptedMessage);
                } else {
                    resultArea.setText("Key must be at least 12 characters");
                }
            }
        });

        // Action untuk tombol Decrypt
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageText.getText();
                String key = keyText.getText();
                String selectedCipher = (String) cipherComboBox.getSelectedItem();
                if (key.length() >= 12) {
                    String decryptedMessage = "";
                    switch (selectedCipher) {
                        case "Vigenere":
                            decryptedMessage = CipherUtils.vigenereDecrypt(message, key);
                            break;
                        case "Playfair":
                            decryptedMessage = CipherUtils.playfairDecrypt(message, key);
                            break;
                        case "Hill":
                            decryptedMessage = CipherUtils.hillDecrypt(message, key);
                            break;
                    }
                    resultArea.setText(decryptedMessage);
                } else {
                    resultArea.setText("Key must be at least 12 characters");
                }
            }
        });
    }
}
