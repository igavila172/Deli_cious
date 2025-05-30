package com.pluralsight;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuService.loadCustomSignaturesFromFile();
        Order currentOrder = null;

        while (true) {
            System.out.println("\nWelcome to DELI-cious!");
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    currentOrder = new Order();
                    manageOrder(scanner, currentOrder);
                    break;
                case "0":
                    System.out.println("Thank you for visiting DELI-cious!");
                    MenuService.saveCustomSignaturesToFile();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageOrder(Scanner scanner, Order order) {
        while (true) {
            System.out.println("\nOrder Menu:");
            System.out.println("1) Add Sandwich");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Chips");
            System.out.println("4) Checkout");
            System.out.println("5) Use AI to add items");
            System.out.println("6) Add Signature Sandwich");
            System.out.println("0) Cancel Order");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Sandwich sandwich = buildSandwich(scanner);
                    order.addItem(sandwich);
                    break;
                case "2":
                    MenuService.printDrinks();
                    System.out.println("Enter drink size:");
                    String drinkSize = scanner.nextLine().trim();
                    System.out.println("Enter drink flavor:");
                    String drinkFlavor = scanner.nextLine().trim();
                    order.addItem(new Drink(drinkSize, drinkFlavor));
                    break;
                case "3":
                    MenuService.printChips();
                    System.out.println("Enter chip flavor:");
                    String chipFlavor = scanner.nextLine().trim();
                    order.addItem(new Chips(chipFlavor));
                    break;
                case "4":
                    System.out.println("\n----- REVIEW YOUR ORDER -----");
                    for (OrderItem item : order.getItems()) {
                        System.out.println(item.getReceiptText());
                        System.out.println("-------------------");
                    }

                    System.out.println("Do you want to proceed to checkout? (yes/no):");
                    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        ReceiptService.printReceipt(order);
                        ReceiptService.saveReceiptToFile(order);
                        return;
                    } else {
                        System.out.println("Returning to order menu...");
                    }
                    break;

                case "5":
                    System.out.println("Enter your full natural language order:");
                    String naturalInput = scanner.nextLine();

                    try {
                        String jsonResponse = OpenAIClient.getOrderFromNaturalLanguage(naturalInput);
                        Gson gson = new Gson();
                        AIOrder aiOrder = gson.fromJson(jsonResponse, AIOrder.class);

                        if (aiOrder.getSandwiches() != null) {
                            for (AIOrder.SandwichRequest s : aiOrder.getSandwiches()) {
                                Sandwich aiSandwich;

                                if (s.type.equalsIgnoreCase("BLT")) {
                                    aiSandwich = new BLT();
                                } else if (s.type.equalsIgnoreCase("Philly Cheese Steak")) {
                                    aiSandwich = new PhillyCheeseSteak();
                                } else {
                                    List<String> meats = new ArrayList<>();
                                    List<String> cheeses = new ArrayList<>();
                                    List<String> toppings = new ArrayList<>();
                                    List<String> sauces = new ArrayList<>();
                                    aiSandwich = new Sandwich(s.size, s.bread, meats, cheeses, toppings, sauces, s.toasted);
                                }

                                System.out.println("Customize AI sandwich? (yes/no):");
                                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                                    Sandwich modified = buildSandwich(scanner);
                                    aiSandwich.setMeats(modified.getMeats());
                                    aiSandwich.setCheeses(modified.getCheeses());
                                    aiSandwich.setRegularToppings(modified.getRegularToppings());
                                    aiSandwich.setSauces(modified.getSauces());
                                }

                                System.out.println("Would you like to save this as a Signature Sandwich? (yes/no):");
                                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                                    System.out.println("Enter a name for this Signature Sandwich:");
                                    String name = scanner.nextLine().trim();

                                    CustomSignatureSandwich customSig = new CustomSignatureSandwich(
                                            name,
                                            aiSandwich.getSize(),
                                            aiSandwich.getBread(),
                                            aiSandwich.getMeats(),
                                            aiSandwich.getCheeses(),
                                            aiSandwich.getRegularToppings(),
                                            aiSandwich.getSauces(),
                                            aiSandwich.toasted
                                    );

                                    MenuService.addCustomSignatureSandwich(customSig);
                                    System.out.println("✅ \"" + name + "\" saved as a Signature Sandwich!");
                                }

                                order.addItem(aiSandwich);
                            }
                        }

                        if (aiOrder.getDrinks() != null) {
                            for (AIOrder.DrinkRequest d : aiOrder.getDrinks()) {
                                Drink drink = new Drink(d.size, d.flavor);
                                order.addItem(drink);
                            }
                        }

                        if (aiOrder.getChips() != null) {
                            for (AIOrder.ChipRequest c : aiOrder.getChips()) {
                                Chips chips = new Chips(c.type);
                                order.addItem(chips);
                            }
                        }

                        System.out.println("AI items successfully added to your order!");

                    } catch (Exception e) {
                        System.out.println("Failed to process AI order: " + e.getMessage());
                    }
                    break;
                case "6":
                    System.out.println("Choose a Signature Sandwich:");
                    System.out.println("1) BLT");
                    System.out.println("2) Philly Cheese Steak");
                    MenuService.printSavedCustomSignatures();
                    String sigChoice = scanner.nextLine();
                    SignatureSandwich sigSandwich = null;
                    switch (sigChoice) {
                        case "1" -> sigSandwich = new BLT();
                        case "2" -> sigSandwich = new PhillyCheeseSteak();
                        default -> {
                            try {
                                int customIndex = Integer.parseInt(sigChoice);
                                if (customIndex >= 3 && customIndex < 3 + MenuService.getCustomSignatureCount()) {
                                    sigSandwich = MenuService.getCustomSignatureSandwich(customIndex);
                                } else {
                                    System.out.println("Invalid custom signature choice.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input.");
                            }
                        }
                    }
                    if (sigSandwich != null) {
                        System.out.println("Do you want to customize it? (yes/no):");
                        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                            Sandwich modified = buildSandwich(scanner);
                            sigSandwich.setMeats(modified.getMeats());
                            sigSandwich.setCheeses(modified.getCheeses());
                            sigSandwich.setRegularToppings(modified.getRegularToppings());
                            sigSandwich.setSauces(modified.getSauces());
                        }
                        order.addItem(sigSandwich);
                    }
                    break;
                case "0":
                    System.out.println("Order canceled.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Sandwich buildSandwich(Scanner scanner) {
        MenuService.printSandwichSizes();
        String size = scanner.nextLine().replace("\"", "").trim();

        MenuService.printBreadOptions();
        String bread = scanner.nextLine().trim();

        MenuService.printMeats();
        System.out.println("Enter meats (comma-separated):");
        String[] meats = scanner.nextLine().split(",");

        MenuService.printCheeses();
        System.out.println("Enter cheeses (comma-separated):");
        String[] cheeses = scanner.nextLine().split(",");

        MenuService.printToppings();
        System.out.println("Enter regular toppings (comma-separated):");
        String[] toppings = scanner.nextLine().split(",");

        MenuService.printSauces();
        System.out.println("Enter sauces (comma-separated):");
        String[] sauces = scanner.nextLine().split(",");

        System.out.println("Toasted? (yes/no):");
        boolean toasted = scanner.nextLine().trim().equalsIgnoreCase("yes");

        List<String> meatList = new ArrayList<>();
        for (String m : meats) meatList.add(m.trim());
        List<String> cheeseList = new ArrayList<>();
        for (String c : cheeses) cheeseList.add(c.trim());
        List<String> toppingList = new ArrayList<>();
        for (String t : toppings) toppingList.add(t.trim());
        List<String> sauceList = new ArrayList<>();
        for (String s : sauces) sauceList.add(s.trim());

        return new Sandwich(size, bread, meatList, cheeseList, toppingList, sauceList, toasted);
    }
}
