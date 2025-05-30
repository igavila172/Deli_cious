package com.pluralsight;

import java.util.List;

public abstract class SignatureSandwich extends Sandwich {

    protected List<String> defaultMeats;
    protected List<String> defaultCheeses;
    protected List<String> defaultToppings;
    protected List<String> defaultSauces;

    public SignatureSandwich(int size, String bread, boolean toasted) {
        super(String.valueOf(size), bread, List.of(), List.of(), List.of(), List.of(), toasted);
        setDefaults();
        defaultMeats = List.copyOf(this.meats);
        defaultCheeses = List.copyOf(this.cheeses);
        defaultToppings = List.copyOf(this.toppings);
        defaultSauces = List.copyOf(this.sauces);
    }

    protected abstract void setDefaults();

    public abstract String getName();

    @Override
    public String getReceiptText() {
        StringBuilder receipt = new StringBuilder("Signature: " + getName() + " (" + size + "\" " + bread + ")");
        if (toasted) {
            receipt.append(" (Toasted)");
        }
        receipt.append("\nMeats: ").append(String.join(", ", meats));
        receipt.append("\nCheeses: ").append(String.join(", ", cheeses));
        receipt.append("\nToppings: ").append(String.join(", ", toppings));
        receipt.append("\nSauces: ").append(String.join(", ", sauces));
        return receipt.toString();
    }
}
