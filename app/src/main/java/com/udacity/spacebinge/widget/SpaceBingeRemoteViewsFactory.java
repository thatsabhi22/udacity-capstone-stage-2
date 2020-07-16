package com.udacity.spacebinge.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.models.Article;
import com.udacity.spacebinge.repositories.NewsRepository;

import java.util.ArrayList;
import java.util.List;

public class SpaceBingeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final int mCount = 10;
    NewsRepository newsRepository;
    private List<Article> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public SpaceBingeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        newsRepository = NewsRepository.getInstance(mContext);
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        mWidgetItems = newsRepository
                .getNews(mContext.getString(R.string.default_news_query),
                        mContext.getString(R.string.news_api_key));

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the SpaceBingeWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        mWidgetItems.clear();
    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        Bundle extras = new Bundle();
        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if (mWidgetItems.size() > 0) {
            rv.setTextViewText(R.id.widget_item, mWidgetItems.get(position).getTitle());
            extras.putInt(SpaceBingeWidgetProvider.EXTRA_ITEM, position);
            extras.putString("newsArticleUrl", mWidgetItems.get(position).getUrl());
        }
        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in SpaceBingeWidgetProvider.
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);


        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}
