import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    public void testMovieCreation() {
        Movie movie = new Movie("ABC123", "The Matrix", new String[]{"Action", "Sci-Fi"});
        assertNotNull(movie);
        assertEquals("ABC123", movie.getMovieId());
        assertEquals("The Matrix", movie.getTitle());
        assertArrayEquals(new String[]{"Action", "Sci-Fi"}, movie.getGenres());
    }
}
