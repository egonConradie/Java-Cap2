import java.util.Scanner;

/**
 * CLASS OVERVIEW: Represents a customer in the QuickFoods Ordering System, handles collecting and
 * storing customer details and special instructions.
 */
public class Customer {
  // initialize fields
  private String name;
  private String phoneNumber;
  private String city;
  private String address;
  private String email;
  private String specialInstructions;

  // Empty constructor
  public Customer() {}

  // collect user information
  public void collectInput(Scanner scanner) {
    // user name
    System.out.println("Please enter your full name & surname: ");
    this.name = scanner.nextLine();

    // number
    while (true) {
      System.out.println("Please enter your 10-digit number: ");
      String number = scanner.nextLine();

      if (checkNumberLength(number)) {
        this.phoneNumber = number;
        break;
      }
      System.out.println("Invalid number, please re-enter.");
    }

    System.out.println("Enter your city: ");
    String cityInput = scanner.nextLine().trim();
    this.city = cityInput;


    // address
    System.out.println("Please enter your home address: ");
    this.address = scanner.nextLine();
    // email
    System.out.println("Please enter your email address: ");
    this.email = scanner.nextLine();
  }

  // Instructions prompt method
  public void collectSpecialInstructions(Scanner scanner) {
    System.out.println("Would you like to add any special instructions added to your order (y/n):");
    String addInstructions = scanner.nextLine();
    if (addInstructions.equalsIgnoreCase("y")) {
      System.out.println("Please enter special instructions:");
      this.specialInstructions = scanner.nextLine();
    } else {
      this.specialInstructions = "";
    }
  }

  // Number validation && check for non-integers
  public static boolean checkNumberLength(String number) {
    return number.length() == 10 && number.matches("\\d+");
  }

  // Getters for all private fields
  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getCity() {
    return city;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getSpecialInstructions() {
    return specialInstructions;
  }

}
