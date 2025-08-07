import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.*;


public class PasswordManagerGUI {
    private static PasswordStore store = new PasswordStore(); //ref to access passwordstore
    public static void main(String[] args) {
        JFrame frame = new JFrame("Password Manager");

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        frame.setLayout(new BorderLayout());
//north: welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Password Manager", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Ariel", Font.BOLD, 16));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        //CENTER PANEL FOR BUTTONS
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1,10,10));
        JButton addButton = new JButton("Add Entry");
        JButton searchButton = new JButton("Search Entry");
        JButton viewButton = new JButton("View All Entries");
        JButton deleteButton = new JButton("Delete Entry");
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    //lets get these action buttons working
        //add entry
        addButton.addActionListener(e -> {
            //ask for a name
            String name = JOptionPane.showInputDialog(frame, "Enter site/app name");

            if (name == null) {
                JOptionPane.showMessageDialog(frame, "Operation Cancelled");
                return;
            }

            name = name.trim();
            //check for cancel or empty. reusued for password
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Entry name cannot be empty");
                    return;
                }

                String password = JOptionPane.showInputDialog(frame, "Enter password for " + name + ":");
                if (password == null) {
                   JOptionPane.showMessageDialog(frame, "Operation Cancelled");
                    return;
                }
                password = password.trim();
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Password cannot be empty");
                    return;
                }
                //create an entry and store it
                Menu.PasswordEntry entry = new Menu.PasswordEntry(name.trim(), password.trim());
                store.addEntry(entry);

                JOptionPane.showMessageDialog(frame, "Entry added successfully!");
            });





        searchButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter the name of the entry to search: ");
            if (name == null) {
                JOptionPane.showMessageDialog(frame, "Operation Cancelled");
                return;
            }
            name = name.trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Entry name cannot be empty");
                return;
            }

            Menu.PasswordEntry entry = store.getEntry(name);
            if (entry != null) {
                JOptionPane.showMessageDialog(frame, "Entry found!\n\nSite/App: " + entry.getName() + "\nPassword: " + entry.password);
            } else {
                JOptionPane.showMessageDialog(frame, "No entry found for \"" + name + "\"");
            }
        });


       viewButton.addActionListener(e -> {
           //window for saved entries....centered
           JFrame viewFrame = new JFrame("Saved Entries");
           viewFrame.setSize(400, 300);
           viewFrame.setLocationRelativeTo(frame);

           //panel to hold the entry labels
           JPanel entryPanel = new JPanel();
           entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS)); //stacked vertically

           Collection<Menu.PasswordEntry> entries = store.getEntries();

           if(entries.isEmpty()) {
               // if no entries show single label
               entryPanel.add(new JLabel("No entries found"));
           } else {
               //iteration to display each one
               for(Menu.PasswordEntry entry : entries) {
                   String labelText = "Site/App: " + entry.getName() + "  |  Password: " + entry.password;
                   entryPanel.add(new JLabel(labelText));
                   entryPanel.add(Box.createRigidArea(new Dimension(10, 0)));
               }
           }
           //PUT ENTRY PANEL INTO A SCROLL PANE
           JScrollPane scrollPane = new JScrollPane(entryPanel);
           scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

           //ADE THE SCROLL PANE TO WINDOW
           viewFrame.add(scrollPane);
           viewFrame.setVisible(true);
       });

        deleteButton.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(frame, "Enter the name of the entry to delete: ");
            if (name == null) {
                JOptionPane.showMessageDialog(frame, "Operation Cancelled");
                return;
            }

            name = name.trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Entry name cannot be empty");
                return;
            }

            Menu.PasswordEntry entry = store.getEntry(name);
            if (entry == null) {
                JOptionPane.showMessageDialog(frame, "No entry found for \"" + name + "\"");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                  frame,
                  "Are you sure you want to delete the entry for\"" + name + "\"?",
                  "Confirm Deletion",
                  JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                store.deleteEntry(name);
                JOptionPane.showMessageDialog(frame, "Entry deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Entry deletion failed!");
            }
        });









        }
}
