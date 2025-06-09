import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class MySQLTest {
    public static void main(String[] args) {
        // 接続情報を設定
        String url = "jdbc:mysql://localhost:3306/mypetdb"; // ここで使うDB名を指定（事前に作成しておく）
        String user = "root"; // ユーザー名
        String password = "kashima1125"; // パスワード（設定している場合はここに）

        try {
            // データベースに接続
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypetdb", "root", "kashima1125");
            System.out.println("✅ 接続成功！");

            // ステートメントを作成
            Statement stmt = conn.createStatement();

            // テーブル作成SQL（存在しなければ作る）
            String createTableSql = "CREATE TABLE IF NOT EXISTS pets (" +
                                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                                    "name VARCHAR(50)," +
                                    "type VARCHAR(50)," +
                                    "age INT)";
            stmt.executeUpdate(createTableSql);
            System.out.println("✅ テーブル作成完了");

            // データ挿入SQL
            String insertSql = "INSERT INTO pets (name, type, age) VALUES ('ポチ', '犬', 3)";
            stmt.executeUpdate(insertSql);
            System.out.println("✅ データ挿入完了");

            // 接続を閉じる
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ 接続失敗: " + e.getMessage());
        }
    }
}