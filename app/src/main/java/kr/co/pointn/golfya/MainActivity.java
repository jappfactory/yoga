package kr.co.pointn.golfya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**dc dddd
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    final AppCompatActivity activity = this;

    private ListView newsListView;
    private NewsListAdapter adapter;
    private List<News> newsList;

    private ListView driverMovieListView;
    private DriverMovieListAdapter driveradapter;
    private List<DriverMovie> driverMovieList;

    private ListView mainIronListView;

    private static  int networkYn = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        newsListView = findViewById(R.id.mainNewsListView);
        newsList = new ArrayList<News>();
        newsList.add(new News("뉴스입니다","쥬피터","2018-10-10"));
        newsList.add(new News("뉴스입니다","쥬피터","2018-10-10"));
        newsList.add(new News("뉴스입니다","쥬피터","2018-10-10"));
        newsList.add(new News("뉴스입니다","쥬피터","2018-10-10"));
        newsList.add(new News("뉴스입니다","쥬피터","2018-10-10"));

        adapter = new NewsListAdapter(getApplicationContext(), newsList);
        newsListView.setAdapter(adapter);



        driverMovieListView  = findViewById(R.id.mainDriverListView);
        driverMovieList = new ArrayList<DriverMovie>();
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));

        driveradapter = new DriverMovieListAdapter(getApplicationContext(), driverMovieList);
        driverMovieListView.setAdapter(driveradapter);


        mainIronListView  = findViewById(R.id.mainIronListView);
        driverMovieList = new ArrayList<DriverMovie>();
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","100"));

        driveradapter = new DriverMovieListAdapter(getApplicationContext(), driverMovieList);
        mainIronListView.setAdapter(driveradapter);


        final Button newsButton = (Button) findViewById(R.id.newsButton);
        final Button driverButton = (Button) findViewById(R.id.driverButton);
        final Button ironButton = (Button) findViewById(R.id.ironButton);
        final LinearLayout main_news = (LinearLayout) findViewById(R.id.main_news) ;


        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_news.setVisibility(View.GONE);
                newsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                driverButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ironButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new NewsFragment());
                fragmentTransaction.commit();
                
            }
        });


        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_news.setVisibility(View.GONE);
                newsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                driverButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                ironButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new DriverFragment());
                fragmentTransaction.commit();

            }
        });


        ironButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_news.setVisibility(View.GONE);
                newsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                driverButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ironButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new IronFragment());
                fragmentTransaction.commit();

            }
        });


        String token = FirebaseInstanceId.getInstance().getToken();
        updateIconBadge(activity,  0);

        setAds();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        Log.d(TAG, "페이지이동 ");

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        builder.setIcon(R.drawable.golfya_icon);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.exitmsg);
        builder.setPositiveButton(R.string.exitmsgY, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                });
        builder.setNegativeButton(R.string.exitmsgN, null);
        AlertDialog dialog = builder.show();


    }
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Online();
            if(networkYn==2){

                NotOnline();
                return true;

            }else {

                Log.d("check URL", url);
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);


            }
        }
    }
    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public static void updateIconBadge(Context context, int notiCnt) {
        Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        badgeIntent.putExtra("badge_count", notiCnt);
        badgeIntent.putExtra("badge_count_package_name", context.getPackageName());
        badgeIntent.putExtra("badge_count_class_name", getLauncherClassName(context));
        context.sendBroadcast(badgeIntent);
    }
    public void NotOnline() {
        final String networkmsg = getString(R.string.networkmsg);

        //mWebView.loadUrl("javascript:alert('"+networkmsg+"')");



        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setIcon(R.drawable.golfya_icon)
                .setTitle(R.string.app_name)
                .setMessage(""+networkmsg+"")
                .setNegativeButton(R.string.exitmsgN, null)
                .setPositiveButton(R.string.exitmsgY,new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int whichButton)
                    {
                        finish();
                        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
                    }
                }).show();


       // startActivity(new Intent(getApplicationContext(), OfflineActivity.class));


    }
    private void setAds() {//

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3808489523903055~9077770988");

       MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");//test

//
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB")
                .addTestDevice("F04045E9794CCECCCE886A59B2FF79DA")
                .build();
        adView.loadAd(adRequest);



        AdView adView2 = (AdView) findViewById(R.id.adView2);
        adView2.loadAd(adRequest);


        AdView adView3 = (AdView) findViewById(R.id.adView3);
        adView3.loadAd(adRequest);



    }

    public void Online() {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {

            Log.d("연결됨" , "연결이 되었습니다.");
            networkYn =1;


        } else {
            Log.d("연결 안 됨" , "연결이 다시 한번 확인해주세요");
            networkYn =2;

        }
    }



}
