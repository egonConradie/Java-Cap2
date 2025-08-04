import java.util.HashMap;
import java.util.Scanner;

/**
 * Manages multiple restaurants by collecting their details and storing them in a map. Handles user
 * input to register restaurants and maintain a collection of Restaurant objects. Difference: This
 * class manages a collection of restaurants and user interaction, not individual restaurant data.
 */


public class RestaurantManager {
  private Scanner scanner = new Scanner(System.in);


  // String key-identifier ,Restaurant class reference
  private HashMap<String, Restaurant> restaurantMap = new HashMap<>();

  // Method to prompt owner for restaurant information
  public void collectRestaurantInfo() {
    // *** RESTAURANT INFORMATION ***
    // Restaurant key-identifier
    System.out.println("***Owner portal:***");
    // Name - key to access restaurant info
    System.out.println("Enter your restaurant's Name value:");
    String restaurantName = scanner.nextLine();

    System.out.println(
        "Enter your restaurant's location (Stellenbosch, Pretoria, Cape Town, Bloemfontein):");
    String restaurantLocation = scanner.nextLine();

    String restaurantNumber;
    // Restaurant Number && validation method call
    while (true) {
      System.out.println("Enter your 10-digit number:");
      restaurantNumber = scanner.nextLine();
      // true exit loop
      if (checkNumberLength(restaurantNumber)) {
        break;
      } else {
        System.out.println("Invalid number, try again.");
      }
    }
    // HASHMAP TO STORE MENU -> key(foodItem) & value(price))
    HashMap<String, Double> menu = new HashMap<>();


    System.out.println("Enter your menu items:");

    while (true) {
      // foodItem
      System.out.println("Enter food item:");
      String item = scanner.nextLine().toLowerCase();
      // Price
      System.out.println("Enter price:");
      double price = scanner.nextDouble();
      scanner.nextLine(); // Consume newline

      menu.put(item, price);
      // prompt for another item input
      System.out.println("Add another item? (y/n):");
      String answer = scanner.nextLine();
      if (answer.equalsIgnoreCase("n"))
        break;
    }
    // instantiate restaurants inforamtion
    Restaurant restaurant =
        new Restaurant(restaurantName, restaurantNumber, restaurantLocation, menu);

    // NOTE!: "restaurant" holds a reference to a Restaurant object with all details(not the
    // actual object itself)
    // "restaurant" is a temporary reference to a Restaurant object holding all details,
    // stored in 'restaurantMap' for later access by key, not via this variable directly.
    restaurantMap.put(restaurantName, restaurant);
    System.out.println("Restaurant successfully registered.\n");
  }

  public HashMap<String, Restaurant> getRestaurantMap() {
    return restaurantMap;
  }

  private boolean checkNumberLength(String number) {
    return number.length() == 10 && number.matches("\\d+");
  }

  // Print Restaurants information
  public void printAllRestaurants() {
    if (restaurantMap.isEmpty()) {
      System.out.println("No restaurants registered yet.");
      return;
    }
    System.out.println("All Registered Restaurants:");

    for (String key : restaurantMap.keySet()) { // Iterate over each restaurant's unique key in the
                                                // map
      Restaurant tempResObj = restaurantMap.get(key); // Retrieve the Restaurant object associated
                                                      // with the current key
      System.out.println("Restaurant name: " + key); // Print the unique key(NAME) for this
                                                     // restaurant
      System.out.println("Phone Number: " + tempResObj.getPhone()); // Print the restaurant's phone
                                                                    // // number
      System.out.println("Location: " + tempResObj.getLocation()); // Print the restaurants location
      System.out.println("Menu:"); // Indicate the start of the menu items listing

      // Loop through each menu item (food name) for this restaurant
      for (String item : tempResObj.getMenu().keySet()) {
        Double price = tempResObj.getMenu().get(item); // Get the price associated with the current
                                                       // menu item
        System.out.println("  " + item + " : R" + price); // Print the menu item and its price
      }

      System.out.println(); // Print a blank line for readability between restaurants
    }
  }

  // print available Restaurants to customer to choose
  public void restaurantList() {
    // Check if map is empty(no restaurants added)
    if (restaurantMap.isEmpty()) {
      System.out.println("No restaurants registered yet...Will be added soon");
      return;
    }
    System.out.println("==================================");
    System.out.println("All Registered Restaurants:");
    // loop to display restaurants
    for (String key : restaurantMap.keySet()) { // Iterate over each restaurant's unique key in the
                                                // map
      Restaurant tempResObj = restaurantMap.get(key); // Retrieve the Restaurant object associated
                                                      // with the current key
      System.out.println("Name: " + tempResObj.getName()); // Print the restaurant's name
      System.out.println("Location: " + tempResObj.getLocation()); // Print the restaurants location
      System.out.println("==================================");
    }
  }

  // Display selected Restaurant info(pass selected restaurant as parameter)
  public void displayRestaurant(String restaurant) {
    System.out.println("==================================");
    System.out.println("Restaurant Details:");
    System.out.println("==================================");
    // loop to display restaurants
    for (String key : restaurantMap.keySet()) { // Iterate over each restaurants unique key in the
                                                // map
      Restaurant tempResObj = restaurantMap.get(key); // Retrieve the Restaurant object associated
                                                      // with the current key
      // find restaurant that matches user restaurant
      if (key.equals(restaurant)) {
        System.out.println("Name: " + tempResObj.getName());
        System.out.println("Location: " + tempResObj.getLocation());

        for (String item : tempResObj.getMenu().keySet()) {
          Double price = tempResObj.getMenu().get(item);
          System.out.println("  " + item + " : R" + price);
        }

        System.out.println("==================================");
        System.out.println(); // Readability
      }
    }
  }

  // Method to get the location of a restaurant by name
  public String getRestaurantLocation(String restaurantName) {
    Restaurant restaurant = restaurantMap.get(restaurantName);
    if (restaurant != null) {
      return restaurant.getLocation();
    }
    return "";
  }

  // Getter to access restaurant Location
  public Restaurant getRestaurant(String name) {
    return restaurantMap.get(name);
  }



}
