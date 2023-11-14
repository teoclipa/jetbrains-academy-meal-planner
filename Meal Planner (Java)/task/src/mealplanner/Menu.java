package mealplanner;

import mealplanner.dao.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    public static void start() throws Exception {
        boolean isTrue = true;
        while (isTrue) {
            switch (getAction()) {
                case "add":
                    addMeal();
                    break;
                case "show":
                    showMeal();
                    break;
                case "plan":
                    addPlan();
                    break;
                case "save":
                    saveShoppingList();
                    break;
                case "exit":
                    System.out.println("Bye!");
                    isTrue = false;
                    break;
            }
        }
    }

    public static String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to do (add, show, plan, save, exit)?");
        return scanner.nextLine();
    }

    public static void addMeal() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealType = scanner.nextLine();

        boolean mealNotCorrect = true;
        while (mealNotCorrect) {
            switch (mealType) {
                case "breakfast", "dinner", "lunch":
                    mealNotCorrect = false;
                    break;
                default:
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    mealType = scanner.nextLine();
                    break;
            }
        }

        System.out.println("Input the meal's name:");
        String mealName = scanner.nextLine();

        boolean nameNotLetters = true;
        while (nameNotLetters) if (!mealName.matches("^([a-zA-Z]+\\s)*[a-zA-Z]+$")) {
            System.out.println("Wrong format. Use letters only!");
            mealName = scanner.nextLine();
        } else {
            nameNotLetters = false;
        }

        System.out.println("Input the ingredients:");
        String mealIngredients = scanner.nextLine();

        boolean ingredientsNotLetters = true;
        while (ingredientsNotLetters) if (!mealIngredients.matches("^(,?\\s*[A-Za-z]+\\s*)+$")) {
            System.out.println("Wrong format. Use letters only!");
            mealIngredients = scanner.nextLine();
        } else {
            ingredientsNotLetters = false;
        }

        DAO<Meal> mealDAO = new MealDAOImpl();
        Meal meal = new Meal(mealType, mealName);
        mealDAO.insert(meal);

        DAO<Ingredient> ingredientDAO = new IngredientDAOImpl();
        String[] ingredients = mealIngredients.split(",");
        for (String item : ingredients) {
            item = item.trim();
            Ingredient ingredient = new Ingredient(item);
            ingredientDAO.insert(ingredient);
        }
        System.out.println("The meal has been added!");
    }

    public static void showMeal() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String category = scanner.nextLine();

        boolean mealNotCorrect = true;
        while (mealNotCorrect) {
            switch (category) {
                case "breakfast", "dinner", "lunch":
                    mealNotCorrect = false;
                    break;
                default:
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    category = scanner.nextLine();
                    break;
            }
        }

        List<Meal> meals;
        DAO<Meal> mealDAO = new MealDAOImpl();
        meals = mealDAO.getAll(category);
        DAO<Ingredient> ingredientDAO = new IngredientDAOImpl();
        StringBuilder ingredients = new StringBuilder();
        if (meals.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.printf("""
                    Category: %s
                                                
                    """, category);
            meals.forEach(meal -> {
                try {
                    ingredientDAO.getAll(meal.getMealId()).forEach(ingredient -> ingredients.append(ingredient.getIngredient()).append("\n"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("""
                        Name: %s
                        Ingredients:
                        %s
                        """, meal.getMeal(), ingredients);
                ingredients.delete(0, ingredients.length());
            });
        }
    }

    public static void addPlan() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        MealDAOImpl mealDAOImpl = new MealDAOImpl();
        PlanDAOImpl planDAOImpl = new PlanDAOImpl();

        for (DaysOfWeek dayOfWeek : DaysOfWeek.values()) {
            int mealIdBreakfast = 0;
            int mealIdLunch = 0;
            int mealIdDinner = 0;
            String properCaseDayOfWeek = dayOfWeek.toString().substring(0, 1).toUpperCase() + dayOfWeek.toString().substring(1);
            System.out.println(properCaseDayOfWeek);

            for (MealCategory mealCategory : MealCategory.values()) {
                List<Meal> meals = mealDAOImpl.getAllOrdered(mealCategory.toString());
                meals.forEach(meal -> System.out.println(meal.getMeal()));
                System.out.printf("""
                        Choose the %s for %s from the list above:
                        """, mealCategory, properCaseDayOfWeek);

                String mealName = scanner.nextLine();
                boolean mealNotFound = true;
                while (mealNotFound) {
                    if (mealDAOImpl.getByName(mealName) == null) {
                        System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                        mealName = scanner.nextLine();
                    } else {
                        Meal meal = mealDAOImpl.getByName(mealName);

                        switch (mealCategory.toString()) {
                            case "breakfast":
                                mealIdBreakfast = meal.getMealId();
                            case "lunch":
                                mealIdLunch = meal.getMealId();
                            case "dinner":
                                mealIdDinner = meal.getMealId();
                        }
                        mealNotFound = false;
                    }
                }
            }

            Plan plan = new Plan(mealIdBreakfast, mealIdLunch, mealIdDinner, dayOfWeek.toString().toLowerCase());
            try {
                planDAOImpl.insert(plan);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("""
                    Yeah! We planned the meals for %s.
                                            
                    """, properCaseDayOfWeek);
        }
        showPlan();
    }

    public static void showPlan() throws SQLException {
        MealDAOImpl mealDAOImpl = new MealDAOImpl();
        PlanDAOImpl planDAOImpl = new PlanDAOImpl();
        for (DaysOfWeek dayOfWeek : DaysOfWeek.values()) {
            String properCaseDayOfWeek = dayOfWeek.toString().substring(0, 1).toUpperCase() + dayOfWeek.toString().substring(1);
            System.out.println("\n" + properCaseDayOfWeek);
            List<Plan> plans = planDAOImpl.getAll(dayOfWeek.toString());

            for (MealCategory mealCategory : MealCategory.values()) {
                switch (mealCategory.toString()) {
                    case "breakfast":
                        System.out.printf("Breakfast: %s\n", mealDAOImpl.get(plans.get(0).getMealIdBreakfast()).getMeal());
                        break;
                    case "lunch":
                        System.out.printf("Lunch: %s\n", mealDAOImpl.get(plans.get(0).getMealIdLunch()).getMeal());
                        break;
                    case "dinner":
                        System.out.printf("Dinner: %s\n", mealDAOImpl.get(plans.get(0).getMealIdDinner()).getMeal());
                        break;
                }
            }
        }

    }

    private static void saveShoppingList() throws IOException, SQLException {
        if (!planExists()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }

        Map<String, Integer> shoppingList = generateShoppingList();
        writeShoppingListToFile(shoppingList);
    }

    private static boolean planExists() throws SQLException {
        PlanDAOImpl planDAO = new PlanDAOImpl();
        for (DaysOfWeek day : DaysOfWeek.values()) {
            List<Plan> plans = planDAO.getAll(day.toString().toLowerCase());
            if (plans.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    private static Map<String, Integer> generateShoppingList() throws SQLException {
        PlanDAOImpl planDAO = new PlanDAOImpl();
        IngredientDAOImpl ingredientDAO = new IngredientDAOImpl();

        Map<String, Integer> ingredientsCount = new HashMap<>();
        for (DaysOfWeek day : DaysOfWeek.values()) {
            List<Plan> plans = planDAO.getAll(day.toString().toLowerCase());
            if (!plans.isEmpty()) {
                Plan plan = plans.get(0);
                processMealIngredients(ingredientsCount, plan.getMealIdBreakfast(), ingredientDAO);
                processMealIngredients(ingredientsCount, plan.getMealIdLunch(), ingredientDAO);
                processMealIngredients(ingredientsCount, plan.getMealIdDinner(), ingredientDAO);
            }
        }

        return ingredientsCount;
    }

    private static void processMealIngredients(Map<String, Integer> ingredientsCount, int mealId, IngredientDAOImpl ingredientDAO) throws SQLException {
        List<Ingredient> ingredients = ingredientDAO.getAll(mealId);
        for (Ingredient ingredient : ingredients) {
            ingredientsCount.put(ingredient.getIngredient(), ingredientsCount.getOrDefault(ingredient.getIngredient(), 0) + 1);
        }
    }

    private static void writeShoppingListToFile(Map<String, Integer> shoppingList) throws IOException {
        System.out.println("Input a filename:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
                String line = entry.getKey();
                if (entry.getValue() > 1) {
                    line += " x" + entry.getValue();
                }
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Saved!");
    }
}
