package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


public class LandingPage {

    private JFrame frame = new JFrame();
    private JLabel welcomeLabel = new JLabel("Área Administrativa");
    private JLabel nameLabel = new JLabel("Nome do Item:");
    private JLabel priceLabel = new JLabel("Preço do Item:");
    private JTextField nameField = new JTextField();
    private JTextField priceField = new JTextField();
    private JButton saveButton = new JButton("Salvar Item");
    private JButton listButton = new JButton("Listar Itens");
    private JButton deleteButton = new JButton("Excluir Item");
    private JButton addUserButton = new JButton("Cadastrar Usuário");

    private JTable itemListTable;
    private DefaultTableModel tableModel;

    private List<String> itemList = new ArrayList<>();
    private String usuarioLogado;

    private static final String ALLOWED_USER1 = "Rodrigo";
    private static final String ALLOWED_USER2 = "Daniel";

    private static final String USERS_FILE = "usuarios.txt";

    private Map<String, String> usuarios = new HashMap<>();

    public LandingPage(String userID) {
        this.usuarioLogado = userID;

        welcomeLabel.setBounds(0, 0, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomeLabel.setText("Olá " + userID);

        nameLabel.setBounds(50, 70, 150, 20);
        nameField.setBounds(200, 70, 150, 20);

        priceLabel.setBounds(50, 100, 150, 20);
        priceField.setBounds(200, 100, 150, 20);

        saveButton.setBounds(200, 150, 120, 30);
        listButton.setBounds(50, 150, 120, 30);
        deleteButton.setBounds(350, 150, 120, 30);
        addUserButton.setBounds(200, 200, 120, 30);

        frame.add(welcomeLabel);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(saveButton);
        frame.add(listButton);
        frame.add(deleteButton);
        frame.add(addUserButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Preço");
        itemListTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemListTable);
        scrollPane.setBounds(50, 250, 420, 150);
        frame.add(scrollPane);

        loadUsuarios();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (podeAcessar(usuarioLogado)) {
                    String nomeItem = nameField.getText();
                    String precoItem = priceField.getText();

                    if (!nomeItem.isEmpty() && !precoItem.isEmpty()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("itens.txt", true))) {
                            writer.write("Nome do Item: " + nomeItem + ", Preço: " + precoItem);
                            writer.newLine();
                            writer.flush();
                            JOptionPane.showMessageDialog(null, "Item salvo com sucesso!");
                            nameField.setText("");
                            priceField.setText("");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro ao salvar o item.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso não permitido para este usuário.");
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarItens();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (podeAcessar(usuarioLogado)) {
                    int selectedRow = itemListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String itemToRemove = itemList.get(selectedRow);
                        if (removerItem(itemToRemove)) {
                            JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
                            listarItens();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao excluir o item.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione um item para excluir.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso não permitido para este usuário.");
                }
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (podeAcessar(usuarioLogado)) {
                    cadastrarUsuario();
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso não permitido para este usuário.");
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private boolean podeAcessar(String usuario) {
        return usuario.equals(ALLOWED_USER1) || usuario.equals(ALLOWED_USER2);
    }

    private void listarItens() {
        itemList.clear();
        tableModel.setRowCount(0);

        try (BufferedReader reader = new BufferedReader(new FileReader("itens.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                itemList.add(line);
                String[] parts = line.split(",");
                String nome = parts[0].trim().substring(13);
                String preco = parts[1].trim().substring(7);
                tableModel.addRow(new String[]{nome, preco});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar os itens.");
        }
    }

    private boolean removerItem(String itemToRemove) {
        List<String> updatedItemList = new ArrayList<>(itemList);
        updatedItemList.remove(itemToRemove);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("itens.txt"))) {
            for (String item : updatedItemList) {
                writer.write(item);
                writer.newLine();
            }
            writer.flush();
            itemList = updatedItemList;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    usuarios.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar os usuários.");
        }
    }

    private void salvarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Map.Entry<String, String> entry : usuarios.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar os usuários.");
        }
    }

    private void cadastrarUsuario() {
        String novoUsuario = JOptionPane.showInputDialog("Digite o nome do novo usuário:");
        if (novoUsuario != null && !novoUsuario.isEmpty()) {
            String senha = JOptionPane.showInputDialog("Digite a senha do novo usuário:");
            if (senha != null && !senha.isEmpty()) {
                usuarios.put(novoUsuario, senha);
                salvarUsuarios();
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Senha inválida.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome de usuário inválido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LandingPage("Rodrigo");
            // new LandingPage("Daniel");
            // new LandingPage("OutroUsuario");
        });
    }
}
