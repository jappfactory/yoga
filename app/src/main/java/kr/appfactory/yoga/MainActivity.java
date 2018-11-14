package kr.appfactory.yoga;

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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**dc dddd
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getSimpleName();
    final AppCompatActivity activity = this;

    private String  target ="http://www.appfactory.kr/gms/reg/Billiard";;
    private  String nextPageToken;
    private static Context context;
    private static  int networkYn = 0;
    private SharedPreferences PageToken;
    private SharedPreferences.Editor pt;
    private DrawerLayout mDrawerLayout;
    private View drawerView;
    private ActionBarDrawerToggle mToggle;
    Toolbar myToolbar;
    private ListView mnuListView;
    private ListView mnuListView2;
    private ListView mnuListView3;
    public List<MenuItema> itemList;
    public List<MenuItema> itemList2;
    public List<MenuItema> itemList3;
    public MenuItemAdapter menuItemAdapter;
    public MenuItemAdapter menuItemAdapter2;
    public MenuItemAdapter menuItemAdapter3;
    private MaterialSearchView searchView;
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView = (View) findViewById(R.id.nav_view);



        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.

        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left_menu); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요


        searchView = findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
       // searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //SearchProduct(getApplicationContext(), query);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, SearchFragment.newInstance(query));
                fragmentTransaction.commit();

                //Toast.makeText(getApplicationContext(),"Query: " + query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.showSuggestions();
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.showSuggestions();
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
 /*

        //myToolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left_menu); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
*/
        /** * 기본 화면 설정 */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new View1Fragment());
        fragmentTransaction.commit();

        AdsFull.getInstance(getApplicationContext()).setAds(this);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        navigationView.setVerticalFadingEdgeEnabled(false);
        navigationView.setVerticalScrollBarEnabled(false);
        navigationView.setHorizontalScrollBarEnabled(false);


        updateIconBadge(activity,  0);




        Button MyfavoritesButton = (Button) findViewById(R.id.MyfavoritesButton);

        //즐겨찾기저장추가
        MyfavoritesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FavoritesFragment());
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawers();

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


       // Log.d(TAG, "페이지이동 ");

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

        AdsFull.getInstance(getApplicationContext()).setAdsFull();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        builder.setIcon(R.drawable.billiard_icon);
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

    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setChecked(true);
        switch (item.getItemId()){

            case 2131230744: {

                Toast.makeText (activity, "" + item.getItemId() , Toast.LENGTH_SHORT).show();
                break;
            }

            case android.R.id.home: {


                // 데이터 원본 준비
                itemList = new ArrayList<MenuItema>();


                //어댑터 생성
                menuItemAdapter = new MenuItemAdapter(activity, itemList);

                //어댑터 연결
                mnuListView = (ListView) findViewById(R.id.club_lesson);

                //Toast.makeText (activity, "클릭3" + mnuListView  , Toast.LENGTH_LONG).show();
                mnuListView.setAdapter(menuItemAdapter);


                itemList.add(new MenuItema("당구 기초 영상", "sub1"));
                itemList.add(new MenuItema("4구 강좌 영상", "sub2"));
                itemList.add(new MenuItema("3쿠션 강좌 영상", "sub3"));
                itemList.add(new MenuItema("포켓볼 강좌 영상", "sub4"));
                itemList.add(new MenuItema("당구묘기 영상", "sub5"));
                //  menuItemAdapter = new MenuItemAdapter(context,  itemList, this);

                mnuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        if (itemList.get(position).getMenu_link() == "sub1")
                            fragmentTransaction.replace(R.id.fragment, new View1Fragment());
                        if (itemList.get(position).getMenu_link() == "sub2")
                            fragmentTransaction.replace(R.id.fragment, new View2Fragment());
                        if (itemList.get(position).getMenu_link() == "sub3")
                            fragmentTransaction.replace(R.id.fragment, new View3Fragment());
                        if (itemList.get(position).getMenu_link() == "sub4")
                            fragmentTransaction.replace(R.id.fragment, new View4Fragment());
                        if (itemList.get(position).getMenu_link() == "sub5")
                            fragmentTransaction.replace(R.id.fragment, new View5Fragment());


                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();

                        // Toast.makeText (activity, "클릭 getMenu_title" + itemList.get(position).getMenu_title()  , Toast.LENGTH_SHORT).show();
                        //Toast.makeText (activity, "클릭 getMenu_link" + itemList.get(position).getMenu_link()  , Toast.LENGTH_SHORT).show();
                    }
                });

                // 데이터 원본 준비
                itemList2 = new ArrayList<MenuItema>();
                ArrayList<Object> channel_list = new ArrayList<Object>();
                channel_list.add("김경률");
                channel_list.add("김행직");
                channel_list.add("서현민");
                channel_list.add("강동궁");
                channel_list.add("김형곤");
                channel_list.add("조명우");
                channel_list.add("조재호");
                channel_list.add("오성욱");
                channel_list.add("김봉철");
                channel_list.add("김재근");
                channel_list.add("허정한");
                channel_list.add("최성원");
                channel_list.add("조치연");
                channel_list.add("류승우");
                channel_list.add("김가영");
                channel_list.add("프레드릭 쿠드롱");
                channel_list.add("토르비에른 블롬달");
                channel_list.add("딕 야스퍼스");
                channel_list.add("다니 산체스");
                channel_list.add("마르코 자네티");
                channel_list.add("세미흐 사이그네르");
                channel_list.add("레이몽 클루망");
                Iterator<Object> ie = channel_list.iterator();

                while(ie.hasNext()) {



                    String title = ie.next().toString();
                    String Keyword = getURLEncode(""+title+" 당구");
                    //itemList2.add(new MenuItema("명품스윙 에이미 조 골프 레슨", "https://www.googleapis.com/youtube/v3/search?part=snippet&order=relevance&videoSyndicated=true&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&safeSearch=strict&type=video&q=포켓볼&pageToken="));
                    itemList2.add(new MenuItema(""+title+" 프로", "https://www.googleapis.com/youtube/v3/search?part=snippet&order=relevance&videoSyndicated=true&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&safeSearch=strict&type=video&q="+Keyword+"&pageToken="));

                }

                //어댑터 생성
                menuItemAdapter2 = new MenuItemAdapter(activity, itemList2);
                //어댑터 연결
                mnuListView2 = (ListView) findViewById(R.id.pro_lesson);
                mnuListView2.setAdapter(menuItemAdapter2);


                mnuListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, ChannelFragment.newInstance(itemList2.get(position).getMenu_link(), itemList2.get(position).getMenu_title()));
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        //Toast.makeText (activity, "클릭 getMenu_title" + itemList2.get(position).getMenu_title()  , Toast.LENGTH_SHORT).show();


                    }
                });



                // 데이터 원본 준비
                itemList3 = new ArrayList<MenuItema>();


                itemList3.add(new MenuItema("[당구강좌 4구 & 3C] 4구 기초", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL43b2md03gKdN2zJGzqFVG_3aCfR8Xltk&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌 4구 & 3C] 4구 모아치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL43b2md03gKeHH28o67I0gCI-TXnQ3UQZ&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌 4구 & 3C] 4구 실력 향상을 위한 기본 배치 연습", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL43b2md03gKcc3sf4h_JsuDi6jE0Tfcm&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌 4구 & 3C] 끌어치기를 이용한 4구 모아치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL43b2md03gKeLLTqOttn7KZQxaQkej-vb&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[아이빌리TV] 당구기초", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PB4HKERIKQa_4KxIYeLxeNM&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[아이빌리TV] 당구두께조절", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PCD6KLQJo02Kxw6P-9GC8-3&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[아이빌리TV] 당구 끌어치기,밀어치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PCRppnvEjQoNTpxeQprdYWK&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[아이빌리TV] 4구당구 1,2쿠션", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PCS9XoC2Y4LMMJUFgcxW_gl&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[아이빌리TV] 4구당구모아치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PDvfzfZX7-47Z6uchWXHpVL&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[아이빌리TV] 3쿠션기초", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLQIx-v8Vj3PApkjxtUevIiM_7OEXkRS0l&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[닥스김의 당구TV] 뒤돌려치기,앞돌리기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL_OXA-uPAecVMKL-m55oIzU0XD-YSRndq&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[닥스김의 당구TV] 뱅크샷, 대회전", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL_OXA-uPAecV4RbwqJvbCnd7wLVEhA6kJ&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[닥스김의 당구TV] 빗겨치기, 세워치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL_OXA-uPAecU3sQTNnT3x951dQIhpB4E5&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[닥스김의 당구TV] 옆돌리기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL_OXA-uPAecXWWlczy2R6ClLQfhxIE-9I&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[닥스김의 당구TV] 횡단샷, 더블쿠션, 더블레일", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL_OXA-uPAecXn8MpACZBBvXaFCcSv6YCo&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[정필규 세리당구] 힘조절", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL-qbw1O53wy7omCkTX2-bEaayDQrhouQm&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[정필규 세리당구] 하이런[HIGH RUN]", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL-qbw1O53wy5mg5igxQ1yY5DVnAMBquiA&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[정필규 세리당구] 세리 연습법", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL-qbw1O53wy4NuduocevxHaiemjgPxji0&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[정필규 세리당구] 제 3구역", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL-qbw1O53wy6jJnL019uRk4Ex_8RkUIPX&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[정필규 세리당구] 세리 만들기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL-qbw1O53wy7iNAOa1owBLuwaYGy8MBxE&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[모두의 당구장] 당구시스템", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLBy7bkXgpNwaB3B80RcZKiSJGK0YMJOsu&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[조이빌리아드] The 레슨", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLklmWgSTNt_rqskHoRtNj1O9biyrnDdid&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

                itemList3.add(new MenuItema("[당구레슨] 비법전수", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLrrtQdjTU-KxO2TKmWz39OU25Q14G3De7&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));


                /*

                itemList3.add(new MenuItema("[당구강좌] 응급실 [Emergency Room]", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER8tV3kXUOOsReZ4e33JTDFe&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 고수들만의 비법", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER_kzB_AvI_wsEDy7WkqycYf&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 실전 당구 풀이 | 3쿠션-1쿠션 걸어치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER-A0HH_SGw3x0dH5z2KJ8rr&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 실전 당구 풀이 | 3쿠션-2쿠션 걸어치기", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER-v0B8vnx31lFTxo_w2rQT3&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 포켓볼 초급편", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER_GnNF-Eev7ozCW6zrSUt7v&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 포켓볼 초급편", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER-v0B8vnx31lFTxo_w2rQT3&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));
                itemList3.add(new MenuItema("[당구강좌] 포켓볼 중,고급편", "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLIHzcFhw_ER-v0B8vnx31lFTxo_w2rQT3&maxResults=10&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&pageToken="));

*/
                //어댑터 생성
                menuItemAdapter3 = new MenuItemAdapter(activity, itemList3);
                //어댑터 연결
                mnuListView3 = (ListView) findViewById(R.id.channel_lesson);
                mnuListView3.setAdapter(menuItemAdapter3);


                mnuListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, ChannelFragment.newInstance(itemList3.get(position).getMenu_link(), itemList3.get(position).getMenu_title()));
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        //Toast.makeText (activity, "클릭 getMenu_title" + itemList2.get(position).getMenu_title()  , Toast.LENGTH_SHORT).show();
                    }
                });



                mDrawerLayout.openDrawer(drawerView);
                //mDrawerLayout.closeDrawers();
            }

                return true;
            }

        return super.onOptionsItemSelected(item);
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
                .setIcon(R.drawable.billiard_icon)
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

    /**
     * getURLEncode
     */
    public static String getURLEncode(String content){

        try {
//          return URLEncoder.encode(content, "utf-8");   // UTF-8
            return URLEncoder.encode(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * getURLDecode
     */
    public static String getURLDecode(String content){

        try {
//          return URLEncoder.encode(content, "utf-8");   // UTF-8
            return URLDecoder.decode(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public int Online() {
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
        return networkYn;
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



            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

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
        String nextPageToken="";
        //Log.e("드라이버2", ""+result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            String totalResults = jsonObject.getJSONObject("pageInfo").getString("totalResults");

            try {
                nextPageToken = jsonObject.getString("nextPageToken");
            }  catch (Exception e) {
                //e.printStackTrace();
                nextPageToken="";

            }


            //Toast.makeText (mContext, "클릭" + totalResults , Toast.LENGTH_SHORT).show();


            SharedPreference.putSharedPreference(mContext, "totalResults", totalResults);
            SharedPreference.putSharedPreference(mContext, "nextPageToken", nextPageToken);


            int count = 0;
            String thum_pic, subjectText, descriptionText, viewCount, viewDate, viewCnt, videoId;

           // Toast.makeText (mContext, "클릭" + jsonArray.length() , Toast.LENGTH_SHORT).show();

            Log.e("jsonArray.length", ""+jsonArray.length());

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);




                if(jsonObject.getString("kind").equals("youtube#playlistItemListResponse")){


                    try {

                        // Toast.makeText (mContext, "클릭" + jsonObject.getString("kind"), Toast.LENGTH_SHORT).show();

                        subjectText = object.getJSONObject("snippet").getString("title");
                        descriptionText = object.getJSONObject("snippet").getString("description");
                        viewDate = object.getJSONObject("snippet").getString("publishedAt")
                                .substring(0, 10);

                        videoId = object.getJSONObject("snippet")
                                .getJSONObject("resourceId").getString("videoId");

                        thum_pic = object.getJSONObject("snippet")
                                .getJSONObject("thumbnails").getJSONObject("medium")
                                .getString("url"); // 썸내일 이미지 URL값


                        viewCnt = "0";
                        DriverMovie drivermovie = new DriverMovie(thum_pic, subjectText, viewDate, viewCnt, videoId , descriptionText);
                        driverMovieList.add(drivermovie);
                    }  catch (Exception e) {
                        //e.printStackTrace();
                        nextPageToken="";

                    }

                }else if(jsonObject.getString("kind").equals("youtube#searchListResponse")){

                    //Toast.makeText (mContext, "클릭" + jsonObject.getString("kind"), Toast.LENGTH_SHORT).show();

                 //   Toast.makeText (mContext, "클릭" + object.getJSONObject("id").getString("videoId") , Toast.LENGTH_SHORT).show();


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
                }





                count++;
            }


            if(location =="main"){
                driverMovieListView.setAdapter(driveradapter);


                driverMovieListView.setOnItemClickListener(new OnItemClickListener() {
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
    String target ="http://www.appfactory.kr/gms/reg/Billiard/"+FirebaseInstanceId.getInstance().getToken();
    String target2 ="http://www.appfactory.kr/gms/cnt/Billiard/"+FirebaseInstanceId.getInstance().getToken();
    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String temp;
        URL url;

        Log.d("주소 url 2 ", ""+FirebaseInstanceId.getInstance().getToken() ) ;
        try {
            url = new URL(target2);
            //Log.e("주소 url 2 ", ""+url);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            stringBuilder = new StringBuilder();
            //Log.e("stringBuilder : ", ""+stringBuilder);
            while ((temp = bufferedReader.readLine()) != null) {
                //Log.e("temp", ""+temp);
                stringBuilder.append(temp + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            int numInt = Integer.parseInt(stringBuilder.toString().trim());

            //Log.e("numInt", ""+numInt);
            if (numInt == 0 ) {

                try {

                    url = new URL(target);
                    //Log.e("주소 url 1", ""+url);


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



