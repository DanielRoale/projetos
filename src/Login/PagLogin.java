package Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PagLogin implements ActionListener {

    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userSenha = new JPasswordField();
    JLabel userIDLabel = new JLabel("Login");
    JLabel userPassLabel = new JLabel("Senha");
    JLabel messageLabel = new JLabel();
    HashMap<String, String> logininfo = new HashMap<String, String>();

    PagLogin(HashMap<String, String> loginOriginal) {

        logininfo = loginOriginal;

        userIDLabel.setBounds(50, 100, 75, 25);
        userPassLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userIDField.setBounds(125, 100, 200, 25);
        userSenha.setBounds(125, 150, 200, 25);

        loginButton.setBounds(125, 200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225, 200, 100, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPassLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userSenha);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userSenha.setText("");
            messageLabel.setText("");
        }

        if (e.getSource() == loginButton) {

            String userID = userIDField.getText();
            String password = String.valueOf(userSenha.getPassword());

            if (logininfo.containsKey(userID)) {
                if (logininfo.get(userID).equals(password)) {
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Sucesso no Login");
                    frame.dispose();
                    LandingPage welcomePage = new LandingPage(userID);
                } else {
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Senha Errada");
                }
            } else if (verificarUsuarioNoArquivo(userID, password)) {
                messageLabel.setForeground(Color.green);
                messageLabel.setText("Sucesso no Login");
                frame.dispose();
                LandingPageUsuario welcomePage = new LandingPageUsuario(userID);
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Credenciais Inválidas");
            }
        }
    }

    private boolean verificarUsuarioNoArquivo(String userID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(userID) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Credencial credencial = new Credencial(); // Crie uma instância de Credencial
            new PagLogin(credencial.getLoginInfo());
        });
    }
}
