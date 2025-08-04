import java.util.HashMap;

/**
 * Represents a single restaurant with its name, phone number, location, and menu. Stores the
 * details and menu items with prices for one restaurant. Difference: This class models one
 * restaurant's information and menu.
 */
public class Restaurant {
  // initialize fields
  private String name;
  private String phone;
  private String location;
  private HashMap<String, Double> menu;

  // Constructor
  public Restaurant(String name, String phone, String location, HashMap<String, Double> menu) {
    this.name = name;
    this.phone = phone;
    this.location = location;
    this.menu = menu;

  }

  // getter methods
  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getLocation() {
    return location;
  }

  public HashMap<String, Double> getMenu() {
    return menu;
  }
}
