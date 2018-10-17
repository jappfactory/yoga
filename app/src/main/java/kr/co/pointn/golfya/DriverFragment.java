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

import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DriverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public DriverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverFragment newInstance(String param1, String param2) {
        DriverFragment fragment = new DriverFragment();
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

    private ListView driverMovieListView;
    private DriverMovieListAdapter driveradapter;
    private List<DriverMovie> driverMovieList;

    @Override
    public void onActivityCreated(@Nullable Bundle b) {
        super.onActivityCreated(b);

        Log.e("드라이버", "드라이버");

        /*
        driverMovieListView  = (ListView) getView().findViewById(R.id.subDriverListView);
        driverMovieList = new ArrayList<DriverMovie>();

        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));
        driverMovieList.add(new DriverMovie("https://www.sacoop.kr/upload/project_img/29.png","쥬피터 드라이버 영상","100"));

        driveradapter = new DriverMovieListAdapter(getContext().getApplicationContext(), driverMovieList);
      //  driverMovieListView.setAdapter(driveradapter);

*/
        driverMovieListView  =  getView().findViewById(R.id.subDriverListView);
        driverMovieList = new ArrayList<>();
        try {

            Log.e("LoadMovie", "LoadMovie");
             new LoadMovie().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driveradapter = new DriverMovieListAdapter(getContext().getApplicationContext(), driverMovieList);
        driverMovieListView.setAdapter(driveradapter);
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

    private static Context context;





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
}




class LoadMovie extends AsyncTask <Void, Void, String>{


    private List<DriverMovie>  driverMovieList = new ArrayList<DriverMovie>() ;

    String target;
    @Override
    protected void onPreExecute() {

        try{
             target = "http://golfya.pointn.co.kr/index.php/MovieSearch/driver";

        }catch (Exception e){
            e.printStackTrace();
        }
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
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {

        Log.e("드라이버2", ""+result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            int count = 0;
            Log.e("드라이버3", ""+jsonArray.length());

            String thum_pic, subjectText, viewCount, viewDate, viewCnt;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);


                thum_pic = object.getString("thumbnails");
                subjectText = object.getString("title");
                viewDate = object.getString("viewDate");
                viewCnt = object.getString("cnt");
                Log.e("thum_pic", ""+thum_pic);
                Log.e("subjectText", ""+subjectText);
                Log.e("viewDate", ""+viewDate);
                Log.e("viewCnt", ""+viewCnt);

                DriverMovie drivermovie = new DriverMovie(thum_pic,subjectText, viewDate , viewCnt);
                driverMovieList.add(drivermovie);
                //driverMovieList.add(new DriverMovie("","비기너골퍼를","2018", "0"));
                count++;
                Log.e("드라이버4", ""+object);

            }

        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

    }


}