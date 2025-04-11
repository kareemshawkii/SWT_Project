import java.util.List;

public class User {
    private final String name;
    private final String userId;
    private final List<String> likedMovieIds;
    private List<String> recommendedMovies; // Added field for recommended movies

    public User(String name, String userId, List<String> likedMovieIds) {
        this.name = name;
        this.userId = userId;
        this.likedMovieIds = likedMovieIds;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getLikedMovieIds() {
        return likedMovieIds;
    }

    public List<String> getRecommendedMovies() {
        return recommendedMovies;
    }

    public void setRecommendedMovies(List<String> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }
}
