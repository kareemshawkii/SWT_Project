import java.util.Set;

public class User {
    private String userId;
    private String userName;
    private Set<String> likedMovieIds;

    public User(String userId, String userName, Set<String> likedMovieIds) {
        this.userId = userId;
        this.userName = userName;
        this.likedMovieIds = likedMovieIds;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Set<String> getLikedMovieIds() {
        return likedMovieIds;
    }
}
