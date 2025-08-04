import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * PROGRAM DETAILS: Goal of the following code is to prompt a customer for input Print a menu and
 * prompt the customer for which menu options they would like to order Total of their order will be
 * added List of drivers information wil be read out of a file A driver will be selected based if
 * the user & driver location matches The customers invoice will be printed to a invoice text file
 */

public class QuickFoods {

  Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    // DriverManager class - extract drivers && store and findDriver
    DriverManager driverManager = new DriverManager();
    String filePath = "drivers.txt";
    // Load driver data from file
    driverManager.loadDriversFromFile(filePath);

    // Create Restaurant manager class(moved outside so not overwritten)
    RestaurantManager manager = new RestaurantManager();

    // While: Takes owner/customer back to main menu after completion
    while (true) {
      // Print MAIN-MENU
      Scanner scanner = new Scanner(System.in);
      int menuSelection = printMainMenu(scanner);

      switch (menuSelection) {
        // RESTAURANT OWNER
        case 1: {
          // Use the existing method in RestaurantManager to collect restaurant info & register
          manager.collectRestaurantInfo();
          // After registration, you can retrieve and print all restaurants if needed
          manager.printAllRestaurants();
          break;
        }
        // CUSTOMER
        case 2: {
          System.out.println("**** User login portal ****");

          // Create a Customer object
          Customer customer = new Customer();
          // Collect customer details
          customer.collectInput(scanner);

          // Prints customer details using the toString() method
          System.out.println("Customer info:\n" + customer);

          // Display available restaurants
          manager.restaurantList();

          // Select restaurant
          System.out.println("Please Select a restaurant:");
          String selectedRestaurant = scanner.nextLine();

          // Display the selected restaurant details
          manager.displayRestaurant(selectedRestaurant);



          // Get location of the selected restaurant
          String restaurantLocation = manager.getRestaurantLocation(selectedRestaurant);
          // find driver best for job
          String deliveryDriver = driverManager.findDriver(customer, restaurantLocation);

          // Get the menu of the selected restaurant
          Restaurant chosenRestaurant = manager.getRestaurantMap().get(selectedRestaurant);
          if (chosenRestaurant == null) {
            System.out.println("Selected restaurant not found.");
            break;
          }
          HashMap<String, Double> menu = chosenRestaurant.getMenu();

          // Create an Order object
          Order order = new Order();

          // Start the ordering process using the selected restaurants menu
          order.takeOrder(menu);

          // Print the order summary
          order.printOrder();
          // instructions(!!!!!!!!!! ADD TO RECEIPT)
          customer.collectSpecialInstructions(scanner);
          // Print the detailed receipt
          String receipt = order.getReceipt(chosenRestaurant.getMenu(), deliveryDriver, customer,
              chosenRestaurant);

          System.out.println(receipt);

          try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoice.txt"))) {
            writer.write(receipt);
            System.out.println("Receipt saved to invoice.txt successfully.");
          } catch (IOException e) {
            System.out.println("Error writing to file!");
          }
          // instructions

          break;
        }
        // EXIT PROGRAM
        case 3: {
          System.out.println("Exiting program...");
          System.exit(0);// exit
          break;
        }
        default: {
          System.out.println("Please select a valid option");
          break;
        }
      }
    }
  }

  // METHOD : Write invoice to file
  public static void writeInvoiceToFile(String invoiceText) {
    try {
      Formatter formatter = new Formatter("invoice.txt");
      // %s format specifier -> inserts a string into the output
      formatter.format("%s", invoiceText); // Write the invoice text into the file
      formatter.close();
    } catch (Exception e) {
      System.out.println("Error writing to file: ");
    }
  }

  // print Main menu
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
