public class Movie {
    private String movieId;
    private String title;
    private String[] genres;

    // Constructor
    public Movie(String movieId, String title, String[] genres) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }

    // Getters
    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String[] getGenres() {
        return genres;
    }
}
