import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Order class handles the customer's order process by allowing them to select items from a given
 * menu, track quantities, calculate the total price, and generate a detailed receipt. It maintains
 * a map of ordered items and supports generating unique order numbers
 */
public class Order {
  // Stores ordered items and their quantities
  private HashMap<String, Integer> orderedItems = new HashMap<>();
  String addMore;

  // Used to generate unique order numbers (you can improve this further if needed)
  private static int orderCounter = 1000;

  // Method to start placing an order
  public void takeOrder(HashMap<String, Double> menu) {
    Scanner scanner = new Scanner(System.in);

    do {
      System.out.println("Please select an item from the menu:");
      for (Map.Entry<String, Double> entry : menu.entrySet()) {
        System.out.println("  " + entry.getKey() + " : R" + entry.getValue());
      }

      System.out.print("Enter item name: ");
      String item = scanner.nextLine().toLowerCase();

      if (!menu.containsKey(item)) { // Check if the selected item exists in the menu
        System.out.println("Item not found in the menu. Please try again.");
        continue;
      }

      System.out.print("Enter quantity: ");
      int quantity;

      try {
        quantity = Integer.parseInt(scanner.nextLine()); // Parse quantity input
      } catch (NumberFormatException e) {
        System.out.println("Invalid quantity. Please enter a number.");
        continue;
      }

      // Update order (add or increase quantity)
      orderedItems.put(item, orderedItems.getOrDefault(item, 0) + quantity);

      System.out.print("Would you like to add another item? (y/n): ");
      addMore = scanner.nextLine().trim().toLowerCase();

    } while (!addMore.equalsIgnoreCase("n")); // Continue ordering until user says 'n'
  }

  // Generates a unique order number by incrementing a static counter
  public int generateOrderNum() {
    return ++orderCounter;
  }

  // Calculates the total cost of the order based on the menu prices
  public double getTotalAmount(HashMap<String, Double> menu) {
    double total = 0.0;
    for (String item : orderedItems.keySet()) {
      int quantity = orderedItems.get(item);
      double price = menu.get(item);
      total += quantity * price;
    }
    return total;
  }

  public String getReceipt(HashMap<String, Double> menu, String deliveryDriver, Customer customer,
      Restaurant restaurant) {
    StringBuilder receipt = new StringBuilder();

    receipt.append("==================\n");
    receipt.append("RECEIPT\n");
    receipt.append("======================\n");

    // Generate order number
    int orderNum = generateOrderNum();
    receipt.append("Order number ").append(orderNum).append("\n");

    // Customer details
    receipt.append("Customer: ").append(customer.getName()).append("\n");
    receipt.append("Email: ").append(customer.getEmail()).append("\n");
    receipt.append("Phone number: ").append(customer.getPhoneNumber()).append("\n");
    receipt.append("Location: ").append(customer.getCity()).append("\n");

    // Intro sentence
    receipt.append("You have ordered the following from ").append(restaurant.getName())
        .append(" in ").append(restaurant.getLocation()).append(":\n");

    // List ordered items
    for (String item : orderedItems.keySet()) {
      int quantity = orderedItems.get(item);
      double price = menu.get(item);

      receipt.append(quantity).append(" x ").append(item).append(" (R")
          .append(String.format("%.2f", price)).append(")\n");
    }

    // Special instructions
    String instructions = customer.getSpecialInstructions();
    if (instructions != null && !instructions.trim().isEmpty()) {
      receipt.append("Special instructions: ").append(instructions).append("\n");
    }

    // Total price
    double total = getTotalAmount(menu);
    receipt.append("Total: R").append(String.format("%.2f", total)).append("\n");

    // Delivery driver info + address
    receipt.append(deliveryDriver)
        .append(" is nearest to the restaurant and so he will be delivering your\n")
        .append("order to you at:\n");
    receipt.append(customer.getAddress()).append("\n");
    receipt.append(customer.getCity()).append("\n");

    // Restaurant contact number
    receipt.append("If you need to contact the restaurant, their number is ")
        .append(restaurant.getPhone()).append(".\n");

    return receipt.toString();
  }

  // returns full map of order and quantities
  public HashMap<String, Integer> getOrderedItems() {
    return orderedItems;
  }

  // Prints the current order items with their quantities
  public void printOrder() {
    if (orderedItems.isEmpty()) {
      System.out.println("No items ordered yet.");
      return;
    }
    System.out.println("Current Order:");
    for (Map.Entry<String, Integer> entry : orderedItems.entrySet()) {
      System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
    }
  }

}


// Method calculates the total cost of all items in the order by multiplying each item's price by
// its quantity,
// then builds and returns a detailed string showing each item's cost and the overall total
/*
 * private String calculateTotal() { double total = 0.0;// Variable to hold the final total cost
 * String totalDetails = ""; // String to build the detailed bill text
 * 
 * // Loop through all items in the final order for (String item : finalAddedItems.keySet()) { //
 * int quantity = finalAddedItems.get(item); // finalAddedItems(key) -> quantity of each item int
 * price = addedItems.get(item); // addedItems(key) -> price of each item double lineTotal =
 * quantity * price; // // Calculate total for item (price * quantity)
 * 
 * totalDetails += quantity + " x " + item + " (R" + lineTotal + ")" + "\n";// Add item line to //
 * invoice total += lineTotal; // Add to running total }
 * 
 * totalDetails += "Total: R" + total; // Add the final total to the bil return totalDetails; //
 * Return complete receipt as a string }
 * 
 * // Method to get totall amount private double getTotalAmount() { double total = 0.0; for (String
 * item : finalAddedItems.keySet()) { int quantity = finalAddedItems.get(item); int price =
 * addedItems.get(item); total += price * quantity; } return total; }
 * 
 * // String item = entry.getKey(); --- get key // Method to print all ordered items and their
 * prices // Method to print all ordered items and their prices
 * 
 * 
 * /* METHOD : PRINT CUSTOMER INVOICE public String printInvoice(Order order1, Customer customer1,
 * String instructions, String[] assignedDriver, Restaurant restaurant) { String output =
 * "=============Customer Invoice=============\n"; output += "Order number: " +
 * order1.generateOrderNum() + "\n"; output += customer1.printCustomerInfo(); output +=
 * "You have ordered the following from " + restaurant.getRestaurantName() + " " +
 * restaurant.getRestaurantLocation() + ":\n"; output += order1.calculateTotal() + "\n"; output +=
 * "Special instruction: " + instructions + "\n"; output += "\n"; output += "Total:\n" + "R" +
 * order1.getTotalAmount() + "\n"; output += "\n"; output += assignedDriver[0] +
 * " is nearest to the restaurant and so he will be delivering your\n order to you at: \n" +
 * customer1.customerLocation + "\n"; output += "\n"; output +=
 * "If you need to contact the restaurant, their number is " + restaurant.getRestaurantNumber() +
 * "\n";
 * 
 * return output; }
 */

