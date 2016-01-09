package se.mharrys.tdw.article;

/**
 * Describes an article item for TheDailyWTF.
 */
public class ArticleItemWTF implements ArticleItem {
    private int id;
    private String title;

    public ArticleItemWTF(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
