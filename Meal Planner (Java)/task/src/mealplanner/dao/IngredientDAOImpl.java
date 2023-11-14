package mealplanner.dao;

import mealplanner.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements DAO<Ingredient> {

    @Override
    public Ingredient get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Ingredient ingredient = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM ingredients WHERE ingredient_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String ingredientName = rs.getString("ingredient");
            int ingredientId = rs.getInt("ingredient_id");
            int mealId = rs.getInt("meal_id");
            ingredient = new Ingredient(ingredientName, ingredientId, mealId);
        }
        con.close();
        return ingredient;
    }

    @Override
    public List<Ingredient> getAll(Object input) throws SQLException {
        Connection con = Database.getConnection();
        List<Ingredient> ingredients = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM ingredients WHERE meal_id = ?");
        ps.setInt(1, Integer.parseInt(String.valueOf(input)));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String ingredientName = rs.getString("ingredient");
            int ingredientId = rs.getInt("ingredient_id");
            int mealId = rs.getInt("meal_id");
            Ingredient ingredient = new Ingredient(ingredientName, ingredientId, mealId);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    @Override
    public int insert(Ingredient ingredient) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO ingredients VALUES (?, nextval('ingredient_sequence'), (SELECT last_value FROM meal_sequence))");
        ps.setString(1, ingredient.getIngredient());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }

    @Override
    public int update(Ingredient ingredient) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE ingredients SET ingredient = ?, meal_id = ? WHERE ingredient_id = ?");
        ps.setString(1, ingredient.getIngredient());
        ps.setInt(2, ingredient.getMealId());
        ps.setInt(3, ingredient.getIngredientId());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }

    @Override
    public int delete(Ingredient ingredient) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM ingredients WHERE ingredient_id = ?");
        ps.setInt(1, ingredient.getIngredientId());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }
}