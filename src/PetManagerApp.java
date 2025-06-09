import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PetManagerApp extends JFrame {

    private JTextField nameField, typeField, ageField;
    private JTextArea displayArea;

    public PetManagerApp() {
        setTitle("ペット管理アプリ");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 入力パネル
        JPanel inputPanel = new JPanel(new FlowLayout());

        nameField = new JTextField(10);
        typeField = new JTextField(10);
        ageField = new JTextField(3);
        JButton addButton = new JButton("登録");

        inputPanel.add(new JLabel("名前:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("種類:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("年齢:"));
        inputPanel.add(ageField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // 表示エリア
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // ボタン動作
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String type = typeField.getText();
            int age;

            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "年齢は数字で入力してください");
                return;
            }

            insertPetData(name, type, age);
            displayPets();  // 登録後に一覧更新
        });

        setVisible(true);
    }

    private void insertPetData(String name, String type, int age) {
        String url = "jdbc:mysql://localhost:3306/mypetdb";
        String user = "root";
        String password = "kashima1125";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypetdb", "root", "kashima1125")) {
            String sql = "INSERT INTO pets (name, type, age) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setInt(3, age);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ 登録に成功しました！");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ データベースエラー: " + e.getMessage());
        }
    }

    private void displayPets() {
        String url = "jdbc:mysql://localhost:3306/mypetdb";
        String user = "root";
        String password = "kashima1125";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypetdb", "root", "kashima1125")) {
            String sql = "SELECT * FROM pets";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(String.format("ID: %d / 名前: %s / 種類: %s / 年齢: %d\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("age")));
            }

            displayArea.setText(sb.toString());

        } catch (SQLException e) {
            displayArea.setText("データ読み込みエラー: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PetManagerApp());
    }
}