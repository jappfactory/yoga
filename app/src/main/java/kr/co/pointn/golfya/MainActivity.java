package kr.co.pointn.golfya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**dc dddd
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    final AppCompatActivity activity = this;


    public  ListView driverMovieListView;
    public List<DriverMovie> driverMovieList;
    public DriverMovieListAdapter driveradapter;

    String target = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g";
    String target2 = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=2&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g";
    private static Context context;
    private static  int networkYn = 0;

    String drivertarget = target +"&q=드라이버+원포인트+레슨";
    String woodtarget = target +"&q=골프+우드+유틸리티+원포인트+레슨";
    String irontarget = target +"&q=골프+아이언+원포인트+레슨";
    String wedgetarget = target +"&q=골프+웨지+원포인트+레슨";
    String puttertarget = target +"&q=골프+퍼팅+원포인트+레슨";

    String drivertarget2 = target2 +"&q=드라이버+원포인트+레슨";
    String woodtarget2 = target2 +"&q=골프+우드+유틸리티+원포인트+레슨";
    String irontarget2 = target2 +"&q=골프+아이언+원포인트+레슨";
    String wedgetarget2 = target2 +"&q=골프+웨지+원포인트+레슨";
    String puttertarget2 = target2 +"&q=골프+퍼팅+원포인트+레슨";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // final Button newsButton = (Button) findViewById(R.id.newsButton);
        final Button homeButton = (Button) findViewById(R.id.homeButton);
        final Button driverButton = (Button) findViewById(R.id.driverButton);
        final Button woodButton = (Button) findViewById(R.id.woodButton);
        final Button ironButton = (Button) findViewById(R.id.ironButton);
        final Button wedgeButton = (Button) findViewById(R.id.wedgeButton);
        final Button putterButton = (Button) findViewById(R.id.putterButton);

        final LinearLayout main_news = (LinearLayout) findViewById(R.id.main_news) ;

        homeButton.setBackgroundColor(getResources().getColor(R.color.colorBlueDark));
        driverButton.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        woodButton.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        ironButton.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        wedgeButton.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        putterButton.setBackgroundColor(getResources().getColor(R.color.colorBlue));


        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("전송 URL", drivertarget);

                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("target",drivertarget);


                AdsFull.getInstance(getApplicationContext()).setAdsFull();
                startActivity(intent);
            }
        });

        woodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("전송 URL", woodtarget);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("target",woodtarget);AdsFull.getInstance(getApplicationContext()).setAdsFull();
                startActivity(intent);
            }
        });

        ironButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("전송 URL", irontarget);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("target",irontarget);
                startActivity(intent);
            }
        });

        wedgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("전송 URL", wedgetarget);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("target",wedgetarget);
                startActivity(intent);
            }
        });

        putterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("전송 URL", puttertarget);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("target",puttertarget);
                startActivity(intent);
            }
        });

        driverMovieListView  = findViewById(R.id.driveronepoint);
        driverMovieList = new ArrayList<DriverMovie>();

        Log.d("전송 URL", drivertarget2);
        new LoadMovieTask(this, driverMovieList, driverMovieListView, drivertarget2).execute();

        driverMovieListView  = findViewById(R.id.woodonepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        new LoadMovieTask(this, driverMovieList, driverMovieListView, woodtarget2).execute();

        driverMovieListView  = findViewById(R.id.irononepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        new LoadMovieTask(this, driverMovieList, driverMovieListView, irontarget2).execute();




        AdsFull.getInstance(getApplicationContext()).setAds(this);

        String token = FirebaseInstanceId.getInstance().getToken();
        updateIconBadge(activity,  0);





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


    private  Context mContext;
    private DriverMovieListAdapter driveradapter;
    private List<DriverMovie> driverMovieList;
    private ListView driverMovieListView;
    String target;


    public LoadMovieTask(Context context, List<DriverMovie> driverMovieList, ListView view, String target) {
        this.mContext = context;
        this.driverMovieList = driverMovieList;
        this.driverMovieListView = view;
        this.target = target;
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {

            URL url = new URL(target);
            Log.e("주소 url", ""+url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            //Log.e("inputStream", ""+inputStream);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            // Log.e("bufferedReader", ""+bufferedReader);
            String temp;
            StringBuilder stringBuilder = new StringBuilder();

            while ((temp = bufferedReader.readLine()) != null) {
                Log.e("temp", ""+temp);
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


        Log.e("드라이버2", ""+result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            int count = 0;
            Log.e("드라이버3", ""+jsonArray.length());

            String thum_pic, subjectText, viewCount, viewDate, viewCnt="0", videoId;


            while (count < jsonArray.length()) {

                JSONObject object = jsonArray.getJSONObject(count);



                videoId = object.getJSONObject("id").getString("videoId");
                subjectText = object.getJSONObject("snippet").getString("title");
                viewDate = object.getJSONObject("snippet").getString("publishedAt")
                        .substring(0, 10);
                thum_pic = object.getJSONObject("snippet")
                        .getJSONObject("thumbnails").getJSONObject("high")
                        .getString("url"); // 썸내일 이미지 URL값



                Log.e("thum_pic", ""+thum_pic);
                Log.e("subjectText", ""+subjectText);
                Log.e("viewDate", ""+viewDate);
                //Log.e("드라이버4", "" + object);
/*

                thum_pic = object.getString("thumbnails");
                subjectText = object.getString("title");
                viewDate = object.getString("viewDate");
                viewCnt = object.getString("cnt");
*/

                DriverMovie drivermovie = new DriverMovie(thum_pic, subjectText, viewDate, viewCnt);


                // Log.e("드라이버5", "" + driverMovieList);

                driverMovieList.add(drivermovie);


                count++;
            }

            Log.d("driverMovieListView2 ", ""+driverMovieListView);
            driveradapter = new DriverMovieListAdapter(mContext.getApplicationContext(), driverMovieList);
            driverMovieListView.setAdapter(driveradapter);


            Log.d("driverMovieList7", ""+driverMovieList);


        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

    }


}
