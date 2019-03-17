package com.proto.raagaguru;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

enum DbOperation {
    INSERT,
    DELETE,
    UPDATE
}

class LessonRepository {

    private LessonDao lessonDao;
    private LiveData<List<Lesson>> allLessons;

    LessonRepository(Application application) {
        LessonRoomDatabase db = LessonRoomDatabase.getDatabase(application);
        lessonDao = db.lessonDao();
        allLessons = lessonDao.getAllLessons();
    }

    LiveData<List<Lesson>> getAllLessons() {
        return allLessons;
    }

    void modify(Lesson lesson, DbOperation operation) {
        new modifyAsyncTask(lessonDao, operation).execute(lesson);
    }
    private static class modifyAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDao asyncTaskDao;
        private DbOperation operation;

        modifyAsyncTask(LessonDao dao, DbOperation operation) {
            asyncTaskDao = dao;
            this.operation = operation;

        }

        @Override
        protected Void doInBackground(final Lesson... params) {
            switch (operation) {
                case INSERT:
                    asyncTaskDao.insert(params[0]);
                    break;
                case DELETE:
                    asyncTaskDao.delete(params[0]);
                    break;
                case UPDATE:
                    asyncTaskDao.update(params[0]);
                    break;
                default:
                    throw new AssertionError();
            }
            return null;
        }
    }
}
