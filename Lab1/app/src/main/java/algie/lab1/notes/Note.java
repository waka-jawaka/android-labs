package algie.lab1.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import algie.lab1.R;

/**
 * Created by me on 03.10.17.
 */

class Note implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.impImageId);
        dest.writeInt(this.importance);
        dest.writeString(this.imagePath);
        dest.writeString(this.datetime);
        dest.writeSerializable(this.fmt);
    }

    protected Note(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.impImageId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.importance = in.readInt();
        this.imagePath = in.readString();
        this.datetime = in.readString();
        this.fmt = (SimpleDateFormat) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
