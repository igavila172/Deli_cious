package com.pluralsight;

public class Chips extends OrderItem {
    private String flavor;

    public Chips(String flavor) {
        this.flavor = flavor;
    }

    @Override
    public String getReceiptText() {
        return "Chips - " + flavor;
    }

    @Override
    public double getPrice() {
        return 1.50;
    }
}
