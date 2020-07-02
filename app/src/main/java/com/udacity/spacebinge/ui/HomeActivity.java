package com.udacity.spacebinge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.HomePageAdapter;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.repositories.VideoItemRepository;
import com.udacity.spacebinge.tasks.SpaceWebService;
import com.udacity.spacebinge.viewmodels.HomePageViewModel;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();
    VideoItemRepository videoItemRepository;
    HomePageViewModel homePageViewModel;
    RecyclerView topicRV;
    HomePageAdapter homePageAdapter;
    Observer<LinkedHashMap<String, List<VideoItem>>> videoItemObserver;
    private SpaceWebService spaceWebService;
    private LinkedHashMap<String, List<VideoItem>> videoCollection;
    private List<String> topics;
    ImageView loading_indicator_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initializing all view elements in this method call
        initViewElements();

        videoCollection = new LinkedHashMap<String, List<VideoItem>>();
        topics = Arrays.asList(getResources().getStringArray(R.array.topics));
        initHomepageViewModel();

        homePageAdapter = new HomePageAdapter(this, videoCollection);
        topicRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        topicRV.setAdapter(homePageAdapter);

        // Register the AdapterdataObserver to the RecyclerView.Adapter
        // onChanged would be called when data set of adapter changes
        RecyclerView.AdapterDataObserver observer= new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                //Toast.makeText(HomeActivity.this,"recyclerview loaded",Toast.LENGTH_SHORT).show();
                loading_indicator_iv.setVisibility(View.GONE);
            }
        };
        homePageAdapter.registerAdapterDataObserver(observer);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(HomeActivity.this, WatchListActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(HomeActivity.this, DownloadActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(HomeActivity.this, NewsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void initViewElements() {
        topicRV = findViewById(R.id.topics_recycler_view);
        loading_indicator_iv = findViewById(R.id.loading_indicator_home_iv);
        Glide
                .with(this)
                .asGif().load(R.drawable.globe_loading)
                .into(loading_indicator_iv);
    }

    private void initHomepageViewModel() {
        videoItemObserver =
                new Observer<LinkedHashMap<String, List<VideoItem>>>() {
                    @Override
                    public void onChanged(LinkedHashMap<String, List<VideoItem>> stringListMap) {
                        videoCollection.clear();
                        videoCollection.putAll(stringListMap);

                        if (homePageAdapter == null) {
                            homePageAdapter = new
                                    HomePageAdapter(HomeActivity.this, videoCollection);
                        } else {
                            homePageAdapter.notifyDataSetChanged();
                        }
                    }
                };

        homePageViewModel = ViewModelProviders.of(this)
                .get(HomePageViewModel.class);
        homePageViewModel.getLatest(topics, "video")
                .observe(HomeActivity.this, videoItemObserver);
    }
}
