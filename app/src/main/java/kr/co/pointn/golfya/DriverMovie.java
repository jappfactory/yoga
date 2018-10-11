package kr.co.pointn.golfya;

public class DriverMovie {

    String thum_img;
    String movie_title;
    String movie_view;

    public DriverMovie(String thum_img, String movie_title, String movie_view) {
        this.thum_img = thum_img;
        this.movie_title = movie_title;
        this.movie_view = movie_view;
    }

    public String getThum_img() {
        return thum_img;
    }

    public void setThum_img(String thum_img) {
        this.thum_img = thum_img;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_view() {
        return movie_view;
    }

    public void setMovie_view(String movie_view) {
        this.movie_view = movie_view;
    }
}
