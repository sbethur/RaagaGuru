package com.proto.raagaguru;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LessonDao {

    @Insert
    void insert(Lesson lesson);

    // TODO Handle duplicate entries

    @Query("DELETE FROM lesson_table")
    void deleteAll();

    @Delete
    void delete(Lesson... lessons);

    @Update
    void update(Lesson... lessons);

    @Query("SELECT * FROM lesson_table")
    LiveData<List<Lesson>> getAllLessons();
}
