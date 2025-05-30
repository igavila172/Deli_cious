package com.pluralsight;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuService {

    // Store custom signature sandwiches
    private static final List<CustomSignatureSandwich> customSandwiches = new ArrayList<>();

    // === STATIC MENU OPTIONS ===

    public static void printBreadOptions() {
        System.out.println("Available Breads:");
        System.out.println("- White\n- Wheat\n- Rye\n- Wrap");
    }

    public static void printSandwichSizes() {
        System.out.println("Available Sandwich Sizes:");
        System.out.println("- 4\" ($5.50)\n- 8\" ($7.00)\n- 12\" ($8.50)");
    }

    public static void printMeats() {
        System.out.println("Premium Meats:");
        System.out.println("- Steak\n- Ham\n- Salami\n- Roast Beef\n- Chicken\n- Bacon");
    }

    public static void printCheeses() {
        System.out.println("Premium Cheeses:");
        System.out.println("- American\n- Provolone\n- Cheddar\n- Swiss");
    }

    public static void printToppings() {
        System.out.println("Regular Toppings:");
        System.out.println("- Lettuce\n- Peppers\n- Onions\n- Tomatoes\n- Jalapeños\n- Cucumbers\n- Pickles\n- Guacamole\n- Mushrooms");
    }

    public static void printSauces() {
        System.out.println("Available Sauces:");
        System.out.println("- Mayo\n- Mustard\n- Ketchup\n- Ranch\n- Thousand Islands\n- Vinaigrette");
    }

    public static void printDrinks() {
        System.out.println("Drink Sizes:");
        System.out.println("- Small ($2.00)\n- Medium ($2.50)\n- Large ($3.00)");
    }

    public static void printChips() {
        System.out.println("Chips ($1.50):\n- BBQ\n- Sour Cream\n- Plain\n- Jalapeño\n- Salt & Vinegar");
    }

    // === CUSTOM SIGNATURE SANDWICH HANDLING ===

    public static void addCustomSignatureSandwich(CustomSignatureSandwich sandwich) {
        customSandwiches.add(sandwich);
    }

    public static void printSavedCustomSignatures() {
        if (customSandwiches.isEmpty()) {
            System.out.println("No custom Signature Sandwiches saved yet.");
            return;
        }

        int index = 3; // Start numbering after 1) BLT, 2) Philly
        for (CustomSignatureSandwich s : customSandwiches) {
            System.out.println(index++ + ") " + s.getName());
        }
    }

    public static CustomSignatureSandwich getCustomSignatureSandwich(int index) {
        return customSandwiches.get(index - 3); // Adjust since BLT and Philly are 1 and 2
    }

    public static int getCustomSignatureCount() {
        return customSandwiches.size();
    }

    // === FILE PERSISTENCE ===

    public static void saveCustomSignaturesToFile() {
        try (Writer writer = new FileWriter("signatures.json")) {
            new Gson().toJson(customSandwiches, writer);
        } catch (IOException e) {
            System.out.println("Failed to save custom sandwiches: " + e.getMessage());
        }
    }

    public static void loadCustomSignaturesFromFile() {
        File file = new File("signatures.json");
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            List<CustomSignatureSandwich> saved = new Gson().fromJson(reader,
                    new TypeToken<List<CustomSignatureSandwich>>() {}.getType());
            if (saved != null) {
                customSandwiches.clear();
                customSandwiches.addAll(saved);
            }
        } catch (IOException e) {
            System.out.println("Failed to load custom sandwiches: " + e.getMessage());
        }
    }
}
