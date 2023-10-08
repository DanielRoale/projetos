package Login;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class LandingPageUsuario {

    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Área do Usuário");
    JTextArea itemListArea = new JTextArea();
    
    LandingPageUsuario(String userID) {

        welcomeLabel.setBounds(0, 0, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomeLabel.setText("Olá " + userID);

        itemListArea.setBounds(50, 50, 300, 250);
        itemListArea.setEditable(false);
        itemListArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(itemListArea);
        scrollPane.setBounds(50, 50, 300, 250);

        frame.add(welcomeLabel);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

        carregarItens();
    }

    private void carregarItens() {
        try (BufferedReader reader = new BufferedReader(new FileReader("itens.txt"))) {
            String line;
            StringBuilder itemList = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                itemList.append(line).append("\n");
            }

            if (itemList.length() > 0) {
                itemListArea.setText(itemList.toString());
            } else {
                itemListArea.setText("Não existem mais itens disponíveis.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os itens.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LandingPageUsuario("Nome do Usuário");
        });
    }
}
