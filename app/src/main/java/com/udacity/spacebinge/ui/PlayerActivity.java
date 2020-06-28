package com.udacity.spacebinge.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.VideoItem;

import static com.udacity.spacebinge.utils.ConstantUtil.VIDEO_ITEM_OBJECT;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        VideoItem item = intent.getParcelableExtra(VIDEO_ITEM_OBJECT);
        String url = item.getThumbnail_url();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent0 = new Intent(PlayerActivity.this, SubscriptionLoginActivity.class);
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

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
