package edu.uni.realestate.service;

import edu.uni.realestate.model.ApartmentPurchase;
import edu.uni.realestate.model.ApartmentRental;
import edu.uni.realestate.model.HousePurchase;
import edu.uni.realestate.model.HouseRental;
import edu.uni.realestate.model.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Agent {
    /*These are lookup tbales so instead of storing data like numbers and text they store methods*/
    // registry (bonus): one entry per concrete property type
    // Key = "<property type> <deal type>". Adding a new type only needs one new
    // entry in each map; the logic of addProperty(Scanner) never changes.
    private static final Map<String, Function<Scanner, Map<String, String>>> PROMPTS = Map.of(
            "house rental",HouseRental::promptInit,
            "house purchase",HousePurchase::promptInit,
            "apartment rental",ApartmentRental::promptInit,
            "apartment purchase",ApartmentPurchase::promptInit
    );

    private static final Map<String, Function<Map<String, String>, Property>> FACTORIES = Map.of(
            "house rental",HouseRental::new,
            "house purchase",HousePurchase::new,
            "apartment rental",   ApartmentRental::new,
            "apartment purchase", ApartmentPurchase::new
    );

    private final List<Property> properties;

    public Agent() {
        properties = new ArrayList<>();
    }//creating an empty list.

    public void addProperty(Property p) {
        if (p == null) throw new IllegalArgumentException("Property cannot be null");
        properties.add(p);
    }

    public void addProperty(Scanner in) {
        /*used by menu option 1. method overloading*/
        String type = ask(in, "Property type (house, apartment): ", "house", "apartment");
        String deal = ask(in, "Deal type (rental, purchase): ", "rental", "purchase");
        String key = type + " " + deal;

        Map<String, String> data = PROMPTS.get(key).apply(in);  // e.g. HouseRental.promptInit(in)
        Property p = FACTORIES.get(key).apply(data);            // e.g. new HouseRental(data)
        addProperty(p);
        System.out.println("Added property #" + p.getId() + ".");
    }

    public void displayProperties(boolean showAll) {// loops throguh the list and shows every property, only the ones where isAvailable is true
        int shown = 0;
        for (Property p : properties) {
            if (showAll || p.isAvailable()) {
                p.display();
                System.out.println();
                shown++;
            }
        }
        if (shown == 0) {
            System.out.println("No properties to show.");
        }
    }

    public boolean closeDeal(int id) {
        Property p = findById(id);
        if (p == null || !p.isAvailable()) {// if it doesnt exist or it is not available
            return false;
        }
        p.close();
        return true;
    }

    public int countAvailable() {// counts the properties that are still available
        int count = 0;
        for (Property p : properties) {
            if (p.isAvailable()) count++;
        }
        return count;
    }

    public List<Property> getProperties() {// this returns the list wrapped in collections.unmodifiable list, other classes can read it but cannot add or remove anything
        return Collections.unmodifiableList(properties);
    }

    private Property findById(int id) {//searching by id
        for (Property p : properties) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // Asks until the answer is one of the options. Agent cannot use InputHelper
    // because that class is package-private in model.
    private static String ask(Scanner in, String prompt, String... options) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim().toLowerCase();
            for (String option : options) {
                if (option.equals(line)) return option;
            }
            System.out.println("Please choose one of: " + String.join(", ", options));
        }
    }
}