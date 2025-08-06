import java.util.Collection;
import java.util.HashMap;

public class PasswordStore {
    private HashMap<String, Menu.PasswordEntry> store;

    public PasswordStore() {
        store = new HashMap<>();
    }

    public void addEntry(Menu.PasswordEntry entry) {

        String key = entry.getName().toLowerCase();
        store.put(key, entry);
    }

    public void deleteEntry(Menu.PasswordEntry entry) {
        if (entry == null) return;
        store.remove(entry.getName().toLowerCase());
    }

    public Menu.PasswordEntry getEntry(String name) {
        if (name == null) return null;
        return store.get(name.toLowerCase());
    }

    public Collection<Menu.PasswordEntry> getEntries() {
        //fixme return all entries
        return store.values();
    }


}
