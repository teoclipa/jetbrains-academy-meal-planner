package mealplanner.dao;

public class Plan {
    private int planId;
    private int mealIdBreakfast;
    private int mealIdLunch;
    private int mealIdDinner;
    private String dayOfWeek;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getMealIdBreakfast() {
        return mealIdBreakfast;
    }

    public void setMealIdBreakfast(int mealIdBreakfast) {
        this.mealIdBreakfast = mealIdBreakfast;
    }

    public int getMealIdLunch() {
        return mealIdLunch;
    }

    public void setMealIdLunch(int mealIdLunch) {
        this.mealIdLunch = mealIdLunch;
    }

    public int getMealIdDinner() {
        return mealIdDinner;
    }

    public void setMealIdDinner(int mealIdDinner) {
        this.mealIdDinner = mealIdDinner;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Plan(int mealIdBreakfast, int mealIdLunch, int mealIdDinner, String dayOfWeek) {
        this.mealIdBreakfast = mealIdBreakfast;
        this.mealIdLunch = mealIdLunch;
        this.mealIdDinner = mealIdDinner;
        this.dayOfWeek = dayOfWeek;
    }

    public Plan(int planId, int mealIdBreakfast, int mealIdLunch, int mealIdDinner, String dayOfWeek) {
        this.planId = planId;
        this.mealIdBreakfast = mealIdBreakfast;
        this.mealIdLunch = mealIdLunch;
        this.mealIdDinner = mealIdDinner;
        this.dayOfWeek = dayOfWeek;
    }
}