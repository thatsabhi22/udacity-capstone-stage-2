package com.udacity.spacebinge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.adapters.SearchResultAdapter;
import com.udacity.spacebinge.models.VideoItem;
import com.udacity.spacebinge.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView video_search_view;
    ListView suggestive_list_view;
    ArrayAdapter list_adapter;
    SearchResultAdapter searchResultAdapter;
    List<String> suggestive_topics_list;
    RecyclerView search_result_recycler_view;
    Observer<List<VideoItem>> videoItemListObserver;
    SearchViewModel searchViewModel;
    List<VideoItem> videoCollection;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        video_search_view = findViewById(R.id.video_search_view);
//        suggestive_list_view = findViewById(R.id.search_list_view);
        search_result_recycler_view = findViewById(R.id.search_result_recycler_view);

//        suggestive_topics_list = Arrays.asList(getResources().getStringArray(R.array.topics));
        videoCollection = new ArrayList<>();

//        list_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestive_topics_list);
//        suggestive_list_view.setAdapter(list_adapter);

        query = "";
        initHomepageViewModel();

        searchResultAdapter = new SearchResultAdapter(this, videoCollection);
        search_result_recycler_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        search_result_recycler_view.setAdapter(searchResultAdapter);

        video_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
//                suggestive_list_view.setVisibility(View.GONE);
                search_result_recycler_view.setVisibility(View.VISIBLE);
                searchViewModel.getSearchResult(query, "video")
                        .observe(SearchActivity.this, videoItemListObserver);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                list_adapter.getFilter().filter(newText);
//
//                if (TextUtils.isEmpty(newText)) {
//                    suggestive_list_view.setVisibility(View.VISIBLE);
//                }
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent0 = new Intent(SearchActivity.this, HomeActivity.class);
                        //intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent0);
                        break;

                    case R.id.watchlist:
                        Intent intent1 = new Intent(SearchActivity.this, WatchListActivity.class);
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        break;

                    case R.id.search:
                        break;

                    case R.id.downloaded:
                        Intent intent3 = new Intent(SearchActivity.this, DownloadActivity.class);
                        //intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        break;

                    case R.id.news:
                        Intent intent4 = new Intent(SearchActivity.this, NewsActivity.class);
                        //intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void initHomepageViewModel() {
        videoItemListObserver =
                new Observer<List<VideoItem>>() {
                    @Override
                    public void onChanged(List<VideoItem> videoItemList) {
                        videoCollection.clear();
                        videoCollection.addAll(videoItemList);

                        if (searchResultAdapter == null) {
                            searchResultAdapter = new
                                    SearchResultAdapter(SearchActivity.this, videoCollection);
                        } else {
                            searchResultAdapter.notifyDataSetChanged();
                        }
                    }
                };

        searchViewModel = ViewModelProviders.of(this)
                .get(SearchViewModel.class);
        searchViewModel.getSearchResult(query, "video")
                .observe(SearchActivity.this, videoItemListObserver);
    }
}
