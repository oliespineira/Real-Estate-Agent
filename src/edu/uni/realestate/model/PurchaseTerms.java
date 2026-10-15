package edu.uni.realestate.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class PurchaseTerms {
    private double price;
    private double annualTaxes;

    public PurchaseTerms(Map<String, String> data) {
        setPrice(Double.parseDouble(data.get("price")));
        setAnnualTaxes(Double.parseDouble(data.get("taxes")));
    }

    // ----- getters -----
    public double getPrice()       { return price; }
    public double getAnnualTaxes() { return annualTaxes; }

    // ----- private validating setters -----
    private void setPrice(double value) {
        if (value <= 0) throw new IllegalArgumentException("Price must be greater than 0");
        price = value;
    }

    private void setAnnualTaxes(double value) {
        if (value < 0) throw new IllegalArgumentException("Annual taxes cannot be negative");
        annualTaxes = value;
    }

    // ----- behavior -----
    public void display() {
        System.out.println(String.format(Locale.US, "  Price:        %.2f EUR", price));
        System.out.println(String.format(Locale.US, "  Annual taxes: %.2f EUR", annualTaxes));
    }

    public static Map<String, String> promptInit(Scanner in) {
        Map<String, String> data = new HashMap<>();
        data.put("price", InputHelper.readDouble(in, "Price: ", 0.01));
        data.put("taxes", InputHelper.readDouble(in, "Estimated annual property taxes: ", 0));
        return data;
    }
}