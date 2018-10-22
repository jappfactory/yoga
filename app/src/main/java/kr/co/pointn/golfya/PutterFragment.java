package kr.co.pointn.golfya;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class PutterFragment extends Fragment {

    public  ListView driverMovieListView;
    public List<DriverMovie> driverMovieList;
    public DriverMovieListAdapter driveradapter;

    String target;
    private OnFragmentInteractionListener mListener;

    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }
    public PutterFragment() {}

    public static PutterFragment newInstance() {
        PutterFragment fragment = new PutterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle b) {
        super.onActivityCreated(b);
        String target = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g&q=퍼터+스윙+레슨";

        driverMovieListView  = getView().findViewById(R.id.subPutterListView);

        Log.d("driverMovieListView", ""+driverMovieListView);

        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        driverMovieListView.setAdapter(driveradapter);

        Log.d("driverMovieList", ""+driverMovieList);
        //driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","2018-10-10", "0"));
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, target).execute();
        Log.d("driverMovieList6", ""+driverMovieList);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        //new LoadMovieTask(getContext(), driverMovieList).execute();

        return inflater.inflate(R.layout.fragment_putter, container, false);
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
       // new LoadMovieTask(getContext(),driverMovieList, driverMovieListView, target).cancel(true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

