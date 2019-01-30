package com.app.amyal.helpers;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;



public abstract class TwitterLoginHelper extends com.twitter.sdk.android.core.Callback<TwitterSession> {

    @Override
    public void success(Result<TwitterSession> result) {

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();

        String token = authToken.token;
        String secret = authToken.secret;

        String userId = String.valueOf(result.data.getUserId());
        String userName = result.data.getUserName();


        TwitterUser user = new TwitterUser();

        user.setUserId(userId);
        user.setUserName(userName);
        user.setToken(token);
        user.setSecret(secret);
        user.setUserPic("");
        user.setUserEmail("");

        onSuccess(user);

    }

    @Override
    public void failure(TwitterException exception) {
        onFailure(exception);
    }

    public abstract void onSuccess(TwitterUser user);
    public abstract void onFailure(TwitterException exception);
}
