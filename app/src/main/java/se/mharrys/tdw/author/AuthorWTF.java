package se.mharrys.tdw.author;

/**
 * Describes an author for TheDailyWTF.
 */
public class AuthorWTF implements Author {
    private String name;

    public AuthorWTF(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
