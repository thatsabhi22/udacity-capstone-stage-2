package com.udacity.spacebinge.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.DownloadedAdapter;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.viewmodels.DownloadlistViewModel;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    DownloadedAdapter downloadedAdapter;
    RecyclerView download_list_recycler_view;
    ImageView loading_indicator_downloadlist_iv, no_data_download_list_iv;
    TextView no_data_download_list_tv;
    Observer<List<VideoItem>> videoItemListObserver;
    DownloadlistViewModel downloadlistViewModel;
    List<VideoItem> videoCollection;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initializing all view elements in this method call
        initViewElements();

        Glide
                .with(this)
                .asGif().load(R.drawable.globe_loading)
                .into(loading_indicator_downloadlist_iv);
        videoCollection = new ArrayList<>();

        initWatchlistViewModel();

        downloadedAdapter = new DownloadedAdapter(this, videoCollection);
        download_list_recycler_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        download_list_recycler_view.setAdapter(downloadedAdapter);

        RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                loading_indicator_downloadlist_iv.setVisibility(View.GONE);
            }
        };
        downloadedAdapter.registerAdapterDataObserver(observer);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent0 = new Intent(DownloadActivity.this, HomeActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(DownloadActivity.this, WatchListActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(DownloadActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(DownloadActivity.this, NewsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void initViewElements() {
        download_list_recycler_view = findViewById(R.id.download_list_rv);
        loading_indicator_downloadlist_iv = findViewById(R.id.loading_indicator_downloadlist_iv);
        no_data_download_list_iv = findViewById(R.id.no_data_download_list_iv);
        no_data_download_list_tv = findViewById(R.id.no_data_download_list_tv);
    }

    private void initWatchlistViewModel() {
        videoItemListObserver =
                new Observer<List<VideoItem>>() {
                    @Override
                    public void onChanged(List<VideoItem> videoItemList) {
                        if (videoItemList.size() == 0) {
                            no_data_download_list_iv.setVisibility(View.VISIBLE);
                            no_data_download_list_tv.setVisibility(View.VISIBLE);
                        }
                        videoCollection.clear();
                        videoCollection.addAll(videoItemList);

                        if (downloadedAdapter == null) {
                            downloadedAdapter = new
                                    DownloadedAdapter(DownloadActivity.this, videoCollection);
                        } else {
                            downloadedAdapter.notifyDataSetChanged();
                        }
                    }
                };

        downloadlistViewModel = ViewModelProviders.of(this)
                .get(DownloadlistViewModel.class);
        downloadlistViewModel.getAllDownloadedListItems()
                .observe(DownloadActivity.this, videoItemListObserver);
    }
}
