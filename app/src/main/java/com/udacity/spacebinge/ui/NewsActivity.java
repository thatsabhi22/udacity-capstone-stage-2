package com.udacity.spacebinge.ui;

import android.content.Intent;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.NewsAdapter;
import com.udacity.spacebinge.adapters.WatchListAdapter;
import com.udacity.spacebinge.models.News;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.viewmodels.NewsViewModel;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    NewsAdapter newsAdapter;
    Observer<List<News>> newsListObserver;
    NewsViewModel newsViewModel;
    String query, api_key;
    TextView watch_more_videos_tv, all_caught_up_tv;
    ImageView all_caught_up_iv;
    List<News> newsCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        all_caught_up_iv = findViewById(R.id.all_caught_up_iv);
        watch_more_videos_tv = findViewById(R.id.watch_more_videos_tv);
        all_caught_up_tv = findViewById(R.id.all_caught_up_tv);

        watch_more_videos_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        api_key = getString(R.string.news_api_key);
        query = getString(R.string.default_news_query);

        initNewsViewModel();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent0 = new Intent(NewsActivity.this, HomeActivity.class);
                        //intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(NewsActivity.this, WatchListActivity.class);
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        Intent intent2 = new Intent(NewsActivity.this, SearchActivity.class);
                        //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(NewsActivity.this, DownloadActivity.class);
                        //intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        break;
                }
                return false;
            }
        });
    }

    private void initNewsViewModel() {
        newsListObserver =
                new Observer<List<News>>() {
                    @Override
                    public void onChanged(List<News> newsList) {
                        if (newsList.size() == 0) {
                            all_caught_up_iv.setVisibility(View.VISIBLE);
                            watch_more_videos_tv.setVisibility(View.VISIBLE);
                            all_caught_up_tv.setVisibility(View.VISIBLE);
                        }
                        newsCollection.clear();
                        newsCollection.addAll(newsList);

                        if (newsAdapter == null) {
                            newsAdapter = new
                                    NewsAdapter(NewsActivity.this, newsCollection);
                        } else {
                            newsAdapter.notifyDataSetChanged();
                        }
                    }
                };

        newsViewModel = ViewModelProviders.of(this)
                .get(NewsViewModel.class);
        newsViewModel.getAllWatchListItems(query, api_key)
                .observe(NewsActivity.this, newsListObserver);
    }
}
