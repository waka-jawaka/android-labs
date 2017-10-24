package algie.lab_2_1.db;

import android.provider.BaseColumns;

/**
 * Created by me on 13.10.17.
 */

public final class NoteDBContract {
    public NoteDBContract() { }

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_IMP = "importance";
        public static final String COLUMN_NAME_IMAGE = "image_path";
        public static final String COLUMN_NAME_DATETIME = "datetime";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_NAME + " TEXT," +
                    NoteEntry.COLUMN_NAME_DESC + " TEXT," +
                    NoteEntry.COLUMN_NAME_IMP + " INTEGER," +
                    NoteEntry.COLUMN_NAME_IMAGE + " TEXT," +
                    NoteEntry.COLUMN_NAME_DATETIME + " TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;
}
