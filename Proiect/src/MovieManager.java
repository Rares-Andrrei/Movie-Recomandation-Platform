import java.util.Map;
import java.util.HashMap;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
public class MovieManager {
    private Map<String, Movie> movies;
    String filePath;

    MovieManager(String filePath) {
        movies = null;
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            movies = (Map<String, Movie>) ois.readObject();
        } catch (EOFException e) {
            movies = new HashMap<>();
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        if (movies == null)
        {
            movies = new HashMap<>();
        }
    }
    public void addMovie(Movie movie)
    {
        movies.put(movie.getTitle(), movie);
        serializeMovies();
    }
    public Movie getMovie(String title)
    {
        return movies.get(title);
    }
    public List<Movie> getMovies()
    {
        return new ArrayList<>(movies.values());
    }
    public boolean likeMovie(String title)
    {
        Movie movie = movies.get(title);
        if (movie != null)
        {
            movie.likeMovie();
            serializeMovies();
            return true;
        }
        else {
            return false;
        }
    }
    public void dislikeMovie(String title)
    {
       if (movies.containsKey(title))
       {
           Movie m = movies.get(title);
           if (m.getLikeNr() > 0)
           {
               m.dislikeMovie();
           }
       }
    }
    private void serializeMovies()
    {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean removeMovie(String title)
    {
        Movie removedMovie = movies.remove(title);
        return removedMovie != null;
    }
}
