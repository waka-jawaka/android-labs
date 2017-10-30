package algie.lab3;

/**
 * Created by me on 03.10.17.
 */

public class Note {
    private long id;
    private String name;
    private String description;
    private Integer impImageId;
    private int importance;
    private String imagePath;
    private String datetime;

    private void setImportanceIcon() {
        switch (this.importance) {
            case 0: impImageId = null; break;
            case 1: impImageId = R.drawable.star_1; break;
            case 2: impImageId = R.drawable.star_2; break;
        }
    }

    public Note(long id, String name, String description, String imagePath, int imp, String datetime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.importance = imp;
        this.datetime = datetime;

        setImportanceIcon();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) { this.description = desc; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getImportance() { return importance; }

    public void setImportance(int imp) {
        this.importance = imp;
        setImportanceIcon();
    }

    public String getDatetime() {
        return datetime;
    }

    public Integer getImpImageId() {
        return impImageId;
    }
}
