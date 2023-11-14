package mealplanner.dao;

import mealplanner.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealDAOImpl implements DAO<Meal> {

    @Override
    public Meal get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Meal meal = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM meals WHERE meal_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String category = rs.getString("category");
            String mealName = rs.getString("meal");
            int mealId = rs.getInt("meal_id");
            meal = new Meal(category, mealName, mealId);
        }
        con.close();
        return meal;
    }

    @Override
    public List<Meal> getAll(Object input) throws SQLException {
        Connection con = Database.getConnection();
        List<Meal> meals = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM meals WHERE category = ?");
        ps.setString(1, String.valueOf(input));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String mealCategory = rs.getString("category");
            String mealName = rs.getString("meal");
            int mealId = rs.getInt("meal_id");
            Meal meal = new Meal(mealCategory, mealName, mealId);
            meals.add(meal);
        }
        return meals;
    }

    public List<Meal> getAllOrdered(Object input) throws SQLException {
        Connection con = Database.getConnection();
        List<Meal> meals = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM meals WHERE category = ? ORDER BY meal");
        ps.setString(1, String.valueOf(input));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String mealCategory = rs.getString("category");
            String mealName = rs.getString("meal");
            int mealId = rs.getInt("meal_id");
            Meal meal = new Meal(mealCategory, mealName, mealId);
            meals.add(meal);
        }
        return meals;
    }

    public Meal getByName(String input) throws SQLException {
        Connection con = Database.getConnection();
        Meal meal = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM meals WHERE meal = ?");
        ps.setString(1, input);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String mealCategory = rs.getString("category");
            String mealName = rs.getString("meal");
            int mealId = rs.getInt("meal_id");
            meal = new Meal(mealCategory, mealName, mealId);
        }
        con.close();
        return meal;
    }

    @Override
    public int insert(Meal meal) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO meals VALUES (?, ?, nextval('meal_sequence'))");
        ps.setString(1, meal.getCategory());
        ps.setString(2, meal.getMeal());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }

    @Override
    public int update(Meal meal) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE meals SET category = ?, meal = ? WHERE meal_id = ?");
        ps.setString(1, meal.getCategory());
        ps.setString(2, meal.getMeal());
        ps.setInt(3, meal.getMealId());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }

    @Override
    public int delete(Meal meal) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM meals WHERE meal_id = ?");
        ps.setInt(1, meal.getMealId());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }
}