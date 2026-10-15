package edu.uni.realestate.model;

import java.util.Map;
import java.util.Scanner;

public class HouseRental extends House {
    private final RentalTerms terms;

    public HouseRental(Map<String, String> data) {
        super(data);// House validates its keys, then Property validates its keys
        terms = new RentalTerms(data);// composition: RentalTerms validates rent, furnished, utilities
    }

    public RentalTerms getTerms() { return terms; }

    @Override
    public String getClosedLabel() { return "RENTED"; }

    @Override
    public void display() {
        super.display();// parent's lines first (Property, then House)
        terms.display();// then the rental terms
    }

    /* Takes the Scanner and returns a map of everything the user typed
       for a house for rent. It never creates the object itself. */
    public static Map<String, String> promptInit(Scanner in) {
        Map<String, String> data = House.promptInit(in);// calls the parent: House asks its questions, after Property's
        data.putAll(RentalTerms.promptInit(in)); // add rent, furnished, utilities
        return data;
    }
}