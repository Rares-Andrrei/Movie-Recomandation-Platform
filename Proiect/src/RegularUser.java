import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
public class RegularUser extends User implements Serializable {
    Map<String, Integer> genereInfo;
    HashSet<String> likedMovies;
    RegularUser(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
        likedMovies = new HashSet<>();
        genereInfo = new HashMap<>();
    }
    public Map<String, Integer> getGenereInfo()
    {
        return genereInfo;
    }
    public boolean addLikedMovie(String title, String genere)
    {
        if (!likedMovies.contains(title))
        {
            likedMovies.add(title);
            int value = genereInfo.getOrDefault(genere, 0) + 1;
            genereInfo.put(genere, value);
            return true;
        }
        return false;
    }
    public boolean removeLikedMovie(String title)
    {
        if (likedMovies.contains(title))
        {
            likedMovies.remove(title);
            return true;
        }
        return false;
    }
    HashSet<String> getLikedMovies()
    {
        return likedMovies;
    }
}
