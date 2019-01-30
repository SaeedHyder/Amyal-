package com.app.amyal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.app.amyal.R;
import com.app.amyal.entities.FacebookLoginEnt;
import com.app.amyal.entities.ResponseWrapper;
import com.app.amyal.entities.UserEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.FacebookLoginHelper;
import com.app.amyal.helpers.GoogleHelper;
import com.app.amyal.helpers.InternetHelper;
import com.app.amyal.helpers.TwitterLoginHelper;
import com.app.amyal.helpers.TwitterUser;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.FacebookLoginListener;
import com.app.amyal.interfaces.TwitterLoginListener;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment implements GoogleHelper.GoogleHelperInterfce, FacebookLoginListener, TwitterLoginListener {

    private static final int RC_SIGN_IN = 007;

    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.btn_forgot_password)
    AnyTextView btnForgotPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.ivFb)
    ImageView ivFb;
    @BindView(R.id.ivTw)
    ImageView ivTw;
    @BindView(R.id.ivGoogle)
    ImageView ivGoogle;
    @BindView(R.id.txtRegister)
    AnyTextView txtRegister;
    @BindView(R.id.svmain)
    ScrollView svmain;
    Unbinder unbinder;

    boolean isCameFromBuyFlow = false;
    @BindView(R.id.twitterLogin)
    TwitterLoginButton twitterLogin;

    private FacebookLoginHelper facebookLoginHelper;
    private CallbackManager callbackManager;
    private GoogleHelper googleHelper;
    private String mSocialMediaPlatform = "";
    private String mFacebookSocialMediaID = "";
    private String mGoogleSocialMediaID = "";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        Utils.HideKeyBoard(getDockActivity());
        setupGoogleSignup();
        setupFacebookLogin();
        setTwitterLogin();

    }

    private void setListeners() {

    }

    public void setIsCameFromBuyFlow(boolean isCameFromBuyFlow) {
        this.isCameFromBuyFlow = isCameFromBuyFlow;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.login));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainActivity().setTwitterLoginListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        getMainActivity().setBackground(R.drawable.sp_light);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private boolean validated() {
        if (edtEmail.getText().toString().trim().equals("") ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.email_error));
            return false;
        } else if (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else {
            return true;
        }

    }

    @OnClick({R.id.btn_forgot_password, R.id.btnLogin, R.id.ivFb, R.id.ivTw, R.id.ivGoogle, R.id.txtRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgot_password:
                edtEmail.setText("");
                edtPassword.setText("");
                getDockActivity().replaceDockableFragment(ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment");
                break;
            case R.id.btnLogin:
                if (validated()) {
                    signIn();
                }
                break;
            case R.id.ivFb:
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, facebookLoginHelper.getPermissionNeeds());
                break;
            case R.id.ivTw:
                twitterLogin.performClick();
                break;
            case R.id.ivGoogle:
                //Login();
                googleHelper.intentGoogleSign();
                break;
            case R.id.txtRegister:
                SignupFragment signupFragment = new SignupFragment();
                signupFragment.setIsCameFromBuyFlow(isCameFromBuyFlow);
                getDockActivity().replaceDockableFragment(signupFragment, SignupFragment.class.getSimpleName());
                break;
        }
    }

    private void signIn() {
        loadingStarted();

        String token = FirebaseInstanceId.getInstance().getToken();

        Call<ResponseWrapper<UserEnt>> call = webService.signIn(
                edtEmail.getText().toString(),
                edtPassword.getText().toString(),
                AppConstants.Device_Type,
                token
        );

        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.CODE_SUCCESS)) {

                    UserEnt userEnt = (UserEnt) response.body().getResult();
                    prefHelper.putUser(userEnt);
                    prefHelper.setLoginStatus(true);
                    Login();

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    private void socialMediaSignIn(final String SocialMediaId, final String SocialMediaPlatform, final String Name, final String Email, final String Image) {
        loadingStarted();

        String token = FirebaseInstanceId.getInstance().getToken();

        Call<ResponseWrapper<UserEnt>> call = webService.signInWithSocialMedia(
                SocialMediaId,
                AppConstants.Device_Type,
                token);

        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.CODE_SUCCESS)) {
                    UserEnt userEnt = (UserEnt) response.body().getResult();
                    prefHelper.putUser(userEnt);
                    prefHelper.setLoginStatus(true);
                    Login();
                } else {
                    SignupFragment signupFragment = new SignupFragment();
                    signupFragment.setIsCameFromBuyFlow(isCameFromBuyFlow);
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.Name, Name);
                    orderBundle.putString(AppConstants.Email, Email);
                    orderBundle.putString(AppConstants.ProfileImage, Image);
                    orderBundle.putString(AppConstants.SocialMediaPlatform, SocialMediaPlatform);
                    orderBundle.putString(AppConstants.SocialMediaId, SocialMediaId);
                    signupFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(signupFragment, SignupFragment.class.getSimpleName());
                    //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    public void Login() {

        if (isCameFromBuyFlow) {
            prefHelper.setLoginStatus(true);
            getDockActivity().popFragment();
        } else {
            prefHelper.setLoginStatus(true);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
        }


    }

    private void clearViews() {
        edtPassword.setText("");
        edtEmail.setText("");
    }

    private void setupGoogleSignup() {
        googleHelper = GoogleHelper.getInstance();
        googleHelper.setGoogleHelperInterface(this);
        googleHelper.configGoogleApiClient(this);
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        // btnfbLogin.setFragment(this);
        facebookLoginHelper = new FacebookLoginHelper(getDockActivity(), this, this);
        LoginManager.getInstance().registerCallback(callbackManager, facebookLoginHelper);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            googleHelper.handleGoogleResult(requestCode, resultCode, data);
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleHelper.ConnectGoogleAPi();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleHelper.DisconnectGoogleApi();
    }


    @Override
    public void onGoogleSignInResult(GoogleSignInAccount result) {
        clearViews();
        String Name = result.getDisplayName();
        String Email = result.getEmail();

        String Image = "";
        if (result.getPhotoUrl() != null)
            Image = result.getPhotoUrl().toString();

        mSocialMediaPlatform = WebServiceConstants.PLATFORM_GOOGLE;
        mGoogleSocialMediaID = result.getId();

        if (mGoogleSocialMediaID != null && mGoogleSocialMediaID.length() > 0) {
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                socialMediaSignIn(mGoogleSocialMediaID, mSocialMediaPlatform, Name, Email, Image);
        }

    }

    @Override
    public void onSuccessfulFacebookLogin(FacebookLoginEnt LoginEnt) {
        clearViews();
        String Name = LoginEnt.getFacebookFullName();
        String Email = LoginEnt.getFacebookEmail() == null ? "" : LoginEnt.getFacebookEmail();
        String Image = LoginEnt.getFacebookUProfilePicture();
        mSocialMediaPlatform = WebServiceConstants.PLATFORM_FACEBOOK;
        mFacebookSocialMediaID = LoginEnt.getFacebookUID();

        if (mFacebookSocialMediaID != null && mFacebookSocialMediaID.length() > 0) {
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                socialMediaSignIn(mFacebookSocialMediaID, mSocialMediaPlatform, Name, Email, Image);
                LoginManager.getInstance().logOut();
            }
        }
    }

   /* TwitterUser twitterUser;
    private void setTwitterLogin1() {
        twitterLogin.setCallback(new TwitterLoginHelper() {
            @Override
            public void onSuccess(final TwitterUser user) {
                Call<com.twitter.sdk.android.core.models.User> userCall = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, false,true);
                userCall.enqueue(new com.twitter.sdk.android.core.Callback<com.twitter.sdk.android.core.models.User>() {
                    @Override
                    public void success(final Result<com.twitter.sdk.android.core.models.User> result) {
                        TwitterAuthClient authClient = new TwitterAuthClient();
                        authClient.requestEmail(TwitterCore.getInstance().getSessionManager().getActiveSession(), new com.twitter.sdk.android.core.Callback<String>() {
                            @Override
                            public void success(Result<String> email) {
                                twitterUser = new TwitterUser(result.data.profileImageUrl.replace("_normal", "_bigger"), user.getUserId(), user.getUserName(), result.data.email, user.getToken(), user.getToken());
                                twitterUser.setUserEmail(email.data.toString());
                                startFragment(twitterUser);


                            }

                            @Override
                            public void failure(TwitterException exception) {
                                try {
                                    twitterUser = user;
                                    twitterUser.setUserEmail("");

                                    startFragment(twitterUser);

                                } catch (ExecutionError e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        try {
                            twitterUser = user;
                            twitterUser.setUserEmail("");

                            startFragment(twitterUser);

                        } catch (ExecutionError e) {
                            e.printStackTrace();
                        }
                    }
                });


            }

            @Override
            public void onFailure(TwitterException exception) {

            }
        });
    }*/

    private void setTwitterLogin() {
        twitterLogin.setCallback(new TwitterLoginHelper() {
            @Override
            public void onSuccess(final TwitterUser user) {

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(TwitterCore.getInstance().getSessionManager().getActiveSession(), new com.twitter.sdk.android.core.Callback<String>() {
                    @Override
                    public void success(Result<String> result) {

                        TwitterUser twitterUser = user;
                        twitterUser.setUserEmail(twitterUser.getUserEmail() + "");

                        startFragment(twitterUser);

                    }

                    @Override
                    public void failure(TwitterException exception) {

                        try {
                            final TwitterUser twitterUser = user;
                            twitterUser.setUserEmail("");

                            startFragment(twitterUser);

                        } catch (ExecutionError e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(TwitterException exception) {

            }
        });
    }


    private void startFragment(TwitterUser twitterUser) {
        mFacebookSocialMediaID = twitterUser.getUserId();
        mSocialMediaPlatform = WebServiceConstants.PLATFORM_TWITTER;
        socialMediaSignIn(twitterUser.getToken(), mSocialMediaPlatform, twitterUser.getUserName(), twitterUser.getUserEmail(), twitterUser.getUserPic());
    }

    @Override
    public void onTwitterActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            try {
                if (resultCode == 1) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Twitter App not found");
                }

                twitterLogin.onActivityResult(requestCode, resultCode, data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onSuccessfulTwitterLogin(TwitterUser user) {

    }
}
