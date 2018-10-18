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


public class WedgeFragment extends Fragment {

    public  ListView driverMovieListView;
    public List<DriverMovie> driverMovieList;
    public DriverMovieListAdapter driveradapter;

    String target;
    private OnFragmentInteractionListener mListener;

    public WedgeFragment() {}

    public static WedgeFragment newInstance() {
        WedgeFragment fragment = new WedgeFragment();
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
        target = "http://golfya.pointn.co.kr/index.php/MovieSearch/wedge";

        driverMovieListView  = getView().findViewById(R.id.subWedgeListView);

        Log.d("driverMovieListView", ""+driverMovieListView);

        driverMovieList = new ArrayList<DriverMovie>();

        Log.d("driverMovieList", ""+driverMovieList);
        //driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","2018-10-10", "0"));
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, target).execute();
        Log.d("driverMovieList6", ""+driverMovieList);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        //new LoadMovieTask(getContext(), driverMovieList).execute();

        return inflater.inflate(R.layout.fragment_wedge, container, false);
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
        new LoadMovieTask(getContext(),driverMovieList, driverMovieListView, target).cancel(true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

