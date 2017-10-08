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
    private Integer imageId;
    private String datetime;

    private SimpleDateFormat fmt;

    private void setImportanceIcon() {
        switch (this.importance) {
            case 0: impImageId = null; break;
            case 1: impImageId = R.drawable.star_1; break;
            case 2: impImageId = R.drawable.star_2; break;
        }
    }

    Note(String name, String description, Integer imageId, int imp, Date date) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
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

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) { this.imageId = imageId; }

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
