import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private String filePath;

    UserManager(String filePath) {
        users = null;
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            users = (Map<String, User>) ois.readObject();
        } catch (EOFException e) {
            users = new HashMap<>();
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        if (users == null)
        {
            users = new HashMap<>();
        }
    }

    private void serializeUsers() {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String userName, String password) {
        if (users.containsKey(userName)) {
            return false;
        } else {
            users.put(userName, new RegularUser(userName, password));
            serializeUsers();
            return true;
        }
    }

    public void addAdmin(User admin) {
        if (!users.containsKey(admin.getUserName())) {
            users.put(admin.getUserName(), admin);
            serializeUsers();
        }
    }

    public User loginUser(String userName, String password) {
        User user = users.get(userName);
        if (user == null) {
            return null;
        } else if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public void serializeChanges()
    {
        serializeUsers();
    }
}
