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

    private int tagData;


    public Lesson(
            @NonNull String audioFilePath,
            @NonNull String audioFileName,
            int tagData) {
        this.audioFilePath = audioFilePath;
        this.audioFileName = audioFileName;
        this.tagData = tagData;
    }

    @NonNull
    String getAudioFilePath() {
        return audioFilePath;
    }

    @NonNull
    String getAudioFileName() {
        return audioFileName;
    }

    int getTagData() {
        return tagData;
    }

    void setTagData(int tagData) {
        this.tagData = tagData;
    }
}
