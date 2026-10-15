package edu.uni.realestate.model;


import java.util.Map;
import java.util.Scanner;

public class ApartmentPurchase extends Apartment{
    private final PurchaseTerms terms;

    public ApartmentPurchase(Map<String, String> data) {
        super(data);                       // House validates its keys, then Property validates its keys
        terms = new PurchaseTerms(data);   // composition: PurchaseTerms validates price and taxes
    }

    public PurchaseTerms getTerms() { return terms; }

    @Override
    public String getClosedLabel() { return "SOLD"; }

    @Override
    public void display() {
        super.display();     //lit same as house purchase but for apartment
        terms.display();
    }

    public static Map<String, String> promptInit(Scanner in) {
        Map<String, String> data = Apartment.promptInit(in); // Propert + apartment questions
        data.putAll(PurchaseTerms.promptInit(in));
        return data;
    }
}