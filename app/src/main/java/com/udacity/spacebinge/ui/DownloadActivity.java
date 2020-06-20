package com.udacity.spacebinge.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent0 = new Intent(DownloadActivity.this, SubscriptionLoginActivity.class);
                        //intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(DownloadActivity.this, WatchListActivity.class);
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(DownloadActivity.this, SearchActivity.class);
                        //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(DownloadActivity.this, NewsActivity.class);
                        //intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
}
