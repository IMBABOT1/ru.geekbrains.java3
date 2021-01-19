package Sqllite;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;

    public static void connect() throws  ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        statement = connection.createStatement();
    }

    public static void disconnect(){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            connect();
            //insertInto();
           // update();
            //delete();
           // dropTable();
            //createTable();
         //   fillTableExample();
            //select();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            disconnect();
        }
    }

    private static void select() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM STUDENTS");
        while (rs.next()){
            System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getString("score"));
        }
    }

    private static void fillTableExample() throws SQLException{
        long time = System.currentTimeMillis();
        ps = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?)");
        connection.setAutoCommit(false);
        for (int i = 1; i <= 1000000 ; i++) {
            ps.setString(1, "Bob" + i);
            ps.setInt(2, + 50);
            ps.executeUpdate();
        }
        connection.commit();
        System.out.println(System.currentTimeMillis() - time);
    }

    private static void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER)");
    }

    private static void dropTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS STUDENTS");
    }

    private static void delete() throws SQLException {
        statement.executeUpdate("DELETE FROM students WHERE ID = 1");
    }

    private static void update() throws SQLException {
        statement.executeUpdate("UPDATE students SET score = 200 WHERE id=1");
    }

    private static void insertInto() throws SQLException {
        statement.executeUpdate("INSERT INTO Students (name, score) VALUES ('BOB10', '100')");
    }
}
