package group1.unisim;

public class Thought {
    private String title;
    private String description;
    private int modification;

    public Thought() {}

    public Thought(String _title, String _description, int _modification) {
        title = _title;
        description  = _description;
        modification = _modification;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getModification() {
        return modification;
    }
}
