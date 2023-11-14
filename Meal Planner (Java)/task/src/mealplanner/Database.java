package mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private Database() {
    }

    public static void create() throws SQLException {
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement st = con.createStatement();
        con.setAutoCommit(true);
        st.executeUpdate("CREATE TABLE IF NOT EXISTS meals (" +
                "category VARCHAR(30)," +
                "meal VARCHAR(30)," +
                "meal_id INTEGER PRIMARY KEY" +
                ")");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (" +
                "ingredient VARCHAR(30)," +
                "ingredient_id INTEGER PRIMARY KEY," +
                "meal_id INTEGER)");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS plan (" +
                "plan_id INTEGER PRIMARY KEY," +
                "meal_id_breakfast INTEGER," +
                "meal_id_lunch INTEGER," +
                "meal_id_dinner INTEGER," +
                "day_of_week VARCHAR(30))");
        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS meal_sequence start 1 increment 1 cache 1");
        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ingredient_sequence start 1 increment 1 cache 1");
        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS plan_sequence start 1 increment 1 cache 1");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}