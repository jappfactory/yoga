package kr.co.pointn.golfya;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdsFull {

    private static InterstitialAd adFull;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static AdsFull instance = null;
    private static Context context;
    private InterstitialAd mInterstitialAd;

    public AdsFull(Context context) {
        this.context = context;
    }

    public static AdsFull getInstance(Context context) {


        if (instance == null) {
            instance = new AdsFull(context);
           // adFull = new InterstitialAd(context);
           // setAds2(this);
        }

        return instance;
    }


    public void  setAds2() {//

        MobileAds.initialize(context, "ca-app-pub-3808489523903055~9077770988");
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.banner_ad_unit_id2));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded(){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("asd", "The interstitial wasn't loaded yet.");
                }
            }
        });
    }

    public void  setAds(Context context) {//

        MobileAds.initialize(context, "ca-app-pub-3808489523903055~9077770988");

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");//test

//
        AdView adView = (AdView) ((Activity) context).findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);



        AdView adView2 = (AdView) ((Activity) context).findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView2.loadAd(adRequest2);


        AdView adView3 = (AdView) ((Activity) context).findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView3.loadAd(adRequest3);


    }
}
