package kr.co.pointn.golfya;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DriverMovieListAdapter extends BaseAdapter {

    private Context context;
    private List<DriverMovie> driverMovieList;

    public DriverMovieListAdapter(Context context, List<DriverMovie> driverMovieList) {
        this.context = context;
        this.driverMovieList = driverMovieList;
    }


    @Override
    public int getCount() {
        return driverMovieList.size();
    }

    @Override
    public Object getItem(int i) {
        return driverMovieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            Log.e("url", ""+ url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.driver, null);

        ImageView thum_pic = (ImageView) v.findViewById(R.id.thumimg);
        TextView subjectText = (TextView) v.findViewById(R.id.subjectText);
        TextView viewText = (TextView) v.findViewById(R.id.viewText);


        Log.e("getThum_img",driverMovieList.get(i).getThum_img());

        thum_pic.setImageBitmap(getBitmapFromURL(driverMovieList.get(i).getThum_img()));

        //thum_pic.setImageURI(Uri.parse(driverMovieList.get(i).getThum_img()));
        subjectText.setText(driverMovieList.get(i).getMovie_title());
        viewText.setText(driverMovieList.get(i).getMovie_view());

        v.setTag(driverMovieList.get(i).getMovie_title());

        return v;
    }
}
