package mealplanner.dao;

public class Ingredient {
    private final String ingredient;
    private int ingredientId;
    private int mealId;

    public String getIngredient() {
        return ingredient;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public int getMealId() {
        return mealId;
    }

    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient(String ingredient, int ingredientId, int mealId) {
        this.ingredient = ingredient;
        this.ingredientId = ingredientId;
        this.mealId = mealId;
    }
}