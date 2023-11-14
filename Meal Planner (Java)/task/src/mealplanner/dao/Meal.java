package mealplanner.dao;

public class Meal {

    private final String category;
    private final String meal;
    private int mealId;

    public String getCategory() {
        return category;
    }

    public String getMeal() {
        return meal;
    }

    public int getMealId() {
        return mealId;
    }

    public Meal(String category, String meal) {
        this.category = category;
        this.meal = meal;
    }

    public Meal(String category, String meal, int mealId) {
        this.category = category;
        this.meal = meal;
        this.mealId = mealId;
    }
}