package com.loveplusplus.update;

import android.content.Context;
import android.util.Log;
import android.view.View;

public class UpdateChecker {


    public static void checkForDialog(Context context,View view) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, true,view).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context,View view) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, false,view).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }


}
