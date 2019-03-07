package com.proto.raagaguru;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LessonDao dao;

        PopulateDbAsync(LessonRoomDatabase db) {
            dao = db.lessonDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();
            Lesson lesson = new Lesson("Lesson 1");
            dao.insert(lesson);
            lesson = new Lesson("Lesson 2");
            dao.insert(lesson);
            return null;
        }
    }
}
