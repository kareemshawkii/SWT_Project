import java.util.Set;

public class Validator {

    public static boolean validateTitle(String title) {
        String[] words = title.trim().split(" ");
        for (String word : words) {
            if (word.isEmpty() || !Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateMovieId(String movieId, String title, Set<String> existingIds) {
        StringBuilder capitalLetters = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (Character.isUpperCase(c)) {
                capitalLetters.append(c);
            }
        }

        String expectedStart = capitalLetters.toString();
        if (!movieId.startsWith(expectedStart)) {
            return false;
        }

        String digits = movieId.substring(expectedStart.length());
        if (!digits.matches("\\d{3}")) {
            return false;
        }

        Set<Character> digitSet = new java.util.HashSet<>();
        for (char c : digits.toCharArray()) {
            digitSet.add(c);
        }

        /*if (digitSet.size() != 3 || existingIds.contains(movieId)) { // need to be implemented
            return false;
        }*/

        return true;
    }

    public static boolean validateUserName(String name) {
        if (name.startsWith(" ")) return false;
        return name.matches("[A-Za-z ]+");
    }

    public static boolean validateUserId(String userId, Set<String> existingIds) {
        if (userId.length() != 9) return false;
        if (!userId.matches("\\d{8}[A-Za-z]?")) return false;
        return !existingIds.contains(userId);
    }

}
