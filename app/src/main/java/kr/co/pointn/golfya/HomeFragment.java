package kr.co.pointn.golfya;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  {

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    public  ListView driverMovieListView;
    public List<DriverMovie> driverMovieList;
    private DriverMovieListAdapter driveradapter;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

    String target = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=2&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&safeSearch=strict&type=video&pageToken=";


    Activity activity;
    private OnFragmentInteractionListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (Activity) getActivity();
    }
    public HomeFragment() {}

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // progressBar.setVisibility(View.GONE);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle b) {
        super.onActivityCreated(b);


        String drivertarget = target +"&q=드라이버+원포인트+레슨";
        String woodtarget = target +"&q=골프+우드+유틸리티+원포인트+레슨";
        String irontarget = target +"&q=골프+아이언+원포인트+레슨";
        String wedgetarget = target +"&q=골프+웨지+원포인트+레슨";
        String puttertarget = target +"&q=골프+퍼팅+원포인트+레슨";


        driverMovieListView  = getView().findViewById(R.id.driveronepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, drivertarget , "main").execute();
        //driverMovieListView.setAdapter(driveradapter);


        driverMovieListView  = getView().findViewById(R.id.woodonepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, woodtarget , "main").execute();
        //driverMovieListView.setAdapter(driveradapter);


        driverMovieListView  = getView().findViewById(R.id.irononepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, irontarget , "main").execute();


        // 다음 데이터를 불러온다.
        // getItem(target);
    }

    public void getItem(String target){

        driverMovieList = new ArrayList<DriverMovie>();
        //driverMovieListView.setAdapter(driveradapter);
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        mLockListView = true;
        //Log.d("target", ""+target);

        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, target, "main").execute();

        // driverMovieListView.setAdapter(driveradapter);
        Log.d("driverMovieList6", ""+driverMovieList);

        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              // driveradapter.notifyDataSetChanged();

                // driveradapter.setNotifyOnChange(false);
                mLockListView = false;


            }
        },1000);
    }

    public void getItem2(String target, DriverMovieListAdapter driveradapter){

        Toast.makeText (getActivity(), "우드" , Toast.LENGTH_LONG).show();
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        mLockListView = true;
        //Log.d("target", ""+target);

        driverMovieListView.setAdapter(driveradapter);
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, target, "main").execute();

        // driverMovieListView.setAdapter(driveradapter);
        Log.d("driverMovieList6", ""+driverMovieList);

        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //driveradapter.notifyDataSetChanged();

                // driveradapter.setNotifyOnChange(false);
                mLockListView = false;


            }
        },1000);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        //new LoadMovieTask(getContext(), driverMovieList).execute();

        View view=inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        // new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, target).cancel(true);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


