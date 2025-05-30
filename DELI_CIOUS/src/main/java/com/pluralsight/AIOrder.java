package com.pluralsight;

import java.util.List;

public class AIOrder {
    private List<SandwichRequest> sandwiches;
    private List<DrinkRequest> drinks;
    private List<ChipRequest> chips;

    public List<SandwichRequest> getSandwiches() { return sandwiches; }
    public List<DrinkRequest> getDrinks() { return drinks; }
    public List<ChipRequest> getChips() { return chips; }

    public static class SandwichRequest {
        public String type;
        public String size;
        public String bread;
        public boolean toasted;
    }

    public static class DrinkRequest {
        public String flavor;
        public String size;
    }

    public static class ChipRequest {
        public String type;
    }
}
