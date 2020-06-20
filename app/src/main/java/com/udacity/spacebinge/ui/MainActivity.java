package com.udacity.spacebinge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.HomePageAdapter;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;
import com.udacity.spacebinge.tasks.SpaceWebService;
import com.udacity.spacebinge.viewmodels.HomePageViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    VideoItemRepository videoItemRepository;
    HomePageViewModel homePageViewModel;
    RecyclerView topicRV;
    HomePageAdapter homePageAdapter;
    Observer<List<Map<String, List<VideoItem>>>> videoItemObserver;
    private SpaceWebService spaceWebService;
    private List<Map<String, List<VideoItem>>> videoCollection;
    private List<String> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing all view elements in this method call
        initViewElements();

        videoCollection = new ArrayList<Map<String, List<VideoItem>>>();
        topics = Arrays.asList(getResources().getStringArray(R.array.topics));
        initHomepageViewModel();

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        Intent intent0 = new Intent(MainActivity.this, SubscriptionLoginActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(MainActivity.this, WatchListActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(MainActivity.this, DownloadActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void initViewElements() {
        topicRV = findViewById(R.id.topics_recycler_view);
    }

    private void initHomepageViewModel() {
        videoItemObserver =
                videos -> {
                    videoCollection.clear();
                    videoCollection.addAll(videos);

                    if (homePageAdapter == null) {
                        homePageAdapter = new
                                HomePageAdapter(MainActivity.this, videoCollection);
                    } else {
                        homePageAdapter.notifyDataSetChanged();
                    }
                };
        homePageViewModel = ViewModelProviders.of(this)
                .get(HomePageViewModel.class);
        homePageViewModel.getLatest(topics, "video")
                .observe(MainActivity.this, videoItemObserver);
    }
}
