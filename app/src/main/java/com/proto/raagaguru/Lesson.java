package com.proto.raagaguru;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "lesson_table")
public class Lesson {
    @PrimaryKey
    @NonNull
    private String audioFilePath;
    //private List<Integer> tags;

    public Lesson(@NonNull String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    /*
    void addTag (Integer newTag) {
        tags.add(newTag);
    }

    List<Integer> getTags() {
        return tags;
    }
    */
}
