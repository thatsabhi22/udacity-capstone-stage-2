package com.udacity.spacebinge.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class SpaceBingeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new SpaceBingeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
