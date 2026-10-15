package edu.uni.realestate.model;

import java.util.Map;      // Map type for the answers (key -> value pairs)
import java.util.Scanner;  // reads user input from the terminal

public class HousePurchase extends House {
    private final PurchaseTerms terms;

    public HousePurchase(Map<String, String> data) {
        super(data);                       // House validates its keys, then Property validates its keys
        terms = new PurchaseTerms(data);   // composition: PurchaseTerms validates price and taxes
    }

    public PurchaseTerms getTerms() { return terms; }   // safe: PurchaseTerms has no public setters

    @Override
    public String getClosedLabel() { return "SOLD"; }

    @Override
    public void display() {
        super.display();     // parent's lines first (Property, then House)
        terms.display();     // then the purchase terms
    }

    public static Map<String, String> promptInit(Scanner in) {
        Map<String, String> data = House.promptInit(in);   // Property + House questions
        data.putAll(PurchaseTerms.promptInit(in));         // add price and taxes
        return data;
    }
}