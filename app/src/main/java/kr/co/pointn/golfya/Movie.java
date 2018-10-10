package kr.co.pointn.golfya;

public class Movie {


    String img;
    String subject;
    String viwcnt;

    public Movie(String img, String subject, String viwcnt) {
        this.img = img;
        this.subject = subject;
        this.viwcnt = viwcnt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getViwcnt() {
        return viwcnt;
    }

    public void setViwcnt(String viwcnt) {
        this.viwcnt = viwcnt;
    }
}
