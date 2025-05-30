package com.pluralsight;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class OrderParser {

    public static Sandwich parseSandwichFromJson(JsonObject sandwichJson) {
        try {
            System.out.println("\nParsing sandwich JSON: " + sandwichJson);

            if (!sandwichJson.has("size") || !sandwichJson.has("bread") || !sandwichJson.has("toasted")) {
                throw new IllegalArgumentException("Missing required sandwich fields: size, bread, toasted");
            }

            int size = sandwichJson.get("size").getAsInt();
            String bread = sandwichJson.get("bread").getAsString();
            boolean toasted = sandwichJson.get("toasted").getAsBoolean();

            List<String> meats = getListFromJsonArray(sandwichJson.getAsJsonArray("meats"));
            List<String> cheeses = getListFromJsonArray(sandwichJson.getAsJsonArray("cheeses"));
            List<String> toppings = getListFromJsonArray(sandwichJson.getAsJsonArray("toppings"));
            List<String> sauces = getListFromJsonArray(sandwichJson.getAsJsonArray("sauces"));

            return new Sandwich(
                    String.valueOf(size),
                    bread,
                    meats,
                    cheeses,
                    toppings,
                    sauces,
                    toasted
            );
        } catch (Exception e) {
            System.out.println("[Error] Could not parse sandwich: " + e.getMessage());
            throw e;
        }
    }

    private static List<String> getListFromJsonArray(JsonArray array) {
        List<String> list = new ArrayList<>();
        if (array == null || array.isJsonNull()) return list;
        for (JsonElement element : array) {
            list.add(element.getAsString());
        }
        return list;
    }

    public static String getMenuInstructionPrompt() {
        return "You are helping build sandwich orders for a deli. Use ONLY these ingredients.\n" +
                "\nBread: white, wheat, rye, wrap" +
                "\nSizes: 4, 8, 12 (inches)" +
                "\nMeats: steak, ham, salami, roast beef, chicken, bacon" +
                "\nCheeses: american, provolone, cheddar, swiss" +
                "\nToppings: lettuce, peppers, onions, tomatoes, jalapeños, cucumbers, pickles, guacamole, mushrooms" +
                "\nSauces: mayo, mustard, ketchup, ranch, thousand islands, vinaigrette" +
                "\n\nAlways include all fields (meats, cheeses, toppings, sauces) even if empty. Always include toasted as true or false.\n" +
                "Return ONLY a single JSON object or an array of objects in this format: " +
                "{\"type\":\"sandwich\", \"size\":\"8\", \"bread\":\"white\", \"meats\":[], \"cheeses\":[], \"toppings\":[], \"sauces\":[], \"toasted\":false}";
    }

    public static String buildAIPrompt(String userOrder) {
        return getMenuInstructionPrompt() + "\n\nOrder: " + userOrder;
    }
}
