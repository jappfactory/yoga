package kr.co.pointn.golfya;

public class News {

    String news;
    String name;
    String date;

    public News(String news, String name, String date) {
        this.news = news;
        this.name = name;
        this.date = date;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
