package com.proto.raagaguru;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "lesson_table")
public class Lesson {
    @PrimaryKey
    @NonNull
    private String audioFilePath;

    @NonNull
    private String audioFileName;


    public Lesson(@NonNull String audioFilePath, @NonNull String audioFileName) {
        this.audioFilePath = audioFilePath;
        this.audioFileName = audioFileName;
    }

    @NonNull
    public String getAudioFilePath() {
        return audioFilePath;
    }

    @NonNull
    public String getAudioFileName() {
        return audioFileName;
    }
}
