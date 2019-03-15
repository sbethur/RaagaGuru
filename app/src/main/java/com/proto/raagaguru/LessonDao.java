package com.proto.raagaguru;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LessonDao {

    @Insert
    void insert(Lesson lesson);

    // TODO Handle duplicate entries

    @Query("DELETE FROM lesson_table")
    void deleteAll();

    @Query("SELECT * FROM lesson_table")
    LiveData<List<Lesson>> getAllLessons();
}
