import java.util.List;

public class Movie {
    private String title;
    private String movieId;
    private List<String> genres;

    public Movie(String title, String movieId, List<String> genres) {
        this.title = title;
        this.movieId = movieId;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getMovieId() {
        return movieId;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}



