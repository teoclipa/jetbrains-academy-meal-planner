package mealplanner.dao;

import mealplanner.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDAOImpl implements DAO<Plan> {

    @Override
    public Plan get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Plan> getAll(Object input) throws SQLException {
        Connection con = Database.getConnection();
        List<Plan> plans = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM plan WHERE day_of_week = ?");
        ps.setString(1, String.valueOf(input));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int planId = rs.getInt("plan_id");
            int mealIdBreakfast = rs.getInt("meal_id_breakfast");
            int mealIdLunch = rs.getInt("meal_id_lunch");
            int mealIdDinner = rs.getInt("meal_id_dinner");
            String dayOfWeek = rs.getString("day_of_week");
            Plan plan = new Plan(planId, mealIdBreakfast, mealIdLunch, mealIdDinner, dayOfWeek);
            plans.add(plan);
        }
        return plans;
    }

    @Override
    public int insert(Plan plan) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO plan VALUES (nextval('plan_sequence'), ?, ?, ?, ?)");
        ps.setInt(1, plan.getMealIdBreakfast());
        ps.setInt(2, plan.getMealIdLunch());
        ps.setInt(3, plan.getMealIdDinner());
        ps.setString(4, plan.getDayOfWeek());
        int result = ps.executeUpdate();
        con.close();
        return result;
    }

    @Override
    public int update(Plan plan) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Plan plan) throws SQLException {
        return 0;
    }
}