import java.io.Serializable;
import java.time.LocalDate;

public class Movie implements Serializable {
    private String title;
    private String genere;
    private LocalDate releaseDate;
    private int likes;

    Movie(String title, String genere, LocalDate releaseDate)
    {
        this.title = title;
        this.genere = genere;
        this.releaseDate = releaseDate;
        likes = 0;
    }
    public int getLikeNr() {return likes;}
    public String getTitle() {return title;}
    public String getGenere() {return genere;}
    public LocalDate getReleaseDate() {return releaseDate;}
    public void likeMovie()
    {
        likes++;
    }
    public void dislikeMovie()
    {
        if(likes > 0)
        {
            likes--;
        }
    }
    public String movieInfoToString()
    {
        return "Movie: " + title + " | Genere: " + genere + " | Relesase Date: " + releaseDate + " | Likes: " + likes + " | ";
    }

}
