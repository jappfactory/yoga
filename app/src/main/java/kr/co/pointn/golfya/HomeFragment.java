package kr.co.pointn.golfya;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListView driverMovieListView;
    public List<DriverMovie> driverMovieList;
    public DriverMovieListAdapter driveradapter;

    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle b) {
        super.onActivityCreated(b);


        String target = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=2&key=AIzaSyBn4fOG4zKOYVbYtcMtGj8gGsVVpTYb68g";


        String drivertarget = target +"&q=드라이버+원포인트+레슨";
        String woodtarget = target +"&q=골프+우드+유틸리티+원포인트+레슨";
        String irontarget = target +"&q=골프+아이언+원포인트+레슨";
        String wedgetarget = target +"&q=골프+웨지+원포인트+레슨";
        String puttertarget = target +"&q=골프+퍼팅+원포인트+레슨";

        driverMovieListView  = getView().findViewById(R.id.driveronepoint);
        driverMovieList = new ArrayList<DriverMovie>();
       // driverMovieListView.setAdapter(driveradapter);


        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);

        //driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","2018-10-10", "0"));


        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, drivertarget).execute();


        driverMovieListView  = getView().findViewById(R.id.woodonepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        //driverMovieListView.setAdapter(driveradapter);


        //driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","2018-10-10", "0"));
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter, woodtarget).execute();


        driverMovieListView  = getView().findViewById(R.id.irononepoint);
        driverMovieList = new ArrayList<DriverMovie>();
        driveradapter = new DriverMovieListAdapter(activity, driverMovieList, this);
        //driverMovieListView.setAdapter(driveradapter);


        //driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/33.jpg","쥬피터 아이언 영상","2018-10-10", "0"));
        new LoadMovieTask(getActivity(), driverMovieList, driverMovieListView, driveradapter,  irontarget).execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //driverMovieListView.setOnItemClickListener(this);


        //Toast.makeText (getActivity(), "클릭", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_home, container, false);
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
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
