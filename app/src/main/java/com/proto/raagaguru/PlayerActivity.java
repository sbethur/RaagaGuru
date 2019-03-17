package com.proto.raagaguru;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private PlayerControlView controlView;
    private SimpleExoPlayer player;
    private String audioFile;
    private String lessonName = "INVALID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView = findViewById(R.id.player_view);
        controlView = findViewById(R.id.control_view);

        Intent intent = getIntent();
        audioFile = intent.getStringExtra(Constants.AUDIO_FILE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setPlayer(player);
        controlView.setPlayer(player);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
    this, Util.getUserAgent(this, "ExoPlayer"));

        Uri currentTrack = Uri.parse(audioFile);
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(currentTrack);
        player.prepare(mediaSource);

        lessonName = getLessonName(currentTrack);

        controlView.setFastForwardIncrementMs(2000);
        controlView.setRewindIncrementMs(2000);

        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        controlView.setPlayer(null);
        player.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void saveLessonClick(View view) {
        Toast.makeText(
            getApplicationContext(),
            getString(R.string.lessonSaved),
            Toast.LENGTH_LONG).show();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(Constants.AUDIO_FILE, audioFile);
        replyIntent.putExtra(Constants.LESSON_NAME, lessonName);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private String getLessonName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    result = result.substring(0, result.lastIndexOf('.'));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
                result = result.substring(0, result.lastIndexOf('.'));
            }
        }
        return result;
    }

}
