package com.app.amyal.interfaces;

import android.content.Intent;

import com.app.amyal.helpers.TwitterUser;

public interface TwitterLoginListener {
    public void onTwitterActivityResult(int requestCode, int resultCode, Intent data);
    public void onSuccessfulTwitterLogin(TwitterUser user);
}
