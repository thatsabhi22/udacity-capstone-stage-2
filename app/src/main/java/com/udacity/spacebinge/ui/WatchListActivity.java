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
import com.udacity.spacebinge.adapters.WatchListAdapter;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity {

    WatchListAdapter watchListAdapter;
    RecyclerView watch_list_recycler_view;
    ImageView loading_indicator_watchlist_iv;
    Observer<List<VideoItem>> videoItemListObserver;
    WatchlistViewModel watchlistViewModel;
    List<VideoItem> videoCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        watch_list_recycler_view = findViewById(R.id.watch_list_recycler_view);
        loading_indicator_watchlist_iv = findViewById(R.id.loading_indicator_watchlist_iv);

        Glide
                .with(this)
                .asGif().load(R.drawable.globe_loading)
                .into(loading_indicator_watchlist_iv);
        videoCollection = new ArrayList<>();

        initWatchlistViewModel();

        watchListAdapter = new WatchListAdapter(this, videoCollection);
        watch_list_recycler_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        watch_list_recycler_view.setAdapter(watchListAdapter);

        RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                loading_indicator_watchlist_iv.setVisibility(View.GONE);
            }
        };
        watchListAdapter.registerAdapterDataObserver(observer);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent0 = new Intent(WatchListActivity.this, HomeActivity.class);
                        //intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(WatchListActivity.this, SearchActivity.class);
                        //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(WatchListActivity.this, DownloadActivity.class);
                        //intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(WatchListActivity.this, NewsActivity.class);
                        //intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void initWatchlistViewModel() {
        videoItemListObserver =
                new Observer<List<VideoItem>>() {
                    @Override
                    public void onChanged(List<VideoItem> videoItemList) {
                        videoCollection.clear();
                        videoCollection.addAll(videoItemList);

                        if (watchListAdapter == null) {
                            watchListAdapter = new
                                    WatchListAdapter(WatchListActivity.this, videoCollection);
                        } else {
                            watchListAdapter.notifyDataSetChanged();
                        }
                    }
                };

        watchlistViewModel = ViewModelProviders.of(this)
                .get(WatchlistViewModel.class);
        watchlistViewModel.getAllWatchListItems()
                .observe(WatchListActivity.this, videoItemListObserver);
    }
}
