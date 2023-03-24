import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;

public class Menu {
    private UserManager userManager;
    private MovieManager movieManager;
    private User userData;
    private List<Movie> userFeed;

    Menu(UserManager userManager, MovieManager movieManager)
    {
        this.userManager = userManager;
        this.movieManager = movieManager;
        this.userFeed = movieManager.getMovies();

        userData = null;
    }
    private void organizeFeed()
    {
        Map<String, Integer> genereInfo = ((RegularUser)userData).getGenereInfo();

        userFeed.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                int likesGenereM1 = genereInfo.getOrDefault(m1.getGenere(), 0);
                int likesGenereM2 = genereInfo.getOrDefault(m2.getGenere(), 0);
                return Integer.compare(likesGenereM2, likesGenereM1);
            }
        });
    }
    private void mostLiked()
    {
        Map<String, Integer> genereInfo = ((RegularUser)userData).getGenereInfo();
        organizeFeed();
        Collections.sort(userFeed, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                int likesGenereM1 = genereInfo.getOrDefault(m1.getGenere(), 0);
                int likesGenereM2 = genereInfo.getOrDefault(m2.getGenere(), 0);
                int genereCompare = Integer.compare(likesGenereM2, likesGenereM1);
                if (genereCompare != 0)
                {
                    return  genereCompare;
                }
                else {
                    return Integer.compare(m2.getLikeNr(), m1.getLikeNr());
                }
            }
        });
    }
    private void mostRecent()
    {
        Map<String, Integer> genereInfo = ((RegularUser)userData).getGenereInfo();
        organizeFeed();
        Collections.sort(userFeed, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                int likesGenereM1 = genereInfo.getOrDefault(m1.getGenere(), 0);
                int likesGenereM2 = genereInfo.getOrDefault(m2.getGenere(), 0);
                int genereCompare = Integer.compare(likesGenereM2, likesGenereM1);
                if (genereCompare != 0)
                {
                    return  genereCompare;
                }
                else {
                    return m2.getReleaseDate().compareTo(m1.getReleaseDate());
                }
            }
        });
    }
    private void printUserFeed(HashSet<String> likedMovies)
    {
        for (Movie movie : userFeed)
        {
            if (likedMovies.contains(movie.getTitle()))
            {
                System.out.println(movie.movieInfoToString() + " - liked");
            }
            else {
                System.out.println(movie.movieInfoToString());
            }
        }
    }
    private void showStartOptions()
    {
        System.out.println("Commands: login , register , exit");
        System.out.println("Enter a command");
    }
    private void showUserOptions()
    {
        System.out.println("Commands: showFeed , likeMovie , removeLike , mostLiked , mostRecent , logout  exit");
        System.out.println("Enter a command");
    }
    private boolean chooseUserOptions()
    {
        showUserOptions();
        String option;
        String title;
        Scanner sc = new Scanner(System.in);
        while(true) {
            option = sc.nextLine();
            switch (option) {
                case("likeMovie"):
                    System.out.println("Enter the movies name");
                    title = sc.nextLine();
                    Movie movie = movieManager.getMovie(title);
                    if (movie != null)
                    {
                        if (((RegularUser) userData).addLikedMovie(title, movie.getGenere()))
                        {
                            movieManager.likeMovie(title);
                            System.out.println("You have successfully liked the movie!");
                            userManager.serializeChanges();
                        }
                        else {
                            System.out.println("You have already liked the movie!");
                        }
                    }
                    else{
                        System.out.println("This movie doesn't exist!");
                    }
                    break;
                case("showFeed"):
                    organizeFeed();
                    printUserFeed(((RegularUser) userData).getLikedMovies());
                    break;
                case("removeLike"):
                    System.out.println("Enter the movies name");
                    title = sc.nextLine();
                    if (((RegularUser) userData).removeLikedMovie(title))
                    {
                        movieManager.dislikeMovie(title);
                    }
                    break;
                case("mostLiked"):
                    mostLiked();
                    printUserFeed(((RegularUser) userData).getLikedMovies());
                    break;
                case("mostRecent"):
                    mostRecent();
                    printUserFeed(((RegularUser) userData).getLikedMovies());
                    break;
                case("logout"):
                    userData = null;
                    System.out.println("You have been logged out!");
                    return false;
                case("exit"):
                    return true;
                default:
                    System.out.println("Incorrect command");
                    break;
            }
        }
    }
    private void showAdminOptions()
    {
        System.out.println("Commands: addMovie , logout , exit");
        System.out.println("Enter a command");
    }
    private boolean chooseAdminOptions()
    {
        showAdminOptions();
        String option;
        String title;
        String genere;
        String releaseDate;

        Scanner sc = new Scanner(System.in);
        while(true) {
            option = sc.nextLine();
            switch (option) {
                case("addMovie"):
                    System.out.println("Enter the movie Name:");
                    title = sc.nextLine();
                    System.out.println("Enter the movie genere:");
                    genere = sc.nextLine();
                    System.out.println("Enter the movie release date (yyyy-mm-dd):");
                    Optional<LocalDate> date = Optional.empty();
                    while(date.isEmpty())
                    {
                        releaseDate = sc.nextLine();
                        try{
                            date = Optional.of(LocalDate.parse(releaseDate));
                        }
                        catch(DateTimeParseException e)
                        {
                            System.out.println("Date format not accepted");
                        }
                    }
                    movieManager.addMovie(new Movie(title, genere, date.get()));
                    System.out.println("The movie was succsessfully added!");
                    break;
                case("logout"):
                    userData = null;
                    System.out.println("You have been looged out!");
                    return false;
                case ("exit"):
                    return true;
                default:
                    System.out.println("Incorrect command");
                    break;
            }
        }
    }

    private boolean chooseStartOption()
    {
        showStartOptions();
        String option;
        String userName;
        String password;
        Scanner sc = new Scanner(System.in);
        while(true)
        {
        option = sc.nextLine();
        switch (option) {
            case ("login"):
                System.out.println("Enter your username:");
                userName = sc.nextLine();
                System.out.println("Enter your password:");
                password = sc.nextLine();
                userData = userManager.loginUser(userName, password);
                if (userData == null) {
                    System.out.println("Incorrect account!");
                } else {
                    System.out.println("Login succsesfull!");
                    return false;
                }
                break;
            case ("register"):
                System.out.println("Choose your username:");
                userName = sc.nextLine();
                System.out.println("Choose your password:");
                password = sc.nextLine();
                if (userManager.registerUser(userName, password))
                {
                    System.out.println("You have been registered!");
                }
                else {
                    System.out.println("This username is already taken!");
                }
                break;
            case ("exit"):
                return true;
            default:
                System.out.println("Incorrect command");
                break;
            }
        }
    }
    public void start()
    {
        boolean exit = false;

        while(!exit) {
            exit = chooseStartOption();
            if (userData == null || exit) {
                return;
            }
            if (userData instanceof RegularUser) {
                System.out.println("You are logged in as an RegularUser");
                exit = chooseUserOptions();
            } else if (userData instanceof Admin) {
                System.out.println("You are logged in as an Admin");
                exit = chooseAdminOptions();
            }
        }
    }
}
