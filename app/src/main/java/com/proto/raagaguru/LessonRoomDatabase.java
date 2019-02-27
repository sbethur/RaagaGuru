package com.proto.raagaguru;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Lesson.class}, version = 1, exportSchema = false)
public abstract class LessonRoomDatabase extends RoomDatabase {

    public abstract LessonDao lessonDao();

    private static volatile LessonRoomDatabase INSTANCE;

    static LessonRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LessonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LessonRoomDatabase.class, "lesson_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
