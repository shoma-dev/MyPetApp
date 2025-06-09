# 🐾 MyPetApp - Java × MySQL ペット管理アプリ

## 📌 アプリ概要
Java + MySQLで作成したペット管理アプリ（簡易バージョン）です。  
GUIでペットの「名前・種類・年齢」を登録してデータベースに保存できます。


## 🚀 使用技術
- Java 17
- MySQL 8.x
- JDBC（mysql-connector）
- Swing

## 🛠️ セットアップ＆実行方法

### ① データベースの作成（MySQL）
```sql
CREATE DATABASE mypetdb;
USE mypetdb;

CREATE TABLE pets (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  type VARCHAR(50),
  age INT
);
