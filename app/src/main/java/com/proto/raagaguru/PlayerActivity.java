package com.proto.raagaguru;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

    public static final String EXTRA_REPLY = "com.proto.raagaguru.REPLY";
    private PlayerView playerView;
    private PlayerControlView controlView;
    private SimpleExoPlayer player;
    private String audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView = findViewById(R.id.player_view);
        controlView = findViewById(R.id.control_view);

        Intent intent = getIntent();
        audioFile = intent.getStringExtra(MainActivity.AUDIO_FILE);

        final Button button = findViewById(R.id.saveLessonButton);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, "Lesson Name");
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setPlayer(player);
        controlView.setPlayer(player);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "ExoPlayer"));

        //String audioFile = "asset:///toms_diner.mp3";
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(audioFile));
        player.prepare(mediaSource);

        controlView.setFastForwardIncrementMs(2000);
        controlView.setRewindIncrementMs(2000);

        //player.setPlayWhenReady(true);
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
                "save this lesson",
                Toast.LENGTH_LONG).show();
    }

}
