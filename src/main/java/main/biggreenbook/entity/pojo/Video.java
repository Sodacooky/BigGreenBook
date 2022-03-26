package main.biggreenbook.entity.pojo;

public class Video {
    private String sid;
    private String path;

    public Video() {
    }

    public Video(String sid, String path) {
        this.sid = sid;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Video{" +
                "sid='" + sid + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
