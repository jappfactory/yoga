package kr.co.pointn.golfya;

public class DriverMovie {

    String thum_img;
    String movie_title;
    String movie_count;
    String movie_date;

    public DriverMovie(String thum_img, String movie_title, String movie_date, String movie_count) {
        this.thum_img = thum_img;
        this.movie_title = movie_title;
        this.movie_count = movie_count;
        this.movie_date = movie_date;
    }

    public String getThum_img() {
        return thum_img;
    }

    public void setThum_img(String thum_img) {
        this.thum_img = thum_img;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_count() {
        return movie_count;
    }

    public void setMovie_count(String movie_count) {
        this.movie_count = movie_count;
    }
}
