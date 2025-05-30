package com.pluralsight;

import java.util.List;

public class CustomSignatureSandwich extends SignatureSandwich {
    private final String name;

    public CustomSignatureSandwich(String name, String size, String bread,
                                   List<String> meats, List<String> cheeses,
                                   List<String> toppings, List<String> sauces,
                                   boolean toasted) {
        super(Integer.parseInt(size), bread, toasted);
        this.name = name;
        this.meats = meats;
        this.cheeses = cheeses;
        this.toppings = toppings;
        this.sauces = sauces;
        setDefaults(); // capture current state for modification tracking
    }

    @Override
    protected void setDefaults() {
        this.defaultMeats = List.copyOf(meats);
        this.defaultCheeses = List.copyOf(cheeses);
        this.defaultToppings = List.copyOf(toppings);
        this.defaultSauces = List.copyOf(sauces);
    }

    @Override
    public String getName() {
        return name;
    }
}
