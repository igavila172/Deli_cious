package com.pluralsight;

import java.util.List;

public class BLT extends SignatureSandwich {

    public BLT() {
        super(8, "white", true); // 8" white, toasted
    }

    @Override
    protected void setDefaults() {
        setMeats(List.of("bacon"));
        setCheeses(List.of("cheddar"));
        setRegularToppings(List.of("lettuce", "tomatoes"));
        setSauces(List.of("ranch"));
    }

    @Override
    public String getName() {
        return "BLT (" + super.getSize() + "\" " + super.getBread() + ")";
    }
}
