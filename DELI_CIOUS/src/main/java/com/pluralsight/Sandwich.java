package com.pluralsight;

import java.util.List;

public class Sandwich extends OrderItem {
    protected String size;
    protected String bread;
    protected List<String> meats;
    protected List<String> cheeses;
    protected List<String> toppings;
    protected List<String> sauces;
    protected boolean toasted;

    public Sandwich(String size, String bread, List<String> meats, List<String> cheeses,
                    List<String> toppings, List<String> sauces, boolean toasted) {
        this.size = size;
        this.bread = bread;
        this.meats = meats;
        this.cheeses = cheeses;
        this.toppings = toppings;
        this.sauces = sauces;
        this.toasted = toasted;
    }

    @Override
    public String getReceiptText() {
        return String.format("%s\" %s Sandwich%s\nMeats: %s\nCheeses: %s\nToppings: %s\nSauces: %s",
                size, bread, toasted ? " (Toasted)" : "",
                String.join(", ", meats),
                String.join(", ", cheeses),
                String.join(", ", toppings),
                String.join(", ", sauces));
    }

    @Override
    public double getPrice() {
        double basePrice = switch (size) {
            case "4" -> 5.50;
            case "8" -> 7.00;
            case "12" -> 8.50;
            default -> 0.0;
        };

        double meatPrice = switch (size) {
            case "4" -> 1.00;
            case "8" -> 2.00;
            case "12" -> 3.00;
            default -> 0.0;
        } * meats.size();

        double cheesePrice = switch (size) {
            case "4" -> 0.75;
            case "8" -> 1.50;
            case "12" -> 2.25;
            default -> 0.0;
        } * cheeses.size();

        return basePrice + meatPrice + cheesePrice;
    }

    public String getSize() {
        return size;
    }

    public String getBread() {
        return bread;
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }
    public void setMeats(List<String> meats) {
        this.meats = meats;
    }

    public void setCheeses(List<String> cheeses) {
        this.cheeses = cheeses;
    }

    public void setRegularToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public void setSauces(List<String> sauces) {
        this.sauces = sauces;
    }

    public List<String> getMeats() {
        return meats;
    }

    public List<String> getCheeses() {
        return cheeses;
    }

    public List<String> getRegularToppings() {
        return toppings;
    }

    public List<String> getSauces() {
        return sauces;
    }


}
