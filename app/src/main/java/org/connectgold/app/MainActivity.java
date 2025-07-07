package com.connectgold.app;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.AppodealCallbacks.BannerCallbacks;
import com.appodeal.ads.AppodealCallbacks.InterstitialCallbacks;
import com.appodeal.ads.AppodealCallbacks.RewardedVideoCallbacks;
import com.appodeal.ads.utils.LogLevel;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AdView adView;
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
        
        // WebView Setup
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://connectgold.sbs");

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();

        // AdMob Banner
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

        // AdMob Interstitial
        InterstitialAd.load(this, INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd ad) {
                interstitialAd = ad;
                interstitialAd.show(MainActivity.this);
            }
        });

        // AdMob Rewarded
        RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(RewardedAd ad) {
                rewardedAd = ad;
                rewardedAd.show(MainActivity.this, reward -> {
                    Log.d("AdMob", "User earned reward: " + reward.getAmount());
                });
            }
        });

        // Load Native Ad
        AdLoader adLoader = new AdLoader.Builder(this, NATIVE_AD_UNIT_ID)
                .forNativeAd(nativeAd -> {
                    NativeAdView adView = (NativeAdView) getLayoutInflater()
                            .inflate(R.layout.native_ad_layout, null);
                    populateNativeAdView(nativeAd, adView);
                    FrameLayout adFrame = findViewById(R.id.native_ad_frame);
                    adFrame.removeAllViews();
                    adFrame.addView(adView);
                })
                .withAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.d("AdMob", "Native ad failed: " + adError.getMessage());
                    }
                })
                .build();
        adLoader.loadAd(adRequest);

        // Appodeal Init
        Appodeal.setLogLevel(LogLevel.DEBUG);
        Appodeal.initialize(this, APPODEAL_KEY,
                Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE);

        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        // Appodeal Banner Callbacks
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                Log.d("Appodeal", "Banner loaded");
            }
            @Override
            public void onBannerFailedToLoad() {
                Log.d("Appodeal", "Banner failed");
            }
            @Override
            public void onBannerShown() {
                Log.d("Appodeal", "Banner shown");
            }
            @Override
            public void onBannerClicked() {
                Log.d("Appodeal", "Banner clicked");
            }
            @Override
            public void onBannerExpired() {
                Log.d("Appodeal", "Banner expired");
            }
        });

        // Appodeal Interstitial Callbacks
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                Log.d("Appodeal", "Interstitial loaded");
            }
            @Override
            public void onInterstitialFailedToLoad() {
                Log.d("Appodeal", "Interstitial failed");
            }
            @Override
            public void onInterstitialShown() {
                Log.d("Appodeal", "Interstitial shown");
            }
            @Override
            public void onInterstitialClicked() {
                Log.d("Appodeal", "Interstitial clicked");
            }
            @Override
            public void onInterstitialClosed() {
                Log.d("Appodeal", "Interstitial closed");
            }
            @Override
            public void onInterstitialExpired() {
                Log.d("Appodeal", "Interstitial expired");
            }
            @Override
            public void onInterstitialShowFailed() {
                Log.d("Appodeal", "Interstitial show failed");
            }
        });

        // Appodeal Rewarded Callbacks
        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean isPrecache) {
                Log.d("Appodeal", "Rewarded loaded");
            }
            @Override
            public void onRewardedVideoFailedToLoad() {
                Log.d("Appodeal", "Rewarded failed");
            }
            @Override
            public void onRewardedVideoShown() {
                Log.d("Appodeal", "Rewarded shown");
            }
            @Override
            public void onRewardedVideoClosed(boolean finished) {
                Log.d("Appodeal", "Rewarded closed");
            }
            @Override
            public void onRewardedVideoCompleted() {
                Log.d("Appodeal", "Rewarded completed");
            }
            @Override
            public void onRewardedVideoExpired() {
                Log.d("Appodeal", "Rewarded expired");
            }
            @Override
            public void onRewardedVideoClicked() {
                Log.d("Appodeal", "Rewarded clicked");
            }
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
