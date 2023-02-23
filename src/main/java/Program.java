import DAO.ManufactureDAO;
import DAO.PhoneDAO;
import Pojo.Phone;
import Pojo.Manufacture;

import java.util.*;

public class Program {
    private static PhoneDAO phoneDAO;
    private static ManufactureDAO manufactureDAO;

    public static void main(String[] args) {
        // Initialize the PhoneDAO
        phoneDAO = new PhoneDAO();
        manufactureDAO = new ManufactureDAO();

        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Display the menu and process user input
        int choice = -1;
        do {
            System.out.println("Phone CRUD Menu");
            System.out.println("1. Create a phone");
            System.out.println("2. View all phones");
            System.out.println("3. Update a phone");
            System.out.println("4. Delete a phone By ID");
            System.out.println("5. Delete a phone By Object");
            System.out.println("6. View a phone");
            System.out.println("7. View all manufacturers");
            System.out.println("8. View a manufacturer");
            System.out.println("9. Add a manufacturer");
            System.out.println("10. Update a manufacturer");
            System.out.println("11. Remove a manufacturer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createPhone(scanner);
                    break;
                case 2:
                    viewAllPhones();
                    break;
                case 3:
                    updatePhone(scanner);
                    break;
                case 4:
                    removePhoneById(scanner);
                    break;
                case 5:
                    removePhoneByObject(scanner);
                    break;
                case 6:
                    viewPhone(scanner);
                    break;
                case 7:
                    viewAllManufactures(manufactureDAO);
                    break;
                case 8:
                    viewManufacture(scanner, manufactureDAO);
                    break;
                case 9:
                    addManufacturer(scanner);
                    break;
                case 10:
                    updateManufacturer(scanner);
                    break;
                case 11:
                    removeManufacturer(scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        // Close the scanner
        scanner.close();
    }

    private static void createPhone(Scanner scanner) {
        System.out.println("Create a phone");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        Phone phone = new Phone(name, price, color, country, quantity);
        boolean success = phoneDAO.add(phone);
        if (success) {
            System.out.println("Phone created successfully.");
        } else {
            System.out.println("Failed to create phone.");
        }
    }

    private static void viewAllPhones() {
        System.out.println("View all phones");
        for (Phone phone : phoneDAO.getAll()) {
            System.out.println(phone);
        }
    }

    private static void updatePhone(Scanner scanner) {
        System.out.println("Update a phone");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Phone phone = phoneDAO.get(id);
        if (phone == null) {
            System.out.println("Phone not found.");
            return;
        }
        System.out.print("Enter name (" + phone.getName() + "): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = phone.getName();
        }
        System.out.print("Enter price (" + phone.getPrice() + "): ");
        String priceStr = scanner.nextLine();
        int price = priceStr.isEmpty() ? phone.getPrice() : Integer.parseInt(priceStr);
        System.out.print("Enter color (" + phone.getColor() + "): ");
        String color = scanner.nextLine();
        if (color.isEmpty()) {
            color = phone.getColor();
        }
        System.out.print("Enter country (" + phone.getCountry() + "): ");
        String country = scanner.nextLine();
        if (country.isEmpty()) {
            country = phone.getCountry();
        }
        System.out.print("Enter quantity (" + phone.getQuantity() + "): ");
        String quantityStr = scanner.nextLine();
        int quantity = quantityStr.isEmpty() ? phone.getQuantity() : Integer.parseInt(quantityStr);
        phone.setName(name);
        phone.setPrice(price);
        phone.setColor(color);
        phone.setCountry(country);
        phone.setQuantity(quantity);
        boolean updated = phoneDAO.update(phone);
        if (updated) {
            System.out.println("Phone with ID " + id + " has been updated.");
        } else {
            System.out.println("Failed to update phone with ID " + id + ".");
        }
    }

    private static void removePhoneById(Scanner scanner) {
        System.out.println("Remove a phone by ID");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean isRemoved = phoneDAO.remove(id);
        if (isRemoved) {
            System.out.println("Phone removed successfully.");
        } else {
            System.out.println("Phone not found.");
        }
    }

    private static void removePhoneByObject(Scanner scanner) {
        System.out.println("Remove a phone by object");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Phone phone = phoneDAO.get(id);
        if (phone == null) {
            System.out.println("Phone not found.");
            return;
        }
        boolean isRemoved = phoneDAO.remove(phone);
        if (isRemoved) {
            System.out.println("Phone removed successfully.");
        } else {
            System.out.println("Failed to remove phone.");
        }
    }

    private static void viewPhone(Scanner scanner) {
        System.out.println("View a phone");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Phone phone = phoneDAO.get(id);
        if (phone == null) {
            System.out.println("Phone not found.");
            return;
        }
        System.out.println(phone.toString());
    }


    private static void viewManufacture(Scanner scanner, ManufactureDAO manufactureDAO) {
        System.out.println("View a manufacture");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Manufacture manufacture = manufactureDAO.get(id);
        if (manufacture == null) {
            System.out.println("Manufacture not found.");
        } else {
            System.out.println(manufacture);
        }
    }

    private static void viewAllManufactures(ManufactureDAO manufactureDAO) {
        System.out.println("View all manufactures");
        List<Manufacture> manufactures = manufactureDAO.getAll();
        if (manufactures.isEmpty()) {
            System.out.println("No manufactures found.");
        } else {
            for (Manufacture manufacture : manufactures) {
                System.out.println(manufacture);
            }
        }
    }

    public static void addManufacturer(Scanner scanner) {
        System.out.println("Add a manufacturer");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter location: ");
        String country = scanner.nextLine();
        System.out.print("Enter number of employee: ");
        int employee = Integer.parseInt(scanner.nextLine());
        Manufacture manufacture = new Manufacture(name, country, employee);
        if (manufactureDAO.add(manufacture)) {
            System.out.println("Manufacturer added successfully.");
        } else {
            System.out.println("Manufacturer addition failed.");
        }
    }

    public static void updateManufacturer(Scanner scanner) {
        System.out.println("Update a manufacturer");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Manufacture manufacture = manufactureDAO.get(id);
        if (manufacture == null) {
            System.out.println("Manufacturer not found.");
            return;
        }
        System.out.print("Enter name (" + manufacture.getName() + "): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = manufacture.getName();
        }
        System.out.print("Enter location (" + manufacture.getLocation() + "): ");
        String location = scanner.nextLine();
        if (location.isEmpty()) {
            location = manufacture.getLocation();
        }
        manufacture.setName(name);
        manufacture.setLocation(location);
        if (manufactureDAO.update(manufacture)) {
            System.out.println("Manufacturer updated successfully.");
        } else {
            System.out.println("Manufacturer update failed.");
        }
    }

    public static void removeManufacturer(Scanner scanner) {
        System.out.println("Remove a manufacturer");
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Manufacture manufacture = manufactureDAO.get(id);
        if (manufacture == null) {
            System.out.println("Manufacturer not found.");
            return;
        }
        if (manufactureDAO.remove(manufacture)) {
            System.out.println("Manufacturer removed successfully.");
        } else {
            System.out.println("Manufacturer remove failed.");
        }
    }
}
