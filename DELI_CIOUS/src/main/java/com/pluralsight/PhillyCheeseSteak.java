package com.pluralsight;

import java.util.List;

public class PhillyCheeseSteak extends SignatureSandwich {

    public PhillyCheeseSteak() {
        super(8, "white", true); // 8" white, toasted
    }

    @Override
    protected void setDefaults() {
        setMeats(List.of("steak"));
        setCheeses(List.of("american"));
        setRegularToppings(List.of("peppers"));
        setSauces(List.of("mayo"));
    }

    @Override
    public String getName() {
        return "Philly Cheese Steak (" + super.getSize() + "\" " + super.getBread() + ")";
    }
}
