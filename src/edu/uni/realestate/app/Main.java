package edu.uni.realestate.app;
import edu.uni.realestate.model.Property;
import edu.uni.realestate.service.Agent;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);   // one Scanner for the whole program
        Agent agent = new Agent();




        System.out.println("Real Estate Agent :)");
        System.out.println("1) Add property   2) List available   3) List all   4) Mark sold/rented   0) Exit");
        boolean running = true;
        while (running) {//entering the loop
            System.out.print("Choice: ");
            String choice = in.nextLine().trim();//read as text, so letters can't crash the menu

            switch (choice) {
                case "1":
                    addProperty(agent, in);
                    break;
                case "2":
                    agent.displayProperties(false); // available only
                    break;
                case "3":
                    agent.displayProperties(true);    // all properties
                    break;
                case "4":
                    markClosed(agent, in);
                    break;
                case "0":
                    System.out.println("Goodbye.");
                    running = false;
                    break;
                default:
                    System.out.println("Please choose 1, 2, 3, 4 or 0.");
            }

            if (running) {
                System.out.println();   // blank line between actions
            }
        }

        in.close();
    }
    // Option 1: the Agent asks all the questionsa and Main only handles a failure
    private static void addProperty(Agent agent, Scanner in) {
        try {
            agent.addProperty(in);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add property: " + e.getMessage());
        }
    }

    // Option 4: read an id, then let the Agent close the deal
    private static void markClosed(Agent agent, Scanner in) {
        System.out.print("Property id: ");
        int id;
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a whole number.");
            return;
        }

        // look up the property only to print its label (RENTED or SOLD)
        Property property = null;
        for (Property p : agent.getProperties()) {
            if (p.getId() == id) {
                property = p;
            }
        }

        if (property == null) {
            System.out.println("No property with id " + id + ".");
        } else if (agent.closeDeal(id)) {
            System.out.println("Property #" + id + " marked as " + property.getClosedLabel() + ".");
        } else {
            System.out.println("Property #" + id + " is not available.");
        }
    }
}