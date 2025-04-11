import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class UserTest {

    @Test
    public void testUserCreation() {
        Set<String> likedMovies = new HashSet<>();
        likedMovies.add("M001");
        likedMovies.add("M002");

        User user = new User("U001", "John Doe", likedMovies);

        assertNotNull(user);
        assertEquals("U001", user.getUserId());
        assertEquals("John Doe", user.getUserName());
        assertTrue(user.getLikedMovieIds().contains("M001"));
        assertTrue(user.getLikedMovieIds().contains("M002"));
    }
}
