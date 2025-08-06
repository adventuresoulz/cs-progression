import java.util.Collection;
import java.util.Scanner;

public class Menu {
  private PasswordStore store;
  private Scanner scanner;
    //constructor for menu
    public Menu () {

        store = new PasswordStore();
        scanner = new Scanner(System.in);
    }
    //here i decided not to make a seperate class for an password object
    //seeing as our menu would handle receiving entries it can handle the creation of the object at the same time
    public static class PasswordEntry {
       String name;
       String password;

        public PasswordEntry(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public String getName() {
            return name;
        }
        public String toString() {
            return "Name: " + name + " Password: " + password;
        }
    }
    public void displayMenu() {

        System.out.println("\n--- Password Manager ---");
        System.out.println("1. Add Entry");
        System.out.println("2. Delete Entry");
        System.out.println("3. Search Entry");
        System.out.println("4. Show all entries");
        System.out.println("5. Exit");
        //this code will be used to display our menu and will more likely than not be used with
        //our handle input method as we need to constantly be calling a menu.
        //will consider submenus at some point
    }

    public void handleInput() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addEntry();
                    break;
                    case "2":
                        removeEntry();
                        break;
                        case "3":
                            searchEntry();
                            break;
                            case "4":
                                try {
                                    Collection<PasswordEntry> entries = store.getEntries();
                                    if (entries.isEmpty()) {
                                        System.out.println("No entries found");
                                    } else {
                                        for (PasswordEntry entry : entries) {
                                            System.out.println(entry);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error displaying entries: " + e.getMessage());
                                }
                                break;

                                case "5":
                                    running = false;
                                    System.out.println("Exiting...");
                                    break;

                                    default:
                                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
            }
        }
        //will probably be a switch case used with our other methods
    }

    public void addEntry(){
        System.out.println("Enter site/app name: ");
        String name = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        PasswordEntry entry = new PasswordEntry(name, password);
        store.addEntry(entry);
        System.out.println("Entry added.");
        //add new entry consisting of website or app name, and password to be stored
    }

    public void removeEntry(){
        System.out.println("Enter site/app to remove: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        try {
            if (store.getEntry(name) != null) {
                store.deleteEntry(store.getEntry(name));
                System.out.println("Entry removed.");
            } else {
                System.out.println("Entry not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing entry: " + e.getMessage());
        }

        //remove from storage will probably function via website or app name non case sensitive
    }

    public void searchEntry(){
        System.out.println("Enter site/app to search: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return;
        }
        try {
        PasswordEntry entry = store.getEntry(name);
        if (entry != null) {
            System.out.println(entry);
        }
        else {
            System.out.println("No entry found for \"" + name + "\".");
        }
        //our way of searching up something non case sensitive
    } catch (Exception e) {
            System.out.println("Something went wrong while searching: " + e.getMessage());}
    }

}
