package kr.co.pointn.golfya;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MovieListAdapter extends BaseAdapter {

    private Context   context;
    private List<News>  movieList;

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.driver, null);

        TextView thum_pic = (TextView) v.findViewById(R.id.thum_pic);
        TextView subjectText = (TextView) v.findViewById(R.id.subjectText);
        TextView viewText = (TextView) v.findViewById(R.id.viewText);

        thum_pic.setText(movieList.get(i).);
        nameText.setText(movieList.get(i).getName());
        dateText.setText(movieList.get(i).getDate());

        v.setTag(movieList.get(i).getNews());
        return null;
    }
}
