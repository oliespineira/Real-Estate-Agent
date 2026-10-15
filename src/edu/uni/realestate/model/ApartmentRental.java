package edu.uni.realestate.model;

import java.util.Map;
import java.util.Scanner;

public class ApartmentRental extends Apartment{
    private final RentalTerms terms;

    public ApartmentRental(Map<String, String> data) {
        super(data);
        terms = new RentalTerms(data);
    }

    public RentalTerms getTerms() {
        return terms;
    }
    @Override
    public void display() {//using methods from superclass
        super.display();
        terms.display();
    }

    @Override
    public String getClosedLabel() { return "RENTED"; }


    public static Map<String, String> promptInit(Scanner in){
        /*Here the apartment takes a scanner and returns a map of the input the user puts*/
        Map<String, String> data= Apartment.promptInit(in);//calling parent. apartmentrental asks apartment which asks property (added)
        data.putAll(RentalTerms.promptInit(in));
        return data;
    }






}