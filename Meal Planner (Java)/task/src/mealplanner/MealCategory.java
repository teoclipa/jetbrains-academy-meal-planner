package mealplanner;

public enum MealCategory {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}