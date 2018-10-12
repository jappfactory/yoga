package kr.co.pointn.golfya;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.driver, null);

        ImageView thum_pic = (ImageView) v.findViewById(R.id.thumimg);
        TextView subjectText = (TextView) v.findViewById(R.id.subjectText);
        TextView viewText = (TextView) v.findViewById(R.id.viewText);


        Log.e("getThum_img",driverMovieList.get(i).getThum_img());

        Picasso.with(context)
                .load(driverMovieList.get(i).getThum_img())
                .into(thum_pic);

        //thum_pic.setImageBitmap(back.class.etBitmapFromURL(driverMovieList.get(i).getThum_img()));

        //thum_pic.setImageURI(Uri.parse(driverMovieList.get(i).getThum_img()));
        subjectText.setText(driverMovieList.get(i).getMovie_title());
        viewText.setText(driverMovieList.get(i).getMovie_view());

        v.setTag(driverMovieList.get(i).getMovie_title());

        return v;
    }

}