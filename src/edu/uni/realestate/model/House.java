package edu.uni.realestate.model;

import java.util.Map;
import java.util.Scanner;

public abstract class House extends Property {
    private int stories;
    private GarageType garage;
    private boolean fencedYard;

    public House(Map<String, String> data) {
        super(data);                                   // Property validates sqft, beds, baths
        setStories(Integer.parseInt(data.get("stories")));
        setGarage(GarageType.valueOf(data.get("garage").toUpperCase()));
        setFencedYard("yes".equalsIgnoreCase(data.get("fenced")));
    }

    // getters accessors
    public int getStories()         { return stories; }
    public GarageType getGarage()   { return garage; }
    public boolean hasFencedYard()  { return fencedYard; }

    // setters mutators
    private void setStories(int value) {
        if (value < 1) throw new IllegalArgumentException("Stories must be at least 1");
        stories = value;
    }

    private void setGarage(GarageType value) {
        if (value == null) throw new IllegalArgumentException("Garage type is required");
        garage = value;
    }

    private void setFencedYard(boolean value) {
        fencedYard = value;
    }

    // behaviour
    @Override
    public void display() {
        super.display();
        System.out.println("  Stories:     " + stories);
        System.out.println("  Garage:      " + garage);
        System.out.println("  Fenced yard: " + (fencedYard ? "yes" : "no"));
    }

    public static Map<String, String> promptInit(Scanner in) {
        Map<String, String> data = Property.promptInit(in);
        data.put("stories", InputHelper.readInt(in, "Number of stories: ", 1));
        data.put("garage",  InputHelper.readChoice(in, "Garage (attached, detached, none): ",
                "attached", "detached", "none"));
        data.put("fenced",  InputHelper.readYesNo(in, "Fenced yard? (yes/no): "));
        return data;
    }
}