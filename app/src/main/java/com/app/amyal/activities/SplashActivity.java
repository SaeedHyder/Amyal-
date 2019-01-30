package com.app.amyal.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.CurrencyModel;
import com.app.amyal.entities.ResponseWrapper;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.retrofit.WebServiceFactory;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 1000;
    final int TIME_INTERVAL_TO_CHECK = 500;// in millis
    final int MIN_TIME_INTERVAL_FOR_SPLASH = 2500; // in millis
    boolean workComplete = false;
    Timer checkWorkTimer;
    //    Runnable backgroundWork = new Runnable() {
//
//        @Override
//        public void run() {
//
//            // This area can be used in Splash to do tasks that do not delay
//            // Splash as well as do not extend its time if there processing time
//            // is less than splash
//            // Check Internet Connection and meet necessary conditions
//            // to start the app. E.g. Disk Checks.
//            // Check preferences and availability of certain data.
//            workComplete = true;
//        }
//
//    };
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        //launchTimerAndTask();
        initAnimation();
    }

    private void initAnimation() {

        YoYo.with(Techniques.ZoomIn)
                .duration(2000)
                .repeat(0)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        getCurrency();
                        imgLogo.setVisibility(View.VISIBLE);
                    }
                })
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {

                        if (workComplete)
                            initActivity();
                        workComplete = true;
                    }
                })
                .playOn(findViewById(R.id.imgLogo));

    }

    private void initActivity() {
        showMainActivity();
    }

    private void launchTimerAndTask() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showMainActivity();
            }
        }, MIN_TIME_INTERVAL_FOR_SPLASH);
        // Launch timer to test image changing and background threads work
       /* checkWorkTimer = new Timer();
        checkWorkTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (workComplete) {
                    initNextActivity();
                }
            }

        }, MIN_TIME_INTERVAL_FOR_SPLASH, TIME_INTERVAL_TO_CHECK);

        new Thread(backgroundWork).start();*/
    }

    private void initNextActivity() {
        checkWorkTimer.cancel();
        showMainActivity();

    }

    private void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void getCurrency() {
        Call<ResponseWrapper<CurrencyModel>> call = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.BASE_URL).GetCurrencyRate();

        call.enqueue(new Callback<ResponseWrapper<CurrencyModel>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<CurrencyModel>> call, Response<ResponseWrapper<CurrencyModel>> response) {
                if (response.body().getResponse().equals(WebServiceConstants.CODE_SUCCESS)) {

                    CurrencyModel currencyModel = (CurrencyModel) response.body().getResult();
                    BasePreferenceHelper prefHelper = new BasePreferenceHelper(SplashActivity.this);
                    prefHelper.putCurrency(currencyModel);
                    if (workComplete)
                        initActivity();
                    workComplete = true;

                } else {
                    UIHelper.showLongToastInCenter(SplashActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<CurrencyModel>> call, Throwable t) {

                UIHelper.showLongToastInCenter(SplashActivity.this, t.getMessage());
            }
        });
    }
}