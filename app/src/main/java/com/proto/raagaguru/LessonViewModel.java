package com.proto.raagaguru;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class LessonViewModel extends AndroidViewModel {

    private LessonRepository repository;
    private LiveData<List<Lesson>> allLessons;

    public LessonViewModel(@NonNull Application application) {
        super(application);
        repository = new LessonRepository(application);
        allLessons = repository.getAllLessons();
    }

    LiveData<List<Lesson>> getAllLessons() { return allLessons; }
    void insert(Lesson lesson) { repository.insert(lesson); }
    void delete(Lesson lesson) { repository.delete(lesson); }
    void update(Lesson lesson) { repository.update(lesson); }
}
