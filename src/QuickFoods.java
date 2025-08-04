import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * PROGRAM DETAILS: Goal of the following code is to prompt a customer for input, print a menu and
 * prompt the customer for which menu options they would like to order. Total of their order will be
 * added. List of drivers information will be read out of a file. A driver will be selected based if
 * the user & driver location matches. The customer's invoice will be printed to an invoice text
 * file.
 */

public class QuickFoods {

  // Constants for file names used in the program
  private static final String DRIVERS_FILE = "drivers.txt";
  private static final String INVOICE_FILE = "invoice.txt";

  Scanner scanner = new Scanner(System.in);

  /**
   * The main method runs the QuickFoods ordering system. It allows restaurant owners to register
   * their restaurant and customers to place orders. The program loops until the user chooses to
   * exit.
   */
  public static void main(String[] args) {
    // DriverManager class - extract drivers, store and findDriver
    DriverManager driverManager = new DriverManager();

    // Load driver data from file using constant
    driverManager.loadDriversFromFile(DRIVERS_FILE);

    // Create Restaurant manager instance
    RestaurantManager manager = new RestaurantManager();

    // Main menu loop - takes owner/customer back to main menu after completion
    while (true) {
      Scanner scanner = new Scanner(System.in);
      int menuSelection = printMainMenu(scanner);

      switch (menuSelection) {
        case 1: { // RESTAURANT OWNER
          // Collect restaurant info and register
          manager.collectRestaurantInfo();
          // Print all registered restaurants if needed
          manager.printAllRestaurants();
          break;
        }
        case 2: { // CUSTOMER
          System.out.println("**** User login portal ****");

          Customer customer = new Customer();
          customer.collectInput(scanner);

          System.out.println("Customer info:\n" + customer);

          manager.restaurantList();

          System.out.println("Please Select a restaurant:");
          String selectedRestaurant = scanner.nextLine();

          manager.displayRestaurant(selectedRestaurant);

          String restaurantLocation = manager.getRestaurantLocation(selectedRestaurant);
          String deliveryDriver = driverManager.findDriver(customer, restaurantLocation);

          Restaurant chosenRestaurant = manager.getRestaurantMap().get(selectedRestaurant);
          if (chosenRestaurant == null) {
            System.out.println("Selected restaurant not found.");
            break;
          }
          HashMap<String, Double> menu = chosenRestaurant.getMenu();

          Order order = new Order();

          order.takeOrder(menu);

          order.printOrder();

          customer.collectSpecialInstructions(scanner);

          String receipt = order.getReceipt(chosenRestaurant.getMenu(), deliveryDriver, customer,
              chosenRestaurant);

          System.out.println(receipt);

          // Save receipt using file name constant
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVOICE_FILE))) {
            writer.write(receipt);
            System.out.println("Receipt saved to " + INVOICE_FILE + " successfully.");
          } catch (IOException e) {
            System.out.println("Error writing to file!");
          }

          break;
        }
        case 3: { // EXIT PROGRAM
          System.out.println("Exiting program...");
          System.exit(0);
          break;
        }
        default: {
          System.out.println("Please select a valid option");
          break;
        }
      }
    }
  }

  /**
   * Writes the invoice text to a file.
   * 
   * @param invoiceText The text of the invoice to be saved.
   */
  public static void writeInvoiceToFile(String invoiceText) {
    try {
      Formatter formatter = new Formatter(INVOICE_FILE);
      formatter.format("%s", invoiceText);
      formatter.close();
    } catch (Exception e) {
      System.out.println("Error writing to file: ");
    }
  }

  /**
   * Prints the main menu options and reads the user selection.
   * 
   * @param scanner Scanner object for reading input
   * @return the selected menu option
   */
  public static int printMainMenu(Scanner scanner) {
    System.out.println("*********************************");
    System.out.println("** QuickFoods Ordering system: **");
    System.out.println("*********************************");
    System.out.println("1) Restaurant owner");
    System.out.println("2) Customer");
    System.out.println("3) Exit Program");
    int selectedOption = scanner.nextInt();
    scanner.nextLine(); // clear newline
    return selectedOption;
  }

}


/**
 * REFERENCES:
 * 
 * 1) Stack Overflow (2010): How to determine whether a String contains an integer
 * https://stackoverflow.com/questions/4388546/how-to-determine-whether-a-string-contains-an-integer
 * 2) Java ArrayLists: 2.1) Coding with John - Java ArrayLists tutorial (YouTube) 3)
 * https://www.geeksforgeeks.org/understanding-java-object-references/ 4)
 * https://docs.oracle.com/javase/jndi/tutorial/objects/storing/reference.html
 */
//
