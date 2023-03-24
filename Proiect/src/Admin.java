import java.io.Serializable;
public class Admin extends User implements Serializable {
    Admin(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }
}