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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.NewsAdapter;
import com.udacity.spacebinge.models.Article;
import com.udacity.spacebinge.utils.AppUtil;
import com.udacity.spacebinge.viewmodels.NewsViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsActivity extends AppCompatActivity {

    CardStackView cardStackView;
    CardStackLayoutManager cardStackLayoutManager;
    NewsAdapter newsAdapter;
    Observer<List<Article>> newsListObserver;
    NewsViewModel newsViewModel;
    String query, api_key;
    TextView watch_more_videos_tv, all_caught_up_tv, offline_mode_tv, go_to_downloads_tv;
    ImageView all_caught_up_iv, loading_indicator_iv, offline_mode_iv;
    List<Article> newsCollection;
    boolean isOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initViewElements();

        try {
            isOffline = new AppUtil.CheckOnlineStatus().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (isOffline) {
            offline_mode_iv.setVisibility(View.VISIBLE);
            offline_mode_tv.setVisibility(View.VISIBLE);
            go_to_downloads_tv.setVisibility(View.VISIBLE);

            go_to_downloads_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewsActivity.this, DownloadActivity.class);
                    startActivity(intent);
                }
            });
        } else {

            loading_indicator_iv.setVisibility(View.VISIBLE);
            Glide
                    .with(this)
                    .asGif().load(R.drawable.globe_loading)
                    .into(loading_indicator_iv);

            watch_more_videos_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewsActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });

            api_key = getString(R.string.news_api_key);
            query = getString(R.string.default_news_query);
            newsCollection = new ArrayList<>();
            initNewsViewModel();

            cardStackLayoutManager = new CardStackLayoutManager(this, new CardStackListener() {
                @Override
                public void onCardDragging(Direction direction, float ratio) {

                }

                @Override
                public void onCardSwiped(Direction direction) {
                    if (cardStackLayoutManager.getTopPosition() == newsAdapter.getItemCount()) {
                        watch_more_videos_tv.setVisibility(View.VISIBLE);
                        all_caught_up_tv.setVisibility(View.VISIBLE);
                        all_caught_up_iv.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCardRewound() {

                }

                @Override
                public void onCardCanceled() {

                }

                @Override
                public void onCardAppeared(View view, int position) {

                }

                @Override
                public void onCardDisappeared(View view, int position) {

                }
            });
            cardStackLayoutManager.setVisibleCount(3);
            cardStackLayoutManager.setTranslationInterval(8.0f);
            cardStackLayoutManager.setScaleInterval(0.95f);
            cardStackLayoutManager.setSwipeThreshold(0.3f);
            cardStackLayoutManager.setMaxDegree(20.0f);
            cardStackLayoutManager.setDirections(Direction.HORIZONTAL);
            cardStackLayoutManager.setCanScrollHorizontal(true);
            cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Manual);
            newsAdapter = new NewsAdapter(this, newsCollection);
            cardStackView.setLayoutManager(cardStackLayoutManager);
            cardStackView.setAdapter(newsAdapter);
            cardStackView.setItemAnimator(new DefaultItemAnimator());

            // Register the AdapterdataObserver to the RecyclerView.Adapter
            // onChanged would be called when data set of adapter changes
            CardStackView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    //Toast.makeText(HomeActivity.this,"recyclerview loaded",Toast.LENGTH_SHORT).show();
                    loading_indicator_iv.setVisibility(View.GONE);
                }
            };
            newsAdapter.registerAdapterDataObserver(observer);
        }
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

    private void initViewElements() {
        all_caught_up_iv = findViewById(R.id.all_caught_up_iv);
        watch_more_videos_tv = findViewById(R.id.watch_more_videos_tv);
        all_caught_up_tv = findViewById(R.id.all_caught_up_tv);
        cardStackView = findViewById(R.id.card_stack_view);
        loading_indicator_iv = findViewById(R.id.loading_indicator_news_iv);
        offline_mode_iv = findViewById(R.id.offline_mode_iv);
        offline_mode_tv = findViewById(R.id.offline_mode_tv);
        go_to_downloads_tv = findViewById(R.id.go_to_downloads_tv);
    }

    private void initNewsViewModel() {
        newsListObserver =
                new Observer<List<Article>>() {
                    @Override
                    public void onChanged(List<Article> newsList) {
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
        newsViewModel.getLatestNews(query, api_key)
                .observe(NewsActivity.this, newsListObserver);
    }
}
