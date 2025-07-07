package com.connectgold.app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.*;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.utils.LogLevel;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;

    private final String ADMOB_APP_ID = "ca-app-pub-1171216593802007~4173705766";
    private final String BANNER_AD_UNIT_ID = "ca-app-pub-1171216593802007/8884489855";
    private final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-1171216593802007/4945244849";
    private final String REWARDED_AD_UNIT_ID = "ca-app-pub-1171216593802007/1435861786";
    private final String APPODEAL_KEY = "923bc30ebaca5186de21bbebb9612173a8759ba61bbb4ed0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebView setup
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://connectgold.sbs");

        // AdMob init
        MobileAds.initialize(this, initializationStatus -> {});

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        InterstitialAd.load(this, INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd ad) {
                interstitialAd = ad;
                interstitialAd.show(MainActivity.this);
            }
        });

        RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(RewardedAd ad) {
                rewardedAd = ad;
                rewardedAd.show(MainActivity.this, reward -> {
                    // reward logic
                });
            }
        });

        // Appodeal init
        Appodeal.setLogLevel(LogLevel.DEBUG);
        Appodeal.initialize(this, APPODEAL_KEY, Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }
}
