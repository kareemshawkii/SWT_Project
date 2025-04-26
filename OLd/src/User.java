import java.util.List;

public class User {
    private String name;
    private String userId;
    private List<String> likedMovieIds;
    private List<String> recommendedMovies;

    public User(String name, String userId, List<String> likedMovieIds) {
        this.name = name;
        this.userId = userId;
        this.likedMovieIds = likedMovieIds;
    }

    // Getters
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLikedMovieIds(List<String> likedMovieIds) {
        this.likedMovieIds = likedMovieIds;
    }

    public void setRecommendedMovies(List<String> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }
}
