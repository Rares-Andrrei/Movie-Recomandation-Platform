import java.time.LocalDate;
public class Main {
    public static void main(String[] args) {
        MovieManager movieManager = new MovieManager("D:\\Facultate\\An2Sem1\\MIP\\Proiect\\src\\movies.ser");
        UserManager userManager= new UserManager("D:\\Facultate\\An2Sem1\\MIP\\Proiect\\src\\users.ser");
        userManager.addAdmin(new Admin("rares", "rares123"));
        Menu menu = new Menu(userManager, movieManager);
        menu.start();
    }
}