package main.biggreenbook.pojo;

public class Picture {
    private String sid;
    private String path;
    private Integer index;

    public Picture() {
    }

    public Picture(String sid, String path, Integer index) {
        this.sid = sid;
        this.path = path;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "sid='" + sid + '\'' +
                ", path='" + path + '\'' +
                ", index=" + index +
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
