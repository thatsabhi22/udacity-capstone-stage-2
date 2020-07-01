package com.udacity.spacebinge.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.VideoItem;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.udacity.spacebinge.utils.ConstantUtil.VIDEO_ITEM_OBJECT;

public class PlayerActivity extends AppCompatActivity {

    public static final String VIDEO_URI = "video_uri";
    public static final String VIDEO_POSITION = "video_position";
    public static final String VIDEO_PLAY_WHEN_READY = "video_play_when_ready";
    public static final String VIDEO_PLAY_WINDOW_INDEX = "video_play_window_index";
    public static final String VIDEO_SINGLE = "video_single";
    TextView videoTitleTV, videoDateTV, videoDescriptionTV;
    boolean mShouldPlayWhenReady = true;
    long mPlayerPosition;
    int mWindowIndex;
    SimpleExoPlayer mSimpleExoPlayer;
    Uri mVideoUri;
    PlayerView mPlayerView;
    VideoItem current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Get the VideoItem object passed from HomePage from Intent
        current = intent.getParcelableExtra(VIDEO_ITEM_OBJECT);

        // Initializing views
        videoTitleTV = findViewById(R.id.video_title_tv);
        videoDateTV = findViewById(R.id.video_date_tv);
        videoDescriptionTV = findViewById(R.id.video_description_tv);
        mPlayerView = findViewById(R.id.player_view);

        // Popluating data to views
        videoTitleTV.setText(current.getTitle());

        // Formatting date
        String date = current.getDate_created();
        date = formatDate(date);
        videoDateTV.setText(date);
        videoDescriptionTV.setText(current.getDescription());

        // Check if there is any state saved
        if (savedInstanceState != null) {
            current = savedInstanceState.getParcelable(VIDEO_SINGLE);
            mShouldPlayWhenReady = savedInstanceState.getBoolean(VIDEO_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(VIDEO_POSITION);
            mWindowIndex = savedInstanceState.getInt(VIDEO_PLAY_WINDOW_INDEX);
            mVideoUri = Uri.parse(savedInstanceState.getString(VIDEO_URI));
        }
        else if (!TextUtils.isEmpty(current.getVideo_url())) {
            //mPlayerView.setUseController(false);
            //mVideoUri = Uri.parse("https://images-assets.nasa.gov/video/GSFC_20190130_NICER_m12854_BlkHole/GSFC_20190130_NICER_m12854_BlkHole~mobile.mp4");
            //mVideoUri = Uri.parse("http://images-assets.nasa.gov/video/42_RethinkingAnAlienWorld/42_RethinkingAnAlienWorld~mobile.mp4");

            mVideoUri = Uri.parse(current.getVideo_url());
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent0 = new Intent(PlayerActivity.this, HomeActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(PlayerActivity.this, WatchListActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(PlayerActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(PlayerActivity.this, DownloadActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(PlayerActivity.this, NewsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    @NotNull
    private String formatDate(String date) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date1 = sdf.parse(date);
            sdf = new SimpleDateFormat("MMMM dd, yyyy");
            date = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // Get the current state of the player
    private void updateStartPosition() {
        if (mSimpleExoPlayer != null) {
            mShouldPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mWindowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayerPosition = mSimpleExoPlayer.getCurrentPosition();
        }
    }

    // Building the MediaSource
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeVideoPlayer(mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mSimpleExoPlayer == null) {
            initializeVideoPlayer(mVideoUri);
        }
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
            mSimpleExoPlayer.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    // Initializing a video player
    public void initializeVideoPlayer(Uri videoUri) {

        if (mSimpleExoPlayer == null) {

            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);

            mSimpleExoPlayer.addListener(new SimpleExoPlayer.EventListener(){
                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    Toast.makeText(PlayerActivity.this,"Error playing file",Toast.LENGTH_SHORT).show();
                }
            });

            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
            MediaSource mediaSource = buildMediaSource(videoUri);

            if (mPlayerPosition != C.TIME_UNSET) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
            }
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(mediaSource, false, false);
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
        }
    }

    // Release player
    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        if (current != null) {
            outState.putString(VIDEO_URI, current.getVideo_url());
            outState.putParcelable(VIDEO_SINGLE, current);
            outState.putLong(VIDEO_POSITION, mPlayerPosition);
            outState.putBoolean(VIDEO_PLAY_WHEN_READY, mShouldPlayWhenReady);
        }
    }
}
