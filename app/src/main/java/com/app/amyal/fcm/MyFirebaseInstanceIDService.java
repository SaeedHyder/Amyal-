package com.app.amyal.fcm;

import android.util.Log;

import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.helpers.TokenUpdater;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by syed.shah on 6/14/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    protected BasePreferenceHelper preferenceHelper;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        // sending gcm token to server
        if(preferenceHelper.getUser()!= null)
            sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);
        TokenUpdater.getInstance().UpdateToken(getApplicationContext(),
                String.valueOf(preferenceHelper.getUser().getUser().getId()), "android", token);
    }
}