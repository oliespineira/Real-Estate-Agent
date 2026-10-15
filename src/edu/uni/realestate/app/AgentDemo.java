package edu.uni.realestate.app;

import edu.uni.realestate.model.ApartmentPurchase;
import edu.uni.realestate.model.ApartmentRental;
import edu.uni.realestate.model.HousePurchase;
import edu.uni.realestate.model.HouseRental;
import edu.uni.realestate.model.Property;
import edu.uni.realestate.service.Agent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Keyboard-free test of the model and the Agent. Builds one object of each
 * concrete class from a Map and demonstrates that (1) invalid values are rejected,
 * (2) closing a property twice is rejected, and (3) closed properties disappear
 * from the available list.
 */
public class AgentDemo {

    public static void main(String[] args) {
        Agent agent = new Agent();

        // ----- valid data: one map per concrete class -----
        Map<String, String> houseRental = data(
                "sqft", "1400", "beds", "3", "baths", "2",
                "stories", "2", "garage", "attached", "fenced", "yes",
                "rent", "1850", "furnished", "no", "utilities", "about 120 EUR/month");

        Map<String, String> housePurchase = data(
                "sqft", "2100", "beds", "4", "baths", "3",
                "stories", "2", "garage", "detached", "fenced", "no",
                "price", "450000", "taxes", "3200");

        Map<String, String> apartmentRental = data(
                "sqft", "650", "beds", "0", "baths", "1",
                "balcony", "yes", "laundry", "coin",
                "rent", "950", "furnished", "yes", "utilities", "included");

        Map<String, String> apartmentPurchase = data(
                "sqft", "900", "beds", "2", "baths", "1",
                "balcony", "no", "laundry", "ensuite",
                "price", "280000", "taxes", "0");

        section("1. Creating one property of each concrete class");
        agent.addProperty(new HouseRental(houseRental));
        agent.addProperty(new HousePurchase(housePurchase));
        agent.addProperty(new ApartmentRental(apartmentRental));
        agent.addProperty(new ApartmentPurchase(apartmentPurchase));
        System.out.println("Properties added: " + agent.getProperties().size()
                + ", available: " + agent.countAvailable());
        System.out.println();
        agent.displayProperties(true);

        section("2. Invalid values are rejected");
        tryCreate("Square feet = 0",         HouseRental::new,       with(houseRental, "sqft", "0"));
        tryCreate("Bedrooms = -1",           HousePurchase::new,     with(housePurchase, "beds", "-1"));
        tryCreate("Bathrooms = 0",           ApartmentRental::new,   with(apartmentRental, "baths", "0"));
        tryCreate("Stories = 0",             HouseRental::new,       with(houseRental, "stories", "0"));
        tryCreate("Garage = \"carport\"",    HousePurchase::new,     with(housePurchase, "garage", "carport"));
        tryCreate("Laundry = \"shared\"",    ApartmentPurchase::new, with(apartmentPurchase, "laundry", "shared"));
        tryCreate("Monthly rent = 0",        ApartmentRental::new,   with(apartmentRental, "rent", "0"));
        tryCreate("Utilities = blank",       HouseRental::new,       with(houseRental, "utilities", "   "));
        tryCreate("Price = -5000",           HousePurchase::new,     with(housePurchase, "price", "-5000"));
        tryCreate("Annual taxes = -1",       ApartmentPurchase::new, with(apartmentPurchase, "taxes", "-1"));
        System.out.println("Properties in the agent's list: " + agent.getProperties().size()
                + " (no rejected property was added)");

        section("3. Closing a property twice is rejected");
        System.out.println("closeDeal(1), first time:  " + agent.closeDeal(1));
        System.out.println("closeDeal(1), second time: " + agent.closeDeal(1));
        System.out.println("closeDeal(99), unknown id: " + agent.closeDeal(99));
        try {
            agent.getProperties().get(0).close();   // calling close() directly on a closed property
            System.out.println("ERROR: close() accepted an already closed property");
        } catch (IllegalStateException e) {
            System.out.println("Direct close() again: rejected -> " + e.getMessage());
        }
        System.out.println("closeDeal(4): " + agent.closeDeal(4));

        section("4. Closed properties disappear from the available list");
        System.out.println("Available: " + agent.countAvailable() + " of " + agent.getProperties().size());
        System.out.println();
        System.out.println("-- Available properties --");
        agent.displayProperties(false);
        System.out.println("-- All properties --");
        agent.displayProperties(true);

        section("5. The agent's list cannot be modified from outside");
        try {
            agent.getProperties().clear();
            System.out.println("ERROR: the list was modified");
        } catch (UnsupportedOperationException e) {
            System.out.println("getProperties().clear(): rejected (unmodifiable list)");
        }
    }

    // ----- helpers -----

    /** Builds a map from alternating key/value arguments. */
    private static Map<String, String> data(String... keyValues) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }
        return map;
    }

    /** Returns a copy of base with one key replaced, leaving base unchanged. */
    private static Map<String, String> with(Map<String, String> base, String key, String value) {
        Map<String, String> copy = new HashMap<>(base);
        copy.put(key, value);
        return copy;
    }

    /** Tries to build a property and reports whether the constructor rejected it. */
    private static void tryCreate(String label, Function<Map<String, String>, Property> constructor,
                                  Map<String, String> data) {
        try {
            constructor.apply(data);
            System.out.println(label + ": ERROR, accepted");
        } catch (IllegalArgumentException e) {
            System.out.println(label + ": rejected -> " + e.getMessage());
        }
    }

    private static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
