package kr.co.pointn.golfya;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MoviePlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String mApiKey = "AIzaSyCBOFrruYNwGIjpNZ9mEHUDyJg3qE3gwco";
    YouTubePlayerView youtubeView;

    YouTubePlayer mPlayer;
    private String videoId;
    Toolbar myToolbar;

    YouTubePlayer.OnInitializedListener listener;


    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);


        TextView title = findViewById(R.id.toolbar_title);
        TextView desc = findViewById(R.id.movie_desc);

        Intent intent = getIntent();
        String subject = intent.getStringExtra("title");
        String videoId = intent.getStringExtra("videoId");
        String videodesc = intent.getStringExtra("videodesc");
        title.setText(subject);
        desc.setText(videodesc);

        //Toast.makeText (getApplicationContext(), "클릭" + videoId , Toast.LENGTH_LONG).show();


        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youtubeView.initialize(mApiKey, this);

        youtubeView.initialize("AIzaSyCBOFrruYNwGIjpNZ9mEHUDyJg3qE3gwco", listener);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


        //mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        // 비디오 아이디
        Intent intent = getIntent();
        String videoId = intent.getStringExtra("videoId");
        enableDisableView(youtubeView, true);
        mPlayer = youTubePlayer;
        mPlayer.loadVideo(""+ videoId);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            mPlayer.release();
        }
    }
}
