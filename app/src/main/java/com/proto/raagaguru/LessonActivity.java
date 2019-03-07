package com.proto.raagaguru;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LessonActivity extends AppCompatActivity {

    public static final int NEW_LESSON_ACTIVITY_REQUEST_CODE = 1;
    private LessonViewModel lessonViewModel;
    private LessonListAdapter lessonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);
        lessonViewModel.getAllLessons().observe(this, lessons -> {
            // Update the cached copy of the lessons in the adapter.
            lessonListAdapter.setLessons((ArrayList<Lesson>) lessons);
        });

        initRecyclerView();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Lesson lesson = new Lesson("Song1");
//        lessonViewModel.insert(lesson);
//        Lesson lesson2 = new Lesson("Song1");
//        lessonViewModel.insert(lesson2);
//    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        lessonListAdapter = new LessonListAdapter(this);
        recyclerView.setAdapter(lessonListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
    }

    public void createNewLessonClick2(View view) {
        Random r = new Random();
        Lesson  lesson = new Lesson("Lesson " + r.nextInt(10000 - 1) + 1);
        lessonViewModel.insert(lesson);
    }

/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_LESSON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Lesson lesson = new Lesson(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            lessonViewModel.insert(lesson);
        } else {
            Toast.makeText(
                getApplicationContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show();
        }
    }*/
}
