package main.biggreenbook.entity.pojo;

public class Resource {
    //资源的id
    private String sid;
    //资源的类型，picture或video
    private String type;
    //资源的路径数组
    private String paths;

    public Resource(String sid, String type, String paths) {
        this.sid = sid;
        this.type = type;
        this.paths = paths;
    }

    public Resource() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}
