package kr.appfactory.golf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**dc dddd
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    final AppCompatActivity activity = this;

    private String  target ="http://golf.pointn.co.kr/index.php/gms/reg/";;
    private  String nextPageToken;
    private static Context context;
    private static  int networkYn = 0;
    private SharedPreferences PageToken;
    private SharedPreferences.Editor pt;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar myToolbar;

    MyFirebaseInstanceIDService mf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token::" + token);

        //   target = target + token;

        SharedPreferences  PageToken = getSharedPreferences(nextPageToken, 0);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우


        new gms_reg().execute();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left_menu); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요

        /** * 기본 화면 설정 */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new DriverFragment());
        fragmentTransaction.commit();

        AdsFull.getInstance(getApplicationContext()).setAds(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setVerticalFadingEdgeEnabled(false);
        navigationView.setVerticalScrollBarEnabled(false);
        navigationView.setHorizontalScrollBarEnabled(false);

        updateIconBadge(activity,  0);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        Log.d(TAG, "페이지이동 ");

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

        AdsFull.getInstance(getApplicationContext()).setAdsFull();

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

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){


            Toast.makeText (getApplicationContext(), "클릭"  , Toast.LENGTH_LONG).show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        Toast.makeText (getApplicationContext(), "클릭2"  , Toast.LENGTH_LONG).show();

        int id = item.getItemId();
        if (id == R.id.club_movie) {
            transaction.replace(R.id.fragment, new DriverFragment());
        } else if (id == R.id.channel_1) {
            transaction.replace(R.id.fragment, new IronFragment());
        } else {

        }

        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);

        //mDrawerLayout.closeDrawer(); return true;

        return true;
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

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


        //startActivity(new Intent(getApplicationContext(), OfflineActivity.class));


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



class LoadMovieTask extends AsyncTask<Void, Void, String> {


    private SharedPreferences PageToken;
    private SharedPreferences.Editor pt;

    private  String location;
    private  Context mContext;
    private DriverMovieListAdapter driveradapter;
    private List<DriverMovie> driverMovieList;
    private ListView driverMovieListView;
    String target;

    private MainActivity activity;



    public LoadMovieTask(Context context, List<DriverMovie> driverMovieList, ListView view, DriverMovieListAdapter driveradapter, String target, String location) {
        this.mContext = context;
        this.driverMovieList = driverMovieList;
        this.driveradapter = driveradapter;
        this.driverMovieListView = view;
        this.target = target;
        this.location = location;

    }


    @Override
    protected String doInBackground(Void... voids) {

        try {

            URL url = new URL(target);
            //Log.e("주소 url", ""+url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String temp;
            StringBuilder stringBuilder = new StringBuilder();

            while ((temp = bufferedReader.readLine()) != null) {
                // Log.e("temp", ""+temp);
                stringBuilder.append(temp + "\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    protected void onPostExecute(String result) {


        //Log.e("드라이버2", ""+result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            String nextPageToken = jsonObject.getString("nextPageToken");
            String totalResults = jsonObject.getJSONObject("pageInfo").getString("totalResults");



            SharedPreference.putSharedPreference(mContext, "totalResults", totalResults);
            SharedPreference.putSharedPreference(mContext, "nextPageToken", nextPageToken);
            //Toast.makeText (mContext, "클릭" + slat , Toast.LENGTH_LONG).show();


            int count = 0;
            String thum_pic, subjectText, descriptionText, viewCount, viewDate, viewCnt, videoId;


            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);


                videoId = object.getJSONObject("id").getString("videoId");
                subjectText = object.getJSONObject("snippet").getString("title");
                descriptionText = object.getJSONObject("snippet").getString("description");
                viewDate = object.getJSONObject("snippet").getString("publishedAt")
                        .substring(0, 10);
                thum_pic = object.getJSONObject("snippet")
                        .getJSONObject("thumbnails").getJSONObject("medium")
                        .getString("url"); // 썸내일 이미지 URL값

                viewCnt = "0";



                DriverMovie drivermovie = new DriverMovie(thum_pic, subjectText, viewDate, viewCnt, videoId , descriptionText);

                driverMovieList.add(drivermovie);

                count++;
            }


            if(location =="main"){
                driverMovieListView.setAdapter(driveradapter);


                driverMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(view.getContext(), MoviePlayActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("videoId", ""+  driverMovieList.get(position).getMovie_videoId());
                        intent.putExtra("videodesc", ""+  driverMovieList.get(position).getMovie_desc());
                        intent.putExtra("title",""+ driverMovieList.get(position).getMovie_title());

                        view.getContext().startActivity(intent);

                    }
                });

            }


        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

    }


}




class gms_reg extends AsyncTask<Void, Void, String> {
    private  Context mContext;
    String target ="http://golf.pointn.co.kr/index.php/Gms/reg/"+FirebaseInstanceId.getInstance().getToken();

    String target2 ="http://golf.pointn.co.kr/index.php/Gms/cnt/"+FirebaseInstanceId.getInstance().getToken();
    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String temp;
        URL url;

        try {
            url = new URL(target2);
            Log.e("주소 url 2 ", ""+url);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            stringBuilder = new StringBuilder();
            Log.e("stringBuilder : ", ""+stringBuilder);
            while ((temp = bufferedReader.readLine()) != null) {
                Log.e("temp", ""+temp);
                stringBuilder.append(temp + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            int numInt = Integer.parseInt(stringBuilder.toString().trim());

            Log.e("numInt", ""+numInt);
            if (numInt == 0 ) {

                try {

                    url = new URL(target);
                    Log.e("주소 url 1", ""+url);


                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    inputStream = httpURLConnection.getInputStream();

                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                    stringBuilder = new StringBuilder();

                    while ((temp = bufferedReader.readLine()) != null) {
                        // Log.e("temp", ""+temp);
                        stringBuilder.append(temp + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }


            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    protected void onPostExecute(String result) {



    }

}