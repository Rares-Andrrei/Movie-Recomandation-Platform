import java.awt.*;
import java.io.Serializable;

public class User implements Serializable {
        protected String userName;
        protected String password;
public String getUserName() {return userName;}
public String getPassword() {return password;}

public void setUserName(String userName)
        {
        this.userName = userName;
        }
public void setPassword(String password)
        {
        this.password = password;
        }
}
