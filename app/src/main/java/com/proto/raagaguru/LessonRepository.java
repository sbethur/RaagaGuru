package com.proto.raagaguru;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

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

    void insert(Lesson lesson) {
        new insertAsyncTask(lessonDao).execute(lesson);
    }
    private static class insertAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDao mAsyncTaskDao;

        insertAsyncTask(LessonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Lesson... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    void delete(Lesson lesson) {
        new deleteAsyncTask(lessonDao).execute(lesson);
    }
    private static class deleteAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDao mAsyncTaskDao;

        deleteAsyncTask(LessonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Lesson... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    void update(Lesson lesson) {
        new updateAsyncTask(lessonDao).execute(lesson);
    }
    private static class updateAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDao mAsyncTaskDao;

        updateAsyncTask(LessonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Lesson... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
