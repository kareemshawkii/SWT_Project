import java.util.List;

public class Movie {
    private final String title;
    private final String movieId;
    private final List<String> genres;

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
}


