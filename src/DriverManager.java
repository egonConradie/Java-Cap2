import java.io.File; // Import File class to handle file operations
import java.io.FileNotFoundException; // Import exception for handling missing file errors
import java.util.ArrayList; // Import ArrayList to store dynamic lists of data
import java.util.Scanner; // Import Scanner to read from file

// Class overview:
// Handles loading delivery driver information from a file (drivers.txt)
// Stores each driver's name, location, and load in separate ArrayLists
public class DriverManager {

  // List to store driver names
  static ArrayList<String> driversNames = new ArrayList<>();

  // List to store driver locations
  static ArrayList<String> driversLocations = new ArrayList<>();

  // List to store driver loads (number of deliveries they're currently handling)
  static ArrayList<Integer> driversLoads = new ArrayList<>();

  // Method to load driver data from a file
  public void loadDriversFromFile(String fileName) {
    try {
      // Create a File object from the provided filename
      File file = new File(fileName);

      // Create a Scanner object to read from the file
      Scanner scanner = new Scanner(file);

      // Loop through each line in the file
      while (scanner.hasNextLine()) {
        // Read the next line from the file
        String line = scanner.nextLine();

        // Skip the line if it is empty
        if (line.trim().equals("")) {
          continue;
        }

        // Split the line into parts using a comma as the delimiter
        String[] parts = line.split(",");

        // Skip this line if it doesn't contain exactly 3 parts
        if (parts.length != 3) {
          continue;
        }

        // Extract and trim the name, location, and load string
        String name = parts[0].trim();
        String location = parts[1].trim();
        String loadString = parts[2].trim();

        int load;
        try {
          // Try to convert the load string to an integer
          load = Integer.parseInt(loadString);
        } catch (NumberFormatException e) {
          // Skip this line if the load is not a valid number
          continue;
        }

        // Add the valid driver info to the respective ArrayLists
        driversNames.add(name);
        driversLocations.add(location);
        driversLoads.add(load);
      }

      // Close the scanner to free resources
      scanner.close();
    } catch (FileNotFoundException e) {
      // If file is not found, show error message
      System.out.println("Delivery drivers not found.");
    }
  }

  // Method to find the best driver for a given customer and restaurant location
  public String findDriver(Customer customer, String restaurantLocation) {
    // Get the city of the customer
    String customerCity = customer.getCity();

    // Check if the customer and restaurant are in the same city
    if (!customerCity.equalsIgnoreCase(restaurantLocation)) {
      // Return an error message if cities don't match
      return "Customer and restaurant are not in the same city.";
    }

    // Variables to keep track of the best driver and their load
    String bestDriver = null;
    int bestLoad = -1;

    // Loop through the list of drivers
    for (int i = 0; i < driversNames.size(); i++) {
      // Get the current driver's name, city, and load
      String driverName = driversNames.get(i);
      String driverCity = driversLocations.get(i);
      int driverLoad = driversLoads.get(i);

      // Check if the driver is in the same city as the customer
      if (driverCity.equalsIgnoreCase(customerCity)) {
        // Select the driver if this is the first match or a lower load
        if (bestDriver == null || driverLoad < bestLoad) {
          bestDriver = driverName;
          bestLoad = driverLoad;
        }
      }
    }

    // If no driver matched the city
    if (bestDriver == null) {
      return "No driver found in the area.";
    }

    // Return the best driver's name
    return bestDriver;
  }
}

/*
 * 
 * // Method checks if lines driver matches user Location(line = current file read line // location
 * =user location) public static void addValidDrivers(String line, String customerCity, String
 * restaurantLocation) { if (line == null || line.trim().isEmpty()) { return; }
 * 
 * String[] parts = line.split(",");
 * 
 * String currentDriverName = parts[0].trim(); String currentDriverLocation = parts[1].trim(); int
 * currentDriverLoad;
 * 
 * try { currentDriverLoad = Integer.parseInt(parts[2].trim()); } catch (NumberFormatException e) {
 * return; } // Check if driver location matches either customer city or restaurant location //
 * (case-insensitive) if (currentDriverLocation.equalsIgnoreCase(customerCity) ||
 * currentDriverLocation.equalsIgnoreCase(restaurantLocation)) {
 * driversNames.add(currentDriverName); driversLocations.add(currentDriverLocation);
 * driversLoads.add(currentDriverLoad); } }
 * 
 * // METHOD: Get driver with lowest workload public static String[] getDriverWithLowestLoad() { int
 * lowestLoad = driversLoads.get(0); // set first index of arrayList as currentLowest load for int
 * lowestIndex = 0; // start with the first index
 * 
 * // Store drivers info that will be used for the job in array String[] driverAssignedInfo = new
 * String[3];
 * 
 * // start comparing from index 1 so we dont compare element 0 with element 0 for (int i = 1; i <
 * driversLoads.size(); i++) { // check if driverLoad at index 1 smaller than lowestLoast(set to
 * index 0 ) if (driversLoads.get(i) < lowestLoad) { lowestLoad = driversLoads.get(i); // if smaller
 * update lowestIndex = i; // Keep track of lowest load index
 * 
 * } } // Capture drivers Personal information with lowest load String selectedDriverName =
 * driversNames.get(lowestIndex); String selectedDriverLocation = driversLocations.get(lowestIndex);
 * String selectedDriverLowestLoad = Integer.toString(lowestLoad);
 * 
 * // assign drivers info to array driverAssignedInfo[0] = selectedDriverName; driverAssignedInfo[1]
 * = selectedDriverLocation; driverAssignedInfo[2] = selectedDriverLowestLoad;
 * 
 * // return array of drivers info with lowest load return driverAssignedInfo;
 * 
 * }
 */


