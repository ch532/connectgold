package com.connectgold.app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.*;
import com.google.android.gms.ads.nativead.*;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.appodeal.ads.utils.LogLevel;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;

    private final String ADMOB_APP_ID = "ca-app-pub-1171216593802007~4173705766";
    private final String BANNER_AD_UNIT_ID = "ca-app-pub-1171216593802007/8884489855";
    private final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-1171216593802007/4945244849";
    private final String REWARDED_AD_UNIT_ID = "ca-app-pub-1171216593802007/1435861786";
    private final String NATIVE_AD_UNIT_ID = "ca-app-pub-1171216593802007/5226917075";
    private final String APPODEAL_KEY = "923bc30ebaca5186de21bbebb9612173a8759ba61bbb4ed0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Load website
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://connectgold.sbs");

        // Init AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        // Show banner ad
        bannerAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);

        // Show interstitial ad
        InterstitialAd.load(this, INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            public void onAdLoaded(InterstitialAd ad) {
                interstitialAd = ad;
                interstitialAd.show(MainActivity.this);
            }
        });

        // Show rewarded ad
        RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            public void onAdLoaded(RewardedAd ad) {
                rewardedAd = ad;
                rewardedAd.show(MainActivity.this, reward -> {
                    Log.d("AdMob", "User earned reward.");
                });
            }
        });

        // Load native ad
        AdLoader adLoader = new AdLoader.Builder(this, NATIVE_AD_UNIT_ID)
                .forNativeAd(nativeAd -> {
                    NativeAdView adView = findViewById(R.id.nativeAdView);
                    populateNativeAdView(nativeAd, adView);
                })
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());

        // Init Appodeal
        Appodeal.setLogLevel(LogLevel.DEBUG);
        Appodeal.initialize(this, APPODEAL_KEY, Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);

        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            public void onInterstitialClicked() { Log.d("Appodeal", "Interstitial Clicked"); }
            public void onInterstitialShown() { Log.d("Appodeal", "Shown"); }
            public void onInterstitialLoaded(boolean isPrecache) { Log.d("Appodeal", "Loaded"); }
            public void onInterstitialFailedToLoad() { Log.d("Appodeal", "Failed"); }
            public void onInterstitialClosed() { Log.d("Appodeal", "Closed"); }
            public void onInterstitialExpired() { Log.d("Appodeal", "Expired"); }
            public void onInterstitialShowFailed() { Log.d("Appodeal", "Show Failed"); }
        });

        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            public void onRewardedVideoClicked() { Log.d("Appodeal", "Rewarded Clicked"); }
            public void onRewardedVideoShown() { Log.d("Appodeal", "Shown"); }
            public void onRewardedVideoClosed(boolean finished) { Log.d("Appodeal", "Closed"); }
            public void onRewardedVideoCompleted() { Log.d("Appodeal", "Completed"); }
            public void onRewardedVideoFailedToLoad() { Log.d("Appodeal", "Failed to Load"); }
            public void onRewardedVideoExpired() { Log.d("Appodeal", "Expired"); }
            public void onRewardedVideoLoaded(boolean isPrecache) { Log.d("Appodeal", "Loaded"); }
        });

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            public void onBannerClicked() { Log.d("Appodeal", "Banner Clicked"); }
            public void onBannerShown() { Log.d("Appodeal", "Banner Shown"); }
            public void onBannerFailedToLoad() { Log.d("Appodeal", "Banner Failed"); }
            public void onBannerLoaded(int height, boolean isPrecache) { Log.d("Appodeal", "Banner Loaded"); }
            public void onBannerExpired() { Log.d("Appodeal", "Banner Expired"); }
        });
    }

    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        TextView headline = adView.findViewById(R.id.headline);
        TextView body = adView.findViewById(R.id.body);
        Button callToAction = adView.findViewById(R.id.call_to_action);

        headline.setText(nativeAd.getHeadline());
        body.setText(nativeAd.getBody());
        callToAction.setText(nativeAd.getCallToAction());

        adView.setHeadlineView(headline);
        adView.setBodyView(body);
        adView.setCallToActionView(callToAction);
        adView.setNativeAd(nativeAd);
    }
}
