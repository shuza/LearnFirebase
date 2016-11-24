package com.learn.learfirebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Boka on 19-Nov-16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String REG_TOCKEN = "REG_TOCKEN";

    @Override
    public void onTokenRefresh() {
        String recentTocken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOCKEN, recentTocken);
    }
}
