// MainActivity.java package com.connectgold.app;

import android.os.Bundle; import android.webkit.WebView; import android.webkit.WebViewClient; import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest; import com.google.android.gms.ads.AdView; import com.google.android.gms.ads.MobileAds; import com.google.android.gms.ads.interstitial.InterstitialAd; import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback; import com.google.android.gms.ads.rewarded.RewardedAd; import com.google.android.gms.ads.rewarded.RewardItem; import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import com.appodeal.ads.Appodeal; import com.appodeal.ads.AppodealNative; import com.appodeal.ads.utils.LogLevel;

public class MainActivity extends AppCompatActivity { private WebView webView; private AdView bannerAdView; private InterstitialAd mInterstitialAd; private RewardedAd rewardedAd; private static final String ADMOB_APP_ID = "ca-app-pub-1171216593802007~4173705766"; private static final String BANNER_AD_UNIT_ID = "ca-app-pub-1171216593802007/8884489855"; private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-1171216593802007/4945244849"; private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-1171216593802007/1435861786"; private static final String NATIVE_AD_UNIT_ID = "ca-app-pub-1171216593802007/5226917075"; private static final String APPODEAL_KEY = "923bc30ebaca5186de21bbebb9612173a8759ba61bbb4ed0";

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Load WebView
    webView = findViewById(R.id.webview);
    webView.setWebViewClient(new WebViewClient());
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl("https://connectgold.sbs");

    // Initialize AdMob
    MobileAds.initialize(this, initializationStatus -> {});

    // Load Banner Ad
    bannerAdView = findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    bannerAdView.loadAd(adRequest);

    // Load Interstitial Ad
    InterstitialAd.load(this, INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
        @Override
        public void onAdLoaded(InterstitialAd ad) {
            mInterstitialAd = ad;
            mInterstitialAd.show(MainActivity.this);
        }
    });

    // Load Rewarded Ad
    RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
        @Override
        public void onAdLoaded(RewardedAd ad) {
            rewardedAd = ad;
            rewardedAd.show(MainActivity.this, rewardItem -> {
                int rewardAmount = rewardItem.getAmount();
                String rewardType = rewardItem.getType();
                // Handle reward logic here
            });
        }
    });

    // Initialize Appodeal
    Appodeal.setLogLevel(LogLevel.DEBUG);
    Appodeal.initialize(this, APPODEAL_KEY, 
        Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);

    Appodeal.show(this, Appodeal.BANNER_BOTTOM);
}

}

