package algie.lab1.notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import algie.lab1.R;

/**
 * Created by me on 03.10.17.
 */

class Note {
    private String name;
    private String description;
    private Integer impImageId;
    private int importance;
    private String imagePath;
    private String datetime;

    private SimpleDateFormat fmt;

    private void setImportanceIcon() {
        switch (this.importance) {
            case 0: impImageId = null; break;
            case 1: impImageId = R.drawable.star_1; break;
            case 2: impImageId = R.drawable.star_2; break;
        }
    }

    Note(String name, String description, String imagePath, int imp, Date date) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.importance = imp;

        this.fmt = new SimpleDateFormat("d EEE HH:mm", Locale.getDefault());

        setImportanceIcon();
        setDatetime(date);
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

    public void setDatetime(Date date) {
        this.datetime = fmt.format(date);
    }

    public Integer getImpImageId() {
        return impImageId;
    }
}
