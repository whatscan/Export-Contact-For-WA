package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


import java.util.Objects;

public class Advertisement {
    public static boolean isShow = true;
    public static InterstitialAd mInterstitialAd;
    public static NativeAd mNativeAd = null;
    public static AdView mAdView = null;
    public static MyCallback myCallback;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    @SuppressLint("StaticFieldLeak")
    private static Advertisement mInstance;

    public Advertisement(Activity activity) {
        Advertisement.activity = activity;
    }

    public static void preLoadAds(Activity activity) {
        if (!Preference.getActive_Weekly().equals("true") || !Preference.getActive_Monthly().equals("true") || !Preference.getActive_Yearly().equals("true")) {
            preLoadFull(activity);
            preLoadBanner(activity);
            preLoadNative(activity);
        }
    }

    public static void preLoadFull(Activity activity) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, "ca-app-pub-6447655601926357/9895627502", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }


    public static void preLoadBanner(Activity activity) {
        AdView adView = new AdView(activity);
        adView.setAdUnitId("ca-app-pub-6447655601926357/3521790845");
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView = adView;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mAdView = null;

            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }


    public static void preLoadNative(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, "ca-app-pub-6447655601926357/9352702025").forNativeAd(nativeAd -> {
            mNativeAd = nativeAd;
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mNativeAd = null;
                @SuppressLint("DefaultLocale") String error = String.format("domain: %s, code: %d, message: %s", loadAdError.getDomain(), loadAdError.getCode(),
                        loadAdError.getMessage());
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public static AdSize getAdSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static void interstitialCallBack() {
        if (myCallback != null) {
            myCallback.callbackCall();
            myCallback = null;
        }
    }

    public static Advertisement getInstance(Activity activity) {
        Advertisement.activity = activity;
        if (mInstance == null) {
            mInstance = new Advertisement(activity);
        }
        return mInstance;
    }


    public void showFull(Activity activity, MyCallback myCallback) {
        Advertisement.myCallback = myCallback;
        if (!Preference.getActive_Weekly().equals("true") || !Preference.getActive_Monthly().equals("true") || !Preference.getActive_Yearly().equals("true")) {
            if (mInterstitialAd == null) {
                interstitialCallBack();
            } else {
                if (isShow) {
                    mInterstitialAd.show(activity);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            isShow = false;
                            preLoadFull(activity);
                            interstitialCallBack();
                            handlerSec();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            // Called when fullscreen content failed to show.
                            interstitialCallBack();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                        }
                    });
                } else {
                    interstitialCallBack();
                }
            }
        }
    }

    public static void handlerSec() {
        new CountDownTimer(4 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isShow = true;
            }
        }.start();
    }

    public static void showBanner(Activity activity, LinearLayout ll_banner) {
        if (!Preference.getActive_Weekly().equals("true") || !Preference.getActive_Monthly().equals("true") || !Preference.getActive_Yearly().equals("true")) {
            if (mAdView != null) {
                if (mAdView.getParent() != null) {
                    ((ViewGroup) mAdView.getParent()).removeView(mAdView);
                }
                ll_banner.addView(mAdView);
                mAdView = null;
                preLoadBanner(activity);
            }
        }
    }

    public static void shoNativeAds(Activity activity, FrameLayout frameLayout) {
        if (!Preference.getActive_Weekly().equals("true") || !Preference.getActive_Monthly().equals("true") || !Preference.getActive_Yearly().equals("true")) {
            if (mNativeAd != null) {
                NativeAdView adView;
                if (Preference.getBooleanTheme(false)) {
                    adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified_new_b, null);
                } else {
                    adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);
                }

                frameLayout.addView(adView);
                populateUnifiedNativeAdView(mNativeAd, adView);
                mNativeAd = null;
                preLoadNative(activity);
            } else {
                preLoadNative(activity);
            }
        }
    }

    private static void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public interface MyCallback {
        void callbackCall();
    }
}

