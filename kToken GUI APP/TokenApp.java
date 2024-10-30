import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class TokenApp extends JFrame {

    public TokenApp() {
        setTitle("Token Generator & Decoder - Created by kavishka Kalhara");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Load and set the application icon
        ImageIcon icon = new ImageIcon("kToken.png");
        setIconImage(icon.getImage());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Select an option:", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(label, gbc);

        JButton encodeButton = new JButton("Encode File to Token");
        JButton decodeButton = new JButton("Decode Token to File");

        // Set button colors using RGB values
        encodeButton.setBackground(new Color(0, 255, 0)); // Green
        decodeButton.setBackground(new Color(255, 0, 0)); // Red

        encodeButton.addActionListener(this::handleEncode);
        decodeButton.addActionListener(this::handleDecode);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(encodeButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(decodeButton, gbc);
    }

    private void handleEncode(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a File to Encode");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String tokenPath = TokenGenerator.encodeToToken(selectedFile);
                JOptionPane.showMessageDialog(this, "Token generated at: " + tokenPath,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error encoding file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDecode(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a .ktoken File to Decode");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().endsWith(".ktoken")) {
                JOptionPane.showMessageDialog(this, "Please select a valid .ktoken file.",
                        "Invalid File", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String decodedFilePath = TokenDecoder.decodeToken(selectedFile);
                JOptionPane.showMessageDialog(this, "File decoded at: " + decodedFilePath,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error decoding file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TokenApp app = new TokenApp();
            app.setVisible(true);
        });
    }
}