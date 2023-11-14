package mealplanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.create();
        Menu.start();
    }
}