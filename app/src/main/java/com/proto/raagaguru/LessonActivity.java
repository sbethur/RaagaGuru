package com.proto.raagaguru;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final int FILE_METADATA_CODE = 43;
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

        // Get the Intent that started this activity and extract the string
        Intent receivedIntent = getIntent();
        if (receivedIntent.hasExtra(Constants.AUDIO_FILE)) {
            String audioFile = receivedIntent.getStringExtra(Constants.AUDIO_FILE);
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra(Constants.AUDIO_FILE, audioFile);
            startActivityForResult(intent, FILE_METADATA_CODE);
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        lessonListAdapter = new LessonListAdapter(this);
        recyclerView.setAdapter(lessonListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_METADATA_CODE && resultCode == RESULT_OK) {
            Lesson lesson = new Lesson(
                data.getStringExtra(Constants.AUDIO_FILE),
                data.getStringExtra(Constants.LESSON_NAME));
            lessonViewModel.insert(lesson);
        } else {
            Toast.makeText(
                getApplicationContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show();
        }
    }

    public void showLessonMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Toast.makeText(
                        getApplicationContext(),
                        "Implement edit",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_delete:
                Toast.makeText(
                        getApplicationContext(),
                        "Implement delete",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }
}
